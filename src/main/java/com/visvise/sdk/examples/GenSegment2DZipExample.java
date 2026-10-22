package com.visvise.sdk.examples;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.api.VisviseAPI;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.NodeType;
import com.visvise.sdk.enums.SegmentSplitType;
import com.visvise.sdk.http.SSEIterator;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.models.SSEResult;
import com.visvise.sdk.models.SegmentComponent;
import com.visvise.sdk.models.SegmentData;
import com.visvise.sdk.models.SegmentOutput;
import com.visvise.sdk.models.View;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenSegment2DOptions;
import com.visvise.sdk.options.WaitOptions;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Example: 2D 拆分产物 zip_path 的两种获取方式（node_type=14）
 *
 * 输入方式：直接上传本地六视图原画，不走图生360
 *   inputView             六视图（正视图必传，其余按需，缺哪个就跳过哪个）
 *   skip360Preprocess     true —— 跳过图生360前置，直接用 input_view 拆分
 *   skipRemoveBackground  true —— 跳过去背景，原始图原样送拆分算法
 *   splitType             2（多视图拆分），支持 1~6 个视图，按实际传入的视图投递
 *
 * zip_path 是算法侧产出的拆分结果压缩包（COS 路径），后端只落库不解析，里面装什么由算法定义。
 * 后端对外返回时会自动加临时签名 ⇒ **浏览器打开 / curl / 代码 GET 都能直接下载，无需鉴权头**。
 *
 * 方式一（推荐，最简单）：genSegment2D + waitModel
 *   genSegment2D → modelId（本地文件由 SDK 自动上传 COS）
 *   waitModel    → ModelInfo.getSegmentOutput().getZipPath()
 *   顺带还能拿到各视图的部件表 components / npz dataPath / 彩色预览图 colorImgs。
 *   链接在「查询那一刻」现签，24h 内有效，过期重新查一次详情即可拿新链接。
 *
 * 方式二（生成瞬间就拿到）：底层 api.initSegment 自己消费 SSE 帧
 *   高层 genSegment2D 只把 model_id 返回、reply 帧被丢弃；
 *   想在同一条 SSE 连接里直接取 zip_path，就自己遍历事件帧拿 reply。
 *   注意底层接口只接受「已上传到平台的 COS URL」，本地文件需要先自己上传一次。
 *
 * 两种方式拿到的是同一个 COS 文件，区别只在「什么时候签的名」和「要不要多查一次接口」。
 *
 * Usage:
 *   VISVISE_APP_ID=xxx VISVISE_SECRET_KEY=xxx VISVISE_RTX=xxx VISVISE_ENV=prod \
 *   SEG_ASSETS_DIR=src/test/resources/assets java ... GenSegment2DZipExample
 */
public class GenSegment2DZipExample {

    static final String APP_ID     = System.getenv("VISVISE_APP_ID");
    static final String SECRET_KEY = System.getenv("VISVISE_SECRET_KEY");
    static final String RTX        = System.getenv("VISVISE_RTX");
    static final String ENV        = System.getenv().getOrDefault("VISVISE_ENV", "prod");

    /** 六视图素材目录（默认 src/test/resources/assets，可用 SEG_ASSETS_DIR 覆盖） */
    static final String ASSETS_DIR = System.getenv().getOrDefault("SEG_ASSETS_DIR", "src/test/resources/assets");
    /** zip 下载到哪个目录 */
    static final String OUT_DIR    = System.getenv().getOrDefault("SEG_OUT_DIR", ".");

    /** 六个视图：本地文件路径，SDK 自动上传 COS */
    private static final String[] VIEW_FILES = {
            "main_view.png", "back_view.png", "left_view.png",
            "right_view.png", "top_view.png", "bottom_view.png",
    };

    public static void main(String[] args) throws Exception {
        Environment env = "dev".equals(ENV) ? Environment.DEV
                : "test".equals(ENV) ? Environment.TEST : Environment.PROD;

        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY,
                ClientOptions.create().setEnv(env));

        View inputView = buildInputView();

        way1HighLevel(client, inputView);
        System.out.println();
        way2SseReply(client, inputView);

