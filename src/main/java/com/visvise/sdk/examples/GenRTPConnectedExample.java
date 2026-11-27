package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenRTPConnectedOptions;
import com.visvise.sdk.options.GenRetopologyOptions;
import com.visvise.sdk.options.WaitOptions;

/**
 * Example: gen_rtp_connected —— 重拓扑-连通体（node_type=19）
 *
 * 两段式自研（VV）重拓扑的完整链路：
 *  1. genRTPConnected：创建连通体分组任务（node_type=19）
 *  2. 等待完成，下载 output_model（连通体分组产物 zip）并在前端编辑连通体分组
 *  3. 将编辑结果重新打包成 zip 并上传到当前账号的 COS 目录
 *  4. saveRTPConnected：保存该产物，得到新的连通体资产 ID（node_type=19，status=3）
 *  5. genRetopology：以保存得到的 model_id 作为 parentModelId 发起第二段重拓扑（node_type=1）
 */
public class GenRTPConnectedExample {

    // 从环境变量读取鉴权信息
    static final String APP_ID = System.getenv("VISVISE_APP_ID");
    static final String SECRET_KEY = System.getenv("VISVISE_SECRET_KEY");
    static final String RTX = System.getenv("VISVISE_RTX");
    static final String ENV = System.getenv().getOrDefault("VISVISE_ENV", "prod");

    // 素材路径（请替换为实际文件路径）
    static final String ASSETS = "src/test/resources/assets";
    static final String MODEL = ASSETS + "/high_model.fbx";
    /** 前端编辑后重新打包的连通体产物 zip（本地文件或 COS URL），为空时跳过「保存产物 + 第二段重拓扑」 */
    static final String EDITED_ZIP = System.getenv("VISVISE_RTP_CONNECTED_ZIP");

    // Creates the client and runs the two-stage re-topology flow
    public static void main(String[] args) throws WeaverError {
        Environment env = "dev".equals(ENV) ? Environment.DEV
                : "test".equals(ENV) ? Environment.TEST : Environment.PROD;
        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY,
                ClientOptions.create().setEnv(env));

        // ── 第一段：重拓扑-连通体 ────────────────────────────────────────────
        System.out.println("[gen_rtp_connected] 创建重拓扑-连通体任务（node_type=19）...");

        String connectedId = client.genRTPConnected(MODEL,
                GenRTPConnectedOptions.create().setName("example_gen_rtp_connected"),
                RTX);
        System.out.println("[gen_rtp_connected] 任务已创建，model_id=" + connectedId);

        System.out.println("[gen_rtp_connected] 等待完成...");
        ModelInfo connected = client.waitModel(connectedId,
                WaitOptions.create().setInterval(5).setTimeout(900),
                RTX);
        System.out.println("[gen_rtp_connected] 连通体分组产物: " + connected.getOutputModel());
        System.out.println("  请下载该 zip，在前端编辑连通体分组后重新打包上传到当前账号的 COS 目录，");
        System.out.println("  然后调用 saveRTPConnected 保存产物，并以返回的 model_id 作为重拓扑的 parentModelId。");

        if (EDITED_ZIP == null || EDITED_ZIP.isEmpty()) {
            System.out.println("\n未设置 VISVISE_RTP_CONNECTED_ZIP，跳过「保存产物 + 第二段重拓扑」。");
            return;
        }

        // ── 保存产物 + 第二段重拓扑 ─────────────────────────────────────────
        System.out.println("\n[save_rtp_connected] 保存编辑后的连通体产物...");
        String savedId = client.saveRTPConnected(connectedId, EDITED_ZIP,
                "example_rtp_connected_saved", RTX);
        System.out.println("[save_rtp_connected] 保存成功，新的连通体资产 ID=" + savedId);

        System.out.println("[gen_retopology] 发起第二段重拓扑（node_type=1）...");
        String rtpId = client.genRetopology(EDITED_ZIP,
                GenRetopologyOptions.create()
                        .setName("example_gen_retopology_vv")
                        .setFaceNum(10000)
                        .setSmoothStrength(1.0)
                        .setParentModelId(savedId),
                RTX);
        System.out.println("[gen_retopology] 任务已创建，model_id=" + rtpId);

        ModelInfo rtpModel = client.waitModel(rtpId,
                WaitOptions.create().setInterval(5).setTimeout(900),
                RTX);
        System.out.println("[gen_retopology] 生成成功！耗时 " + rtpModel.getTimeCost() + "s");
        System.out.println("  output_model : " + rtpModel.getOutputModel());
    }
}
