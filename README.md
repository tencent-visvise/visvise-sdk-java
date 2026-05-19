# VISVISE Weaver Java SDK

**[English](README_EN.md)** | 中文

VISVISE Weaver OpenAPI 的 Java SDK，提供：

- 全部原子 API 方法（逐一对应 OpenAPI 接口）
- 各节点类型的高阶 `Gen*()` 方法（自动上传文件 + 创建任务）
- `WaitModel()` 异步轮询方法

---

## 目录

- [下载](#下载)
- [快速开始](#快速开始)
- [客户端初始化](#客户端初始化)
- [枚举常量](#枚举常量)
- [高阶方法参考](#高阶方法参考)
  - [Gen360 — 图生360](#gen360--图生360)
  - [GenHighModel — 图生高模](#genhighmodel--图生高模)
  - [GenMidModel — 图生中模](#genmidmodel--图生中模)
  - [GenLowModel — 图生低模](#genlowmodel--图生低模)
  - [GenMeshRefine — 重布线](#genmeshrefine--重布线)
  - [GenRetopology — 重拓扑](#genretopology--重拓扑)
  - [GenLOD — LOD](#genlod--lod)
  - [GenUV — UV展开](#genuv--uv展开)
  - [GenTexture — 贴图纹理](#gentexture--贴图纹理)
  - [GenRigging — 骨骼架设](#genrigging--骨骼架设)
  - [GenSkinning — 蒙皮生成](#genskinning--蒙皮生成)
  - [GenVideoMotion — 视频生动画](#genvideomotion--视频生动画)
  - [GenTextMotion — 文本生动画](#gentextmotion--文本生动画)
  - [GenPose — 图生Pose](#genpose--图生pose)
  - [GenSegment2D — 2D 拆分](#gensegment2d--2d-拆分)
  - [WaitModel — 等待完成](#waitmodel--等待完成)
- [原子 API 方法参考](#原子-api-方法参考)
- [错误说明](#错误说明)
- [完整流程示例](#完整流程示例)

---

## 下载

手动下载 JAR 文件引入项目。

- [最新 Release](https://github.com/tencent-visvise/visvise-sdk-java/releases/latest)
- [所有 Releases](https://github.com/tencent-visvise/visvise-sdk-java/releases)

---

## 快速开始

```java
import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.options.*;

public class Main {
    public static void main(String[] args) {
        // 创建客户端（rtx 通过环境变量或每次调用时传入）
        String rtx = System.getenv("VISVISE_RTX");
        VisviseClient client = new VisviseClient(
            "your_app_id",
            "your_secret_key",
            null // 使用默认配置，或传入 ClientOptions
        );

        // ① 图生360：上传本地图片，生成多视图
        Gen360Options opts = Gen360Options.create()
            .setEnableAPose(true);  // 可选
        String mvModelId = client.gen360("character.png", opts, rtx);

        // ② 等待图生360完成，获取多视图输出
        ModelInfo mvInfo = client.waitModel(mvModelId,
            WaitOptions.create().setInterval(3.0).setTimeout(300), rtx);
        View outputView = mvInfo.getImageGen360Output().getOutputView();

        // ③ 图生高模（多视图输出的 COS URL 直接传入）
        GenHighModelOptions highOpts = GenHighModelOptions.create()
            .setBackView(outputView.getBackView())
            .setLeftView(outputView.getLeftView())
            .setRightView(outputView.getRightView());
        String highModelId = client.genHighModel(outputView.getMainView(), highOpts, rtx);

        // ④ 等待高模完成
        ModelInfo modelInfo = client.waitModel(highModelId,
            WaitOptions.create().setTimeout(900), rtx);
        System.out.println("输出模型: " + modelInfo.getOutputModel());
    }
}
```

---

## 客户端初始化

```java
import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.enums.Environment;

// 必需参数
String rtx = System.getenv("VISVISE_RTX");  // 从环境变量获取
VisviseClient client = new VisviseClient(
    "your_app_id",     // 必填，由平台分配
    "your_secret_key"  // 必填，由平台分配
);

// 简化构造函数（使用默认配置）
// 默认配置：Env.PROD, Timeout=30

// 自定义环境示例
ClientOptions opts = ClientOptions.create()
    .setEnv(Environment.DEV)   // 设置环境
    .setTimeout(60)            // 设置超时（秒）
    .setDebug(true);           // 开启调试日志
VisviseClient client = new VisviseClient(appId, secretKey, opts);
```

| 参数 | 必填 | 说明 |
|---|---|---|
| `appId` | ✅ | 由平台分配的客户端标识 |
| `secretKey` | ✅ | 由平台分配的签名密钥 |
| `opts` | — | 可选参数 `ClientOptions`，null 表示使用默认值 |
| `rtx` | ✅ | 用户 Token，每次 API 调用时传入 |

### 调试日志

开启调试日志后，会输出 HTTP 请求/响应的详细信息：

```java
org.slf4j.simpleLogger.log.visvise.http=debug
```

调试日志示例输出：
```
[DEBUG] POST request: url=https://ws.visvise.com.cn/api/xxx, body={"key":"value"...}
[DEBUG] POST response: url=https://ws.visvise.com.cn/api/xxx, status=200, body={"code":0,"data":{...}}
```

日志内容包含：
- HTTP 请求方法、URL 和请求体
- HTTP 响应状态码和响应体
- 响应体超过 2000 字符时会被截断


---

## 枚举常量

SDK 提供以下枚举常量，推荐使用枚举替代硬编码数字/字符串：

```java
import com.visvise.sdk.enums.*;

// 面数类型
FaceType.TRIANGLE  // 1 - 三角面
FaceType.QUAD      // 2 - 四边面

// 精细程度（重拓扑）
DetailLevel.LOW    // 1 - 低
DetailLevel.MEDIUM // 2 - 中
DetailLevel.HIGH   // 3 - 高

// 模型格式
ModelFormat.FBX  // "fbx"
ModelFormat.OBJ  // "obj"
ModelFormat.GLB  // "glb"

// 布线优化模式
MeshRefineMode.OPTIMIZE  // 1 - 布线优化
MeshRefineMode.DENSIFY   // 2 - 布线加密

// 2D 拆分方式
SegmentSplitType.FRONT_VIEW  // 1 - 生成正视图拆分（默认）
SegmentSplitType.FOUR_VIEW   // 2 - 生成四视图拆分

// 2D 拆分颗粒度
SegmentGranularity.COARSE   // 1 - 粗
SegmentGranularity.MEDIUM   // 2 - 中（默认）
SegmentGranularity.FINE     // 3 - 细

// 动画子类型
AnimationSubType.VIDEO  // 1 - 视频生动画
AnimationSubType.TEXT    // 2 - 文生动画

// 环境
Environment.PROD  // 生产环境
Environment.TEST  // 测试环境
Environment.DEV   // 开发环境

// 节点类型
NodeType.IMG_TO_360      // 7 - 图生360
NodeType.IMG_TO_3D_HIGH  // 3 - 图生高模
NodeType.ANIMATION       // 4 - 动画
// ... 更多节点类型

// 模型状态
ModelStatus.SUCCESS  // 3 - 生成成功
ModelStatus.FAILED   // 4 - 生成失败
ModelStatus.PENDING   // 1 - 等待中
ModelStatus.RUNNING   // 2 - 生成中
```

---

## 高阶方法参考

高阶方法封装了「COS 文件上传 + 创建异步任务」两步，传入文件路径（本地）或 COS URL 均可，返回 `model_id`。

所有 `Gen*()` 方法采用 **Options 结构体** 模式，支持链式调用：

> **关于 `name`：** 所有 Gen* 方法的 `name` 参数均为可选，默认值在 `Gen*Options.create()` 中设置。可通过 `setName()` 自定义。

> **关于 `algorithmModel`：** 所有 Gen* 方法的 `algorithmModel` 参数均为可选。若不传，SDK 将自动调用 `ListAlgorithmModel` 获取当前账号可用的第一个算法模型。

> **关于文件输入：** 所有文件类参数（如 `main_view` / `model_path` / `video_path` / `input_images` 等）统一支持四种形式：
> - **本地路径**（`str`）：直接传文件路径，SDK 自动上传。
> - **VISVISE 平台 COS URL**（`str`）：传入 `https://...myqcloud.com/...` 形式的链接，SDK 不再上传。
> - **文件类型**(`File`)：直接传File文件，SDK自动上传
> - **二进制内容**（`bytes` / `InputStream`）：SDK 自动通过 magic bytes 识别格式（图片 PNG/JPEG/GIF/BMP/WebP/TIFF、3D 模型 FBX/OBJ/GLB/GLTF、视频 MP4/MOV/WebM/AVI、ZIP），用 `<uuid>.<识别后缀>` 自动命名上传，无需用户提供文件名。


### Gen360 — 图生360

从单张图片生成 360 度多视图。→ [示例代码](src/main/java/com/visvise/sdk/examples/Gen360Example.java)

```java
Gen360Options opts = Gen360Options.create()
    .setName("my_360")                                    // 可选，默认 "gen_360"
    .setOutputModelFormat(ModelFormat.FBX)                // 可选，输出格式（默认 fbx）
    .setFaceType(FaceType.TRIANGLE)                       // 可选，面数类型（默认三角面）
    .setEnableAPose(true)                                 // 可选，是否开启 A-Pose
    .setStyle(ImageGen360Style.GRAY_MODEL.getValue())     // # 可选，风格类型（仅 VISVISE 自研模型支持），只接受 ImageGen360Style 枚举：GRAY_MODEL/PHOTOREAL/Q_TOON/PIXEL，传其他值会报错
    .setBackView(backViewPath)                            // 可选，背视图（本地路径或 COS URL）
    .setLeftView(leftViewPath)                            // 可选，左视图
    .setRightView(rightViewPath)                          // 可选，右视图

String modelId = client.gen360("path/to/character.png", opts, rtx);
```

---

### GenHighModel — 图生高模

从图片/多视图生成高精度 3D 模型（node_type=3）。→ [示例代码](src/main/java/com/visvise/sdk/examples/GenHighModelExample.java)

```java
GenHighModelOptions opts = GenHighModelOptions.create()
    .setName("my_high_model")                            // 可选，默认 "gen_high_model"
    .setOutputModelFormat(ModelFormat.FBX)               // 可选，输出格式（默认 fbx）
    .setFaceType(FaceType.TRIANGLE)                      // 可选，面数类型（默认三角面）
    .setFaceNum(500000)                                  // 可选，目标面数（1000~1500000）
    .setBackView(backView)                               // 可选，背视图（本地路径或 COS URL）
    .setLeftView(leftView)                               // 可选，左视图
    .setRightView(rightView)                             // 可选，右视图

String modelId = client.genHighModel("path/to/main.png", opts, rtx);
```

---

### GenMidModel — 图生中模

中模要求四视图全部必传（node_type=11）。→ [示例代码](src/main/java/com/visvise/sdk/examples/GenMidModelExample.java)

```java
GenMidModelOptions opts = GenMidModelOptions.create()
    .setName("my_mid_model")                             // 可选，默认 "gen_mid_model"
    .setOutputModelFormat(ModelFormat.FBX)               // 可选，输出格式
    .setFaceType(FaceType.TRIANGLE)                      // 可选，面数类型
    .setSegmentModelId("Model2026...")                   // 可选，2D 分割资产 ID

// mainView, backView, leftView, rightView 四个视图必填
String modelId = client.genMidModel(
    "path/to/main.png",
    "path/to/back.png",
    "path/to/left.png",
    "path/to/right.png",
    opts, rtx
);
```

---

### GenLowModel — 图生低模

低模只需主视图（node_type=13）。→ [示例代码](src/main/java/com/visvise/sdk/examples/GenLowModelExample.java)

```java
GenLowModelOptions opts = GenLowModelOptions.create()
    .setName("my_low_model")                             // 可选，默认 "gen_low_model"
    .setOutputModelFormat(ModelFormat.FBX)               // 可选，输出格式
    .setFaceType(FaceType.TRIANGLE)                      // 可选，面数类型
    .setBackView(backView)                               // 可选，背视图（本地路径或 COS URL）
    .setLeftView(leftView)                               // 可选，左视图
    .setRightView(rightView)                             // 可选，右视图

String modelId = client.genLowModel("path/to/main.png", opts, rtx);
```

---

### GenMeshRefine — 重布线

对模型进行布线优化（node_type=10）。→ [示例代码](src/main/java/com/visvise/sdk/examples/GenMeshRefineExample.java)

```java
GenMeshRefineOptions opts = GenMeshRefineOptions.create()
    .setName("my_mesh_refine")                          // 可选，默认 "gen_mesh_refine"
    .setInputModelFormat(ModelFormat.FBX)                // 可选，输入模型格式（默认 fbx）
    .setMode(MeshRefineMode.OPTIMIZE)                   // 可选，布线优化模式
    .setColorModel(colorModelPath)                       // 可选，色彩模型（本地路径或 COS URL）

String modelId = client.genMeshRefine("path/to/model.fbx", opts, rtx);
```

---

### GenRetopology — 重拓扑

对高面数模型进行拓扑优化（node_type=1）。→ [示例代码](src/main/java/com/visvise/sdk/examples/GenRetopologyExample.java)

> 注意：混元模型传 `DetailLevel`，VISVISE 自研模型传 `FaceNum`，二选一。

```java
// 混元模型示例
GenRetopologyOptions opts = GenRetopologyOptions.create()
    .setName("my_retopo")                                // 可选，默认 "gen_retopology"
    .setAlgorithmModel("hunyuan3D-RTP-v1.5")             // 可选    
    .setOutputModelFormat(ModelFormat.FBX)               // 可选，输出格式
    .setFaceType(FaceType.QUAD)                          // 可选，面数类型（默认四边面）
    .setDetailLevel(DetailLevel.MEDIUM)                  // 可选，混元模型

// VISVISE 自研模型示例
GenRetopologyOptions opts2 = GenRetopologyOptions.create()
    .setAlgorithmModel("VISVISE-RTP-V1.0.0")
    .setFaceNum(50000)                                   // 可选，VISVISE 自研模型

String modelId = client.genRetopology("path/to/model.fbx", opts, rtx);
```

---

### GenLOD — LOD

生成多级细节模型（node_type=2），支持抽卡。默认抽卡次数为 3。→ [示例代码](src/main/java/com/visvise/sdk/examples/GenLODExample.java)

```java
ReduceFace rf1 = new ReduceFace(1, 50, FaceType.QUAD);
ReduceFace rf2 = new ReduceFace(2, 25, FaceType.QUAD);
List<ReduceFace> reduceFaces = Arrays.asList(rf1, rf2);

GenLODOptions opts = GenLODOptions.create()
    .setName("my_lod")                                  // 可选，默认 "gen_lod"
    .setOutputModelFormat(ModelFormat.FBX)               // 可选，输出格式（默认 fbx）
    .setGenTimes(3)                                     // 可选，抽卡次数（默认 3）

List<String> modelIds = client.genLOD("path/to/model.fbx", reduceFaces, opts, rtx);
```

---

### GenUV — UV展开

自动 UV 展开（node_type=9）。→ [示例代码](src/main/java/com/visvise/sdk/examples/GenUVExample.java)

```java
GenUVOptions opts = GenUVOptions.create()
    .setName("my_uv")                                    // 可选，默认 "gen_uv"
    .setEnableAutoSmoothing(true)                        // 可选，是否启用自动平滑

String modelId = client.genUV("path/to/model.fbx", opts, rtx);
```

---

### GenTexture — 贴图纹理

为模型生成贴图纹理（node_type=8）。→ [示例代码](src/main/java/com/visvise/sdk/examples/GenTextureExample.java)

> `InputView.MainView` 与 `Prompt` 至少传一个，可同时传入。

```java
View refView = new View();
refView.setMainView("path/to/ref.png");

GenTextureOptions opts = GenTextureOptions.create()
    .setName("my_texture")                              // 可选，默认 "gen_texture"
    .setInputView(refView)                              // 可选，原画视图（与 prompt 至少传一个）
    .setResolution(2048)                                // 可选，分辨率
    .setUnwarpUV(true)                                  // 可选，是否同时展开 UV
    .setPrompt("high quality, realistic")                // 可选，贴图文本提示词

String modelId = client.genTexture("path/to/model.fbx", opts, rtx);
```

---

### GenRigging — 骨骼架设

自动为模型生成骨骼（node_type=5）。SDK 自动将模型文件与参数 JSON 打包成 zip 上传，无需手动准备 zip 包。→ [示例代码](src/main/java/com/visvise/sdk/examples/GenRiggingExample.java)

```java
GenRiggingOptions opts = GenRiggingOptions.create()
    .setName("my_rigging")                               // 可选，默认 "gen_rigging"
    .setMeshCategory("humanoid")                        // 可选，"humanoid"（人形，默认）或 "tetrapod"（四足）
    .setTemplateSkeleton(skeletonPath)                   // 可选，模板骨骼（本地路径或 COS URL）

String modelId = client.genRigging("path/to/model.fbx", opts, rtx);
```

---

### GenSkinning — 蒙皮生成

自动绑定蒙皮权重（node_type=6）。SDK 自动将模型文件与参数 JSON 打包成 zip 上传。→ [示例代码](src/main/java/com/visvise/sdk/examples/GenSkinningExample.java)

```java
List<String> meshNames = Arrays.asList("Body_Mesh", "Hair_Mesh");
List<String> jointNames = Arrays.asList("Bip001", "Bip001 Pelvis");

GenSkinningOptions opts = GenSkinningOptions.create(meshNames, jointNames)
    .setName("my_skinning")                             // 可选，默认 "gen_skinning"

String modelId = client.genSkinning("path/to/rigged_model.fbx", opts, rtx);
```

---

### GenVideoMotion — 视频生动画

从视频中提取动作驱动 3D 模型（node_type=4）。→ [示例代码](src/main/java/com/visvise/sdk/examples/GenVideoMotionExample.java)

```java
GenVideoMotionOptions opts = GenVideoMotionOptions.create()
    .setName("my_video_motion")                          // 可选，默认 "gen_video_motion"
    .setOutputModelFormat(ModelFormat.FBX)               // 可选，输出格式
    .setWithHand(true)                                  // 可选，是否开启手部捕捉
    .setMultipleTrack(false)                             // 可选，是否开启多人捕捉
    .setRotateAxisAngle(0, 0, 0)                       // 可选，旋转轴角 [x, y, z]（弧度）

String modelId = client.genVideoMotion("path/to/model.fbx", "path/to/dance.mp4", opts, rtx);
```

---

### GenTextMotion — 文本生动画

通过提示词生成动画，一次返回 4 个模型供抽卡（node_type=4）。→ [示例代码](src/main/java/com/visvise/sdk/examples/GenTextMotionExample.java)

```java
GenTextMotionOptions opts = GenTextMotionOptions.create()
    .setName("my_text_motion")                           // 可选，默认 "gen_text_motion"
    .setOutputModelFormat(ModelFormat.FBX)               // 可选，输出格式

List<String> modelIds = client.genTextMotion("path/to/model.fbx", "一个人在跳街舞", opts, rtx);
// modelIds 包含 4 个 ID，等待其中你需要的那个即可
```

---

### GenPose — 图生Pose

从参考图生成 Pose 模型（最多 10 张图片）。→ [示例代码](src/main/java/com/visvise/sdk/examples/GenPoseExample.java)

```java
GenPoseOptions opts = GenPoseOptions.create()
    .setName("my_pose")                                  // 可选，默认 "gen_pose"
    .setOutputModelFormat(ModelFormat.FBX)               // 可选，输出格式

List<String> modelIds = client.genPose(
    "path/to/model.fbx",
    Arrays.asList("path/to/pose_ref_1.png", "path/to/pose_ref_2.png"),
    opts, rtx
);
```

---

### GenSegment2D — 2D 拆分

对图生 360 输出的多视图进行组件分割（node_type=14，SSE 协议）。生成的分割资产 `model_id` 可作为图生中模/低模的 `segmentModelID` 输入。→ [示例代码](src/main/java/com/visvise/sdk/examples/GenSegment2DExample.java)

```java
ThinkingCallback onThinking = content -> {
    System.out.println("[思考] " + content);
};

GenSegment2DOptions opts = GenSegment2DOptions.create()
    .setName("my_segment")                               // 可选，默认 "gen_segment_2d"
    .setAlgorithmModel("VISVISE-Seg2D-V1.0.0")          // 可选
    .setSplitType(SegmentSplitType.FRONT_VIEW)          // 可选，正视图/四视图拆分
    .setGranularity(SegmentGranularity.MEDIUM)           // 可选，颗粒度
    .setPrompt("segment by body parts")                 // 可选，自然语言描述拆分规则
    .setOnThinking(onThinking)                           // 可选，处理 thinking 事件的回调
    .setReadTimeout(120);                               // 可选，读取超时时间

String segModelId = client.genSegment2D("Model2026...", opts, rtx);
// 后续可作为 segmentModelID 传给 GenMidModel
```

---

### WaitModel — 等待完成

轮询等待异步任务完成，返回 `ModelInfo`。

```java
ModelInfo modelInfo = client.waitModel(
    "Model2026033100192028",
    WaitOptions.create()
        .setInterval(2)   // 轮询间隔（秒），默认 2
        .setTimeout(600), // 超时时长（秒），默认 600
    rtx
);

System.out.println(modelInfo.getOutputModel()); // 输出模型下载 URL
System.out.println(modelInfo.getTimeCost());    // 耗时（秒）
```

**错误：**

- `PollingTimeoutError`：超时仍未完成时抛出
- `ModelGenerationError`：模型生成失败（status=4）时抛出
- `InvalidParamsError`：轮询接口返回参数错误时立即抛出（不重试）
- 其他网络/业务错误不抛出，会打印日志并继续重试

---

## 原子 API 方法参考

通过 `client.getAPI().xxx()` 访问底层接口：

```java
VisviseAPI api = client.getAPI();

// 获取临时上传凭证
GetCosCredResult cred = api.getCosCred(false, rtx);

// 查询剩余配额
UserQuota quota = api.getUserQuota();
System.out.println(quota.getQuota(),rtx); // 剩余次数

// 拉取模型列表
ModelListResult result = api.getModelList(
    Collections.singletonList("Model2026..."),
    null, null, "", 10, 1,rtx
);

// 获取算法模型列表
List<String> algModels = api.listAlgorithmModel(NodeType.ANIMATION.getValue(), null, rtx);

// 获取下载链接
String url = api.downloadModel("Model2026...", rtx);

// 删除单个
api.deleteModel("Model2026...", rtx);

// 批量删除
api.batchDeleteModel(Arrays.asList("Model2026...", "Model2026..."), rtx);

// 去除背景
String outUrl = api.removeBackground("https://cos.../image.png", rtx);

// 文生动画提示词列表
List<String> prompts = api.getText2MotionPromptList("zh", rtx);
```

---

## 错误说明

所有 SDK 错误均继承自 `WeaverError`，可以捕获基类也可以精确捕获子类。

| 错误类 | 对应错误码 | 说明 |
|---|---|---|
| `WeaverError` | 任意 | 基础错误 |
| `NetworkError` | — | 网络连接失败、超时等 |
| `SignatureError` | 410    | 签名错误 |
| `SignatureExpiredError` | 411    | 签名过期，本地时钟与服务端偏差过大 |
| `InvalidParamsError` | 120008 | 请求参数错误 |
| `UserNotFoundError` | 120017 | 用户未找到 |
| `PermissionDeniedError` | 120018 | 用户无权限 |
| `QuotaExceededError` | 120020 | 每日配额超出上限 |
| `ProjectPermissionError` | 120027 | 项目权限未授权 |
| `ServerNetworkError` | 120028 | 服务器网络错误 |
| `ServerTimeoutError` | 120032 | 服务器处理超时 |
| `RateLimitError` | 120040 | 请求过于频繁 |
| `ModelGenerationError` | — | 模型生成失败（status=4） |
| `PollingTimeoutError` | — | WaitModel 等待超时 |

```java
import com.visvise.sdk.exceptions.*;

String rtx = System.getenv("VISVISE_RTX");
VisviseClient client = new VisviseClient("...", "...", null);

try {
    String modelId = client.gen360("image.png", Gen360Options.create(), rtx);
} catch (PollingTimeoutError e) {
    System.out.println("等待超时");
} catch (QuotaExceededError e) {
    System.out.println("今日配额已用完，明天再试");
} catch (WeaverError e) {
    System.out.println("接口错误: " + e.getMessage());
}
```

---

## 完整流程示例

### 示例一：图片 → 高模（图生360 + 图生高模）

```java
String rtx = System.getenv("VISVISE_RTX");
VisviseClient client = new VisviseClient("...", "...", null);

// Step 1: 图生360
System.out.println("Step 1: 生成多视图...");
Gen360Options opts = Gen360Options.create();
String mvId = client.gen360("character.png", opts, rtx);
ModelInfo mv = client.waitModel(mvId,
    WaitOptions.create().setInterval(3.0).setTimeout(300), rtx);
View views = mv.getImageGen360Output().getOutputView();

// Step 2: 图生高模
System.out.println("Step 2: 生成高模...");
GenHighModelOptions highOpts = GenHighModelOptions.create()
    .setBackView(views.getBackView())
    .setLeftView(views.getLeftView())
    .setRightView(views.getRightView());
String highId = client.genHighModel(views.getMainView(), highOpts, rtx);
ModelInfo highModel = client.waitModel(highId,
    WaitOptions.create().setTimeout(900), rtx);
System.out.println("高模下载地址: " + highModel.getOutputModel());
```

---

### 示例二：动画生成流水线（骨骼 → 蒙皮 → 动画）

```java
String rtx = System.getenv("VISVISE_RTX");
VisviseClient client = new VisviseClient("...", "...", null);

// Step 1: 骨骼架设
GenRiggingOptions riggingOpts = GenRiggingOptions.create();
String rigId = client.genRigging("character.fbx", riggingOpts, rtx);
ModelInfo rig = client.waitModel(rigId,
    WaitOptions.create().setTimeout(600), rtx);
System.out.println("骨骼模型: " + rig.getOutputModel());

// Step 2: 蒙皮生成
GenSkinningOptions skinningOpts = GenSkinningOptions.create(
    Arrays.asList("Body_Mesh"),
    Arrays.asList("Bip001", "Bip001 Pelvis")
);
String skinId = client.genSkinning("rigged_character.fbx", skinningOpts, rtx);
client.waitModel(skinId, WaitOptions.create().setTimeout(600), rtx);

// Step 3: 视频生动画
GenVideoMotionOptions motionOpts = GenVideoMotionOptions.create();
String animId = client.genVideoMotion("skinned_model.fbx", "dance.mp4", motionOpts, rtx);
ModelInfo anim = client.waitModel(animId,
    WaitOptions.create().setTimeout(900), rtx);
System.out.println("动画下载地址: " + anim.getOutputModel());
```

---

### 示例三：LOD 生成（含抽卡）

```java
String rtx = System.getenv("VISVISE_RTX");
VisviseClient client = new VisviseClient("...", "...", null);

ReduceFace rf1 = new ReduceFace(1, 50, FaceType.QUAD);
ReduceFace rf2 = new ReduceFace(2, 25, FaceType.QUAD);
List<ReduceFace> reduceFaces = Arrays.asList(rf1, rf2);

GenLODOptions opts = GenLODOptions.create();
List<String> modelIds = client.genLOD("high_model.fbx", reduceFaces, opts, rtx);

// 等待全部完成
for (String mid : modelIds) {
    ModelInfo r = client.waitModel(mid,
        WaitOptions.create().setTimeout(300), rtx);
    System.out.println(r.getModelId() + " " + r.getOutputModel());
}
```

---

## 许可证

MIT License