        System.out.println("\n[提示] zip_path 是带签名的 URL，24h 内直接 GET 即可下载；"
                + "过期后重新调用 waitModel / getModelList 就能拿到新链接。");
    }

    /** 逐个探测六视图素材，存在则带上，不存在则跳过该视图。 */
    static View buildInputView() {
        View view = new View();
        String main = pick("main_view.png");
        if (main == null) {
            throw new IllegalStateException("[gen_segment_2d_zip] 正视图 main_view.png 必传，目录: " + ASSETS_DIR);
        }
        view.setMainView(main);
        view.setBackView(pick("back_view.png"));
        view.setLeftView(pick("left_view.png"));
        view.setRightView(pick("right_view.png"));
        view.setTopView(pick("top_view.png"));
        view.setBottomView(pick("bottom_view.png"));
        return view;
    }

    static String pick(String filename) {
        Path path = Paths.get(ASSETS_DIR, filename);
        if (!Files.isRegularFile(path)) {
            System.out.println("  [skip] 素材不存在，跳过该视图: " + path);
            return null;
        }
        System.out.println("  [view] " + path);
        return path.toString();
    }

    // ── 方式一：高层方法 + 资产详情（waitModel），最省事的一种 ──────────────

    static void way1HighLevel(VisviseClient client, View inputView) throws Exception {
        System.out.println("[方式一] genSegment2D → waitModel → segmentOutput.zipPath");

        String segModelId = client.genSegment2D("", // 不用图生360 的 model_id
                GenSegment2DOptions.create()
                        .setName("example_gen_segment_2d_zip_way1")
                        .setSplitType(SegmentSplitType.FOUR_VIEW) // 2 多视图拆分（按实际传入的视图，1~6 个）
                        .setSkip360Preprocess(true)               // 跳过图生360前置，直接用 input_view 拆分
                        .setSkipRemoveBackground(true)            // 跳过去背景，原始图直接送算法
                        .setInputView(inputView),                 // 本地文件由 SDK 自动上传 COS
                RTX);
        System.out.println("  model_id=" + segModelId);

        // waitModel 轮询到 status=3 才返回，此时 zipPath 一定已落库
        ModelInfo info = client.waitModel(segModelId, WaitOptions.create().setTimeout(600), RTX);

        printSegmentOutput(info.getSegmentOutput());
        if (info.getSegmentOutput() != null) {
            download(info.getSegmentOutput().getZipPath(), "segment_output_way1.zip");
        }
    }

    // ── 方式二：自己消费 SSE 流，在 reply 帧里直接拿 zip_path（不用再查一次） ──

    static void way2SseReply(VisviseClient client, View inputView) throws Exception {
        System.out.println("[方式二] getAPI().initSegment 自己遍历 SSE 帧 → reply 帧里的 zip_path");

        VisviseAPI api = client.getAPI();

        // 底层接口需要显式传算法模型名，高层 genSegment2D 会自动选，这里手动取一个
        List<String> models = api.listAlgorithmModel(NodeType.SEGMENT_2D.getValue(), null, RTX);
        if (models == null || models.isEmpty()) {
            System.out.println("  [warn] 当前账号没有可用的 2D 拆分算法模型");
            return;
        }
        String algorithmModel = models.get(0);
        System.out.println("  algorithm_model=" + algorithmModel);

        // 底层接口只接受已上传到平台的 COS URL，本地文件先上传一次
        View cosView = uploadView(client, inputView);

        String zipPath = "";
        String clientId = "";

        try (SSEIterator iter = api.initSegment(
                "example_gen_segment_2d_zip_way2",             // name
                algorithmModel,                                // algorithm_model
                "",                                            // model_id：不走图生360，留空
                cosView,                                       // input_view（六视图的 COS URL）
                SegmentSplitType.FOUR_VIEW.getValue(),         // split_type：2 多视图
                null,                                          // granularity
                null,                                          // prompt
                true,                                          // skip_360_preprocess
                true,                                          // skip_remove_background
                null,                                          // decomposition_plan
                0,                                             // readTimeout，0 表示用默认值
                RTX)) {

            while (true) {
                SSEResult event = iter.next();
                if (event == null) {
                    break;
                }
                String name = event.getEvent();
                Object data = event.getData();
                if ("pre_create".equals(name)) {
                    System.out.println("  [pre_create] model_id=" + str(data, "model_id"));
                } else if ("thinking".equals(name)) {
                    System.out.println("  [thinking] " + data);
                } else if ("reply".equals(name)) {
                    zipPath = str(data, "zip_path");
                    clientId = str(data, "client_id");
                    System.out.println("  [reply] zip_path=" + zipPath);
                    break;
                } else if ("error".equals(name)) {
                    System.out.println("  [error] " + data);
                    return;
                }
            }
        }

        System.out.println("  client_id=" + clientId + "（可传给 openSegment 做二次编辑）");
        download(zipPath, "segment_output_way2.zip");
    }

    /** 把本地六视图逐个上传到平台 COS，返回只含 COS URL 的 View。 */
    static View uploadView(VisviseClient client, View view) throws Exception {
        View uploaded = new View();
        uploaded.setMainView(uploadOne(client, view.getMainView()));
        uploaded.setBackView(uploadOne(client, view.getBackView()));
        uploaded.setLeftView(uploadOne(client, view.getLeftView()));
        uploaded.setRightView(uploadOne(client, view.getRightView()));
        uploaded.setTopView(uploadOne(client, view.getTopView()));
        uploaded.setBottomView(uploadOne(client, view.getBottomView()));
        return uploaded;
    }

    static String uploadOne(VisviseClient client, String path) throws Exception {
        if (path == null || path.isEmpty()) {
            return null;
        }
        return client.upload(path, new File(path).getName(), false, RTX);
    }

    // ── 打印与下载 ────────────────────────────────────────────────────────

    /** 打印各视图的分割数据（没参与拆分的视图为 null）。 */
    static void printSegmentOutput(SegmentOutput so) {
        if (so == null) {
            System.out.println("  [warn] segmentOutput 为空：只有 2D 拆分（node_type=14）资产才有拆分产物");
            return;
        }
        System.out.println("  zip_path = " + so.getZipPath());

        String[] names = {"main_view", "back_view", "left_view", "right_view", "top_view", "bottom_view"};
        SegmentData[] views = {so.getMainView(), so.getBackView(), so.getLeftView(),
                so.getRightView(), so.getTopView(), so.getBottomView()};

        for (int i = 0; i < names.length; i++) {
            SegmentData v = views[i];
            if (v == null) {
                continue; // 该视图没参与拆分
            }
            System.out.println("  " + names[i] + ": shape=" + v.getShape()
                    + " npz=" + v.getDataPath() + " mask PNG=" + v.getMaskImagePath());
            if (v.getComponents() != null) {
                for (SegmentComponent comp : v.getComponents()) {
                    System.out.println("    部件 label=" + comp.getLabel() + " name=" + comp.getName()
                            + " color=" + comp.getColor() + "  ← label 可作为 componentLabel 送图生中模");
                }
            }
        }
        if (so.getColorImgs() != null && so.getColorImgs().getMainView() != null) {
            System.out.println("  正视图彩色预览图=" + so.getColorImgs().getMainView());
        }
    }

    /** zip_path 是带 COS 签名的 URL，直接 GET 即可，无需任何鉴权头。 */
    static void download(String url, String filename) {
        if (url == null || url.isEmpty()) {
            System.out.println("  [warn] zip_path 为空（该资产没有算法产物，或后端未落库），跳过下载");
            return;
        }
        Path saveAs = Paths.get(OUT_DIR, filename);
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(60000);
            int code = conn.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                System.out.println("  [warn] 下载失败: HTTP " + code);
                return;
            }
            try (InputStream in = conn.getInputStream()) {
                Files.copy(in, saveAs, StandardCopyOption.REPLACE_EXISTING);
            }
            System.out.println("  [download] " + saveAs + "（" + Files.size(saveAs) + " bytes）");
        } catch (Exception e) {
            System.out.println("  [warn] 下载失败: " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /** 从 SSE 帧的 data（JsonObject）里取字符串字段，取不到返回空串。 */
    static String str(Object data, String key) {
        if (!(data instanceof JsonObject)) {
            return "";
        }
        JsonElement el = ((JsonObject) data).get(key);
        if (el == null || el.isJsonNull()) {
            return "";
        }
        return el.getAsString();
    }
}
