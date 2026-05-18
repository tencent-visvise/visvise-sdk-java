# VISVISE Weaver Java SDK

**[中文](README.md)** | English

Java SDK for the VISVISE Weaver OpenAPI. It provides:

- All atomic API methods (1:1 mapping to OpenAPI endpoints)
- High-level `Gen*()` methods for each node type (auto-upload files + create tasks)
- `WaitModel()` async polling helper

---

## Table of Contents

- [Installation](#installation)
- [Quick Start](#quick-start)
- [Client Initialization](#client-initialization)
- [Enum Constants](#enum-constants)
- [High-Level Methods](#high-level-methods)
  - [Gen360 — Image to 360](#gen360--image-to-360)
  - [GenHighModel — Image to High-poly](#genhighmodel--image-to-high-poly)
  - [GenMidModel — Image to Mid-poly](#genmidmodel--image-to-mid-poly)
  - [GenLowModel — Image to Low-poly](#genlowmodel--image-to-low-poly)
  - [GenMeshRefine — Mesh Refinement](#genmeshrefine--mesh-refinement)
  - [GenRetopology — Retopology](#genretopology--retopology)
  - [GenLOD — LOD](#genlod--lod)
  - [GenUV — UV Unwrap](#genuv--uv-unwrap)
  - [GenTexture — Texture Generation](#gentexture--texture-generation)
  - [GenRigging — Rigging](#genrigging--rigging)
  - [GenSkinning — Skinning](#genskinning--skinning)
  - [GenVideoMotion — Video to Animation](#genvideomotion--video-to-animation)
  - [GenTextMotion — Text to Animation](#gentextmotion--text-to-animation)
  - [GenPose — Image to Pose](#genpose--image-to-pose)
  - [GenSegment2D — 2D Segmentation](#gensegment2d--2d-segmentation)
  - [WaitModel — Wait for Completion](#waitmodel--wait-for-completion)
- [Atomic API Methods](#atomic-api-methods)
- [Errors](#errors)
- [Full Workflow Examples](#full-workflow-examples)

---

## Installation


manually download the JAR file and add it to your project.

---

## Quick Start

```java
import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.options.*;

public class Main {
    public static void main(String[] args) {
        // Create client (rtx passed via each API call)
        String rtx = System.getenv("VISVISE_RTX");
        VisviseClient client = new VisviseClient(
            "your_app_id",
            "your_secret_key",
            null // Use default config, or pass ClientOptions
        );

        // 1) Image-to-360: upload local image, generate multi-views
        Gen360Options opts = Gen360Options.create()
            .setEnableAPose(true);  // optional
        String mvModelId = client.gen360("character.png", opts, rtx);

        // 2) Wait for completion, fetch multi-view output
        ModelInfo mvInfo = client.waitModel(mvModelId,
            WaitOptions.create().setInterval(3.0).setTimeout(300), rtx);
        View outputView = mvInfo.getImageGen360Output().getOutputView();

        // 3) Image-to-high-poly (pass COS URLs directly)
        GenHighModelOptions highOpts = GenHighModelOptions.create()
            .setBackView(outputView.getBackView())
            .setLeftView(outputView.getLeftView())
            .setRightView(outputView.getRightView());
        String highModelId = client.genHighModel(outputView.getMainView(), highOpts, rtx);

        // 4) Wait for completion
        ModelInfo modelInfo = client.waitModel(highModelId,
            WaitOptions.create().setTimeout(900), rtx);
        System.out.println("Output model: " + modelInfo.getOutputModel());
    }
}
```

---

## Client Initialization

```java
import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.enums.Environment;

// Required parameters
String rtx = System.getenv("VISVISE_RTX");  // Get from environment variable
VisviseClient client = new VisviseClient(
    "your_app_id",     // required, assigned by platform
    "your_secret_key"  // required, assigned by platform
);

// Simplified constructor (use default config)
// Default: Env.PROD, Timeout=30, Debug=false

// Custom environment example
ClientOptions opts = ClientOptions.create()
    .setEnv(Environment.DEV)   // Set environment
    .setTimeout(60)            // Set timeout (seconds)
    .setDebug(true);           // Enable debug logging
VisviseClient client = new VisviseClient(appId, secretKey, opts);
 
```

| Parameter | Required | Description |
|---|---|---|
| `appId` | ✅ | Client identifier assigned by the platform |
| `secretKey` | ✅ | Signing key assigned by the platform |
| `opts` | — | Optional parameters `ClientOptions`, null means use defaults |
| `rtx` | ✅ | User Token, passed with each API call |

### Debug Logging

Enable debug logging to output detailed HTTP request/response information:

```java
org.slf4j.simpleLogger.log.visvise.http=debug
```

Debug log output example:
```
[DEBUG] POST request: url=https://ws.visvise.com.cn/api/xxx, body={"key":"value"...}
[DEBUG] POST response: url=https://ws.visvise.com.cn/api/xxx, status=200, body={"code":0,"data":{...}}
```

Log content includes:
- HTTP request method, URL and request body
- HTTP response status code and response body
- Response body over 2000 characters will be truncated


---

## Enum Constants

The SDK exposes the following enums. Prefer them over hard-coded numbers/strings:

```java
import com.visvise.sdk.enums.*;

// Face type
FaceType.TRIANGLE  // 1 - triangle faces
FaceType.QUAD      // 2 - quad faces

// Detail level (for retopology)
DetailLevel.LOW    // 1 - low
DetailLevel.MEDIUM // 2 - medium
DetailLevel.HIGH   // 3 - high

// model format
ModelFormat.FBX  // "fbx"
ModelFormat.OBJ  // "obj"
ModelFormat.GLB  // "glb"

// Mesh refine mode
MeshRefineMode.OPTIMIZE  // 1 - mesh optimization
MeshRefineMode.DENSIFY   // 2 - mesh densification

// 2D segmentation split type
SegmentSplitType.FRONT_VIEW  // 1 - front-view split (default)
SegmentSplitType.FOUR_VIEW   // 2 - four-view split

// 2D segmentation granularity
SegmentGranularity.COARSE   // 1 - coarse
SegmentGranularity.MEDIUM   // 2 - medium (default)
SegmentGranularity.FINE     // 3 - fine

// Animation sub-type
AnimationSubType.VIDEO  // 1 - video to animation
AnimationSubType.TEXT    // 2 - text to animation

// Environment
Environment.PROD  // Production environment
Environment.TEST  // Test environment
Environment.DEV   // Development environment

// Node type
NodeType.IMG_TO_360      // 7 - Image to 360
NodeType.IMG_TO_3D_HIGH  // 3 - Image to High-poly
NodeType.ANIMATION       // 4 - Animation
// ... more node types

// Model status
ModelStatus.SUCCESS  // 3 - Generation succeeded
ModelStatus.FAILED   // 4 - Generation failed
ModelStatus.PENDING   // 1 - Waiting for generation
ModelStatus.RUNNING   // 2 - Generating
```

---

## High-Level Methods

High-level methods bundle "COS upload + async task creation" into a single call. Pass either a local file path or a VISVISE COS URL; each method returns a `model_id`.

All `Gen*()` methods use **Options struct** pattern with fluent API for cleaner optional parameter handling:

> **About `name`:** Every `Gen*` method's `name` is optional. Default values are set in `Gen*Options.create()`. Customize via `setName()`.

> **About `algorithmModel`:** Every `Gen*` method's `algorithmModel` is optional. When omitted, the SDK calls `ListAlgorithmModel` and uses the first available model for the current account.

> **About file inputs:** All file parameters (e.g. `main_view` / `model_path` / `video_path` / `input_images`) accept four forms:
> - **Local path** (`str`): the SDK uploads the file automatically.
> - **VISVISE COS URL** (`str`): pass a `https://...myqcloud.com/...` link directly; the SDK skips upload.
> - **File type** (`File`): Pass the Fileobject directly and the SDK will upload it automatically.
> - **Binary content** (`bytes` / `InputStream`): the SDK auto-detects the format via magic bytes (images PNG/JPEG/GIF/BMP/WebP/TIFF, 3D models FBX/OBJ/GLB/GLTF, videos MP4/MOV/WebM/AVI, ZIP) and uploads as `<uuid>.<sniffed-ext>` — no filename required from the caller.

### Gen360 — Image to 360

Generate 360-degree multi-views from a single image. → [Example](src/main/java/com/visvise/sdk/examples/Gen360Example.java)

```java
Gen360Options opts = Gen360Options.create()
    .setName("my_360")                                    // optional, default "gen_360"
    .setOutputModelFormat(ModelFormat.FBX)                // optional, output format (default fbx)
    .setFaceType(FaceType.TRIANGLE)                       // optional, face type (default triangle)
    .setEnableAPose(true)                                 // optional, enable A-Pose
    .setStyle(ImageGen360Style.GRAY_MODEL.getValue())     // optional, style (VISVISE proprietary models only). Must be one of ImageGen360Style: GRAY_MODEL/PHOTOREAL/Q_TOON/PIXEL — any other value will be rejected
    .setBackView(backViewPath)                            // optional, back view (local path or COS URL)
    .setLeftView(leftViewPath)                            // optional, left view
    .setRightView(rightViewPath)                          // optional, right view

String modelId = client.gen360("path/to/character.png", opts, rtx);
```

---

### GenHighModel — Image to High-poly

Generate a high-poly 3D model from images / multi-views (node_type=3). → [Example](src/main/java/com/visvise/sdk/examples/GenHighModelExample.java)

```java
GenHighModelOptions opts = GenHighModelOptions.create()
    .setName("my_high_model")                            // optional, default "gen_high_model"
    .setOutputModelFormat(ModelFormat.FBX)               // optional, output format (default fbx)
    .setFaceType(FaceType.TRIANGLE)                      // optional, face type (default triangle)
    .setFaceNum(500000)                                 // optional, target face count (1000-1500000)
    .setBackView(backView)                               // optional, back view (local path or COS URL)
    .setLeftView(leftView)                              // optional, left view
    .setRightView(rightView)                             // optional, right view

String modelId = client.genHighModel("path/to/main.png", opts, rtx);
```

---

### GenMidModel — Image to Mid-poly

Mid-poly generation requires all four views (node_type=11). → [Example](src/main/java/com/visvise/sdk/examples/GenMidModelExample.java)

```java
GenMidModelOptions opts = GenMidModelOptions.create()
    .setName("my_mid_model")                             // optional, default "gen_mid_model"
    .setOutputModelFormat(ModelFormat.FBX)               // optional, output format
    .setFaceType(FaceType.TRIANGLE)                      // optional, face type
    .setSegmentModelId("Model2026...")                   // optional, 2D segmentation asset ID

// mainView, backView, leftView, rightView are all required
String modelId = client.genMidModel(
    "path/to/main.png",
    "path/to/back.png",
    "path/to/left.png",
    "path/to/right.png",
    opts, rtx
);
```

---

### GenLowModel — Image to Low-poly

Low-poly only needs the main view (node_type=13). → [Example](src/main/java/com/visvise/sdk/examples/GenLowModelExample.java)

```java
GenLowModelOptions opts = GenLowModelOptions.create()
    .setName("my_low_model")                             // optional, default "gen_low_model"
    .setOutputModelFormat(ModelFormat.FBX)               // optional, output format
    .setFaceType(FaceType.TRIANGLE)                      // optional, face type
    .setBackView(backView)                               // optional, back view (local path or COS URL)
    .setLeftView(leftView)                               // optional, left view
    .setRightView(rightView)                             // optional, right view

String modelId = client.genLowModel("path/to/main.png", opts, rtx);
```

---

### GenMeshRefine — Mesh Refinement

Mesh-line refinement (node_type=10). → [Example](src/main/java/com/visvise/sdk/examples/GenMeshRefineExample.java)

```java
GenMeshRefineOptions opts = GenMeshRefineOptions.create()
    .setName("my_mesh_refine")                          // optional, default "gen_mesh_refine"
    .setInputModelFormat(ModelFormat.FBX)                // optional, input format (default fbx)
    .setMode(MeshRefineMode.OPTIMIZE)                  // optional, refine mode
    .setColorModel(colorModelPath)                       // optional, color model (local path or COS URL)

String modelId = client.genMeshRefine("path/to/model.fbx", opts, rtx);
```

---

### GenRetopology — Retopology

Retopology of high-poly models (node_type=1). → [Example](src/main/java/com/visvise/sdk/examples/GenRetopologyExample.java)

> Note: pass `DetailLevel` for Hunyuan models, `FaceNum` for VISVISE proprietary models — choose one.

```java
// Hunyuan model example
GenRetopologyOptions opts = GenRetopologyOptions.create()
    .setName("my_retopo")                               // optional, default "gen_retopology"
    .setAlgorithmModel("hunyuan3D-RTP-v1.5")             // optional
    .setOutputModelFormat(ModelFormat.FBX)               // optional, output format
    .setFaceType(FaceType.QUAD)                         // optional, face type (default quad)
    .setDetailLevel(DetailLevel.MEDIUM)                  // optional, for Hunyuan models

// VISVISE proprietary model example
GenRetopologyOptions opts2 = GenRetopologyOptions.create()
    .setAlgorithmModel("VISVISE-RTP-V1.0.0")
    .setFaceNum(50000)                                 // optional, for VISVISE models

String modelId = client.genRetopology("path/to/model.fbx", opts, rtx);
```

---

### GenLOD — LOD

Generate level-of-detail meshes (node_type=2), with multi-shot support. Default generation times is 3. → [Example](src/main/java/com/visvise/sdk/examples/GenLODExample.java)

```java
ReduceFace rf1 = new ReduceFace(1, 50, FaceType.QUAD);
ReduceFace rf2 = new ReduceFace(2, 25, FaceType.QUAD);
List<ReduceFace> reduceFaces = Arrays.asList(rf1, rf2);

GenLODOptions opts = GenLODOptions.create()
    .setName("my_lod")                                  // optional, default "gen_lod"
    .setOutputModelFormat(ModelFormat.FBX)               // optional, output format (default fbx)
    .setGenTimes(3)                                     // optional, number of generations (default 3)

List<String> modelIds = client.genLOD("path/to/model.fbx", reduceFaces, opts, rtx);
```

---

### GenUV — UV Unwrap

Automatic UV unwrap (node_type=9). → [Example](src/main/java/com/visvise/sdk/examples/GenUVExample.java)

```java
GenUVOptions opts = GenUVOptions.create()
    .setName("my_uv")                                    // optional, default "gen_uv"
    .setEnableAutoSmoothing(true)                        // optional, enable auto-smoothing

String modelId = client.genUV("path/to/model.fbx", opts, rtx);
```

---

### GenTexture — Texture Generation

Generate textures for a model (node_type=8). → [Example](src/main/java/com/visvise/sdk/examples/GenTextureExample.java)

> At least one of `InputView.MainView` or `Prompt` is required; both can be supplied together.

```java
View refView = new View();
refView.setMainView("path/to/ref.png");

GenTextureOptions opts = GenTextureOptions.create()
    .setName("my_texture")                              // optional, default "gen_texture"
    .setInputView(refView)                             // optional, reference view (or use prompt)
    .setResolution(2048)                               // optional, resolution
    .setUnwarpUV(true)                                 // optional, also unwrap UV
    .setPrompt("high quality, realistic")                // optional, text prompt

String modelId = client.genTexture("path/to/model.fbx", opts, rtx);
```

---

### GenRigging — Rigging

Auto-rigging (node_type=5). The SDK packages the raw model + JSON parameters into a zip automatically — no manual zipping required. → [Example](src/main/java/com/visvise/sdk/examples/GenRiggingExample.java)

```java
GenRiggingOptions opts = GenRiggingOptions.create()
    .setName("my_rigging")                              // optional, default "gen_rigging"
    .setMeshCategory("humanoid")                        // optional, "humanoid" (default) or "tetrapod"
    .setTemplateSkeleton(skeletonPath)                   // optional, template skeleton (local path or COS URL)

String modelId = client.genRigging("path/to/model.fbx", opts, rtx);
```

---

### GenSkinning — Skinning

Auto-skinning (node_type=6). The SDK packages the rigged model + selection JSON into a zip automatically. → [Example](src/main/java/com/visvise/sdk/examples/GenSkinningExample.java)

```java
List<String> meshNames = Arrays.asList("Body_Mesh", "Hair_Mesh");
List<String> jointNames = Arrays.asList("Bip001", "Bip001 Pelvis");

GenSkinningOptions opts = GenSkinningOptions.create(meshNames, jointNames)
    .setName("my_skinning")                            // optional, default "gen_skinning"

String modelId = client.genSkinning("path/to/rigged_model.fbx", opts, rtx);
```

---

### GenVideoMotion — Video to Animation

Drive a 3D model from motion extracted from a video (node_type=4). → [Example](src/main/java/com/visvise/sdk/examples/GenVideoMotionExample.java)

```java
GenVideoMotionOptions opts = GenVideoMotionOptions.create()
    .setName("my_video_motion")                         // optional, default "gen_video_motion"
    .setOutputModelFormat(ModelFormat.FBX)               // optional, output format (default fbx)
    .setWithHand(true)                                 // optional, enable hand capture
    .setMultipleTrack(false)                             // optional, enable multi-person capture
    .setRotateAxisAngle(0, 0, 0)                       // optional, rotation axis-angle [x, y, z] (radians)

String modelId = client.genVideoMotion("path/to/model.fbx", "path/to/dance.mp4", opts, rtx);
```

---

### GenTextMotion — Text to Animation

Generate animation from a text prompt; returns 4 candidate models (node_type=4). → [Example](src/main/java/com/visvise/sdk/examples/GenTextMotionExample.java)

```java
GenTextMotionOptions opts = GenTextMotionOptions.create()
    .setName("my_text_motion")                          // optional, default "gen_text_motion"
    .setOutputModelFormat(ModelFormat.FBX)               // optional, output format

List<String> modelIds = client.genTextMotion("path/to/model.fbx", "a person breakdancing", opts, rtx);
// modelIds contains 4 IDs, wait for whichever you prefer
```

---

### GenPose — Image to Pose

Generate pose models from reference images (up to 10). → [Example](src/main/java/com/visvise/sdk/examples/GenPoseExample.java)

```java
GenPoseOptions opts = GenPoseOptions.create()
    .setName("my_pose")                                 // optional, default "gen_pose"
    .setOutputModelFormat(ModelFormat.FBX)               // optional, output format

List<String> modelIds = client.genPose(
    "path/to/model.fbx",
    Arrays.asList("path/to/pose_ref_1.png", "path/to/pose_ref_2.png"),
    opts, rtx
);
```

---

### GenSegment2D — 2D Segmentation

Component segmentation over multi-views from Gen360 (node_type=14, SSE protocol). The resulting `model_id` can be passed as `segmentModelID` for `GenMidModel` / `GenLowModel`. → [Example](src/main/java/com/visvise/sdk/examples/GenSegment2DExample.java)

```java
ThinkingCallback onThinking = content -> {
    System.out.println("[thinking] " + content);
};

GenSegment2DOptions opts = GenSegment2DOptions.create()
    .setName("my_segment")                              // optional, default "gen_segment_2d"
    .setSplitType(SegmentSplitType.FRONT_VIEW)          // optional, split type
    .setGranularity(SegmentGranularity.MEDIUM)           // optional, granularity
    .setPrompt("segment by body parts")                 // optional, natural language prompt
    .setOnThinking(onThinking)                           // optional, thinking callback
    .setReadTimeout(120);                               // optional, read timeout

String segModelId = client.genSegment2D("Model2026...", opts, rtx);
// Use the result as segmentModelID for GenMidModel
```

---

### WaitModel — Wait for Completion

Poll until an async task finishes; returns `ModelInfo`.

```java
ModelInfo modelInfo = client.waitModel(
    "Model2026033100192028",
    WaitOptions.create()
        .setInterval(2)    // poll interval in seconds (default 2)
        .setTimeout(600),  // max wait in seconds (default 600)
    rtx
);

System.out.println(modelInfo.getOutputModel()); // output model URL
System.out.println(modelInfo.getTimeCost());     // elapsed seconds
```

**Errors:**

- `PollingTimeoutError` — raised when the timeout is reached
- `ModelGenerationError` — raised when the task fails (status=4)
- `InvalidParamsError` — raised immediately on parameter errors during polling (no retry)
- Other network/business errors are **silently retried**

---

## Atomic API Methods

Access low-level endpoints via `client.getAPI().xxx()`:

```java
VisviseAPI api = client.getAPI();

// Get temporary upload credentials
GetCosCredResult cred = api.getCosCred(false, rtx);

// Query remaining quota
UserQuota quota = api.getUserQuota();
System.out.println(quota.getQuota(), rtx); // remaining count

// Fetch model list
ModelListResult result = api.getModelList(
    Collections.singletonList("Model2026..."),
    null, null, "", 10, 1, rtx
);

// Fetch algorithm models for a node type
List<String> algModels = api.listAlgorithmModel(NodeType.ANIMATION.getValue(), null, rtx);

// Get download URL
String url = api.downloadModel("Model2026...", rtx);

// Delete a single model
api.deleteModel("Model2026...", rtx);

// Batch delete
api.batchDeleteModel(Arrays.asList("Model2026...", "Model2026..."), rtx);

// Remove background
String outUrl = api.removeBackground("https://cos.../image.png", rtx);

// Text-to-motion prompt suggestions
List<String> prompts = api.getText2MotionPromptList("en", rtx);
```

---

## Errors

All SDK errors inherit from `WeaverError`; you can catch the base class or any subclass.

| Error | Code | Description |
|---|---|---|
| `WeaverError` | any | Base error |
| `NetworkError` | — | Connection / timeout errors |
| `SignatureError` | 410    | Signature failure |
| `SignatureExpiredError` | 411    | Signature expired (clock skew between client and server) |
| `InvalidParamsError` | 120008 | Invalid request parameters |
| `UserNotFoundError` | 120017 | User not found |
| `PermissionDeniedError` | 120018 | Permission denied |
| `QuotaExceededError` | 120020 | Daily quota exceeded |
| `ProjectPermissionError` | 120027 | Project permission missing |
| `ServerNetworkError` | 120028 | Server network error |
| `ServerTimeoutError` | 120032 | Server processing timeout |
| `RateLimitError` | 120040 | Too many requests |
| `ModelGenerationError` | — | Task failed (status=4) |
| `PollingTimeoutError` | — | WaitModel timed out |

```java
import com.visvise.sdk.exceptions.*;

String rtx = System.getenv("VISVISE_RTX");
VisviseClient client = new VisviseClient("...", "...", null);

try {
    String modelId = client.gen360("image.png", Gen360Options.create(), rtx);
} catch (PollingTimeoutError e) {
    System.out.println("Timeout waiting for model");
} catch (QuotaExceededError e) {
    System.out.println("Daily quota exceeded; please try again tomorrow");
} catch (WeaverError e) {
    System.out.println("API error: " + e.getMessage());
}
```

---

## Full Workflow Examples

### Example 1: Image → High-poly (Gen360 + GenHighModel)

```java
String rtx = System.getenv("VISVISE_RTX");
VisviseClient client = new VisviseClient("...", "...", null);

// Step 1: Image-to-360
System.out.println("Step 1: generating multi-views...");
Gen360Options opts = Gen360Options.create();
String mvId = client.gen360("character.png", opts, rtx);
ModelInfo mv = client.waitModel(mvId,
    WaitOptions.create().setInterval(3.0).setTimeout(300), rtx);
View views = mv.getImageGen360Output().getOutputView();

// Step 2: High-poly model
System.out.println("Step 2: generating high-poly model...");
GenHighModelOptions highOpts = GenHighModelOptions.create()
    .setBackView(views.getBackView())
    .setLeftView(views.getLeftView())
    .setRightView(views.getRightView());
String highId = client.genHighModel(views.getMainView(), highOpts, rtx);
ModelInfo highModel = client.waitModel(highId,
    WaitOptions.create().setTimeout(900), rtx);
System.out.println("High-poly download URL: " + highModel.getOutputModel());
```

---

### Example 2: Animation pipeline (Rigging → Skinning → Animation)

```java
String rtx = System.getenv("VISVISE_RTX");
VisviseClient client = new VisviseClient("...", "...", null);

// Step 1: Rigging
GenRiggingOptions riggingOpts = GenRiggingOptions.create();
String rigId = client.genRigging("character.fbx", riggingOpts, rtx);
ModelInfo rig = client.waitModel(rigId,
    WaitOptions.create().setTimeout(600), rtx);
System.out.println("Rigged model: " + rig.getOutputModel());

// Step 2: Skinning
GenSkinningOptions skinningOpts = GenSkinningOptions.create(
    Arrays.asList("Body_Mesh"),
    Arrays.asList("Bip001", "Bip001 Pelvis")
);
String skinId = client.genSkinning("rigged_character.fbx", skinningOpts, rtx);
client.waitModel(skinId, WaitOptions.create().setTimeout(600), rtx);

// Step 3: Video-to-animation
GenVideoMotionOptions motionOpts = GenVideoMotionOptions.create();
String animId = client.genVideoMotion("skinned_model.fbx", "dance.mp4", motionOpts, rtx);
ModelInfo anim = client.waitModel(animId,
    WaitOptions.create().setTimeout(900), rtx);
System.out.println("Animation download URL: " + anim.getOutputModel());
```

---

### Example 3: LOD generation (with multi-shot)

```java
String rtx = System.getenv("VISVISE_RTX");
VisviseClient client = new VisviseClient("...", "...", null);

ReduceFace rf1 = new ReduceFace(1, 50, FaceType.QUAD);
ReduceFace rf2 = new ReduceFace(2, 25, FaceType.QUAD);
List<ReduceFace> reduceFaces = Arrays.asList(rf1, rf2);

GenLODOptions opts = GenLODOptions.create();
List<String> modelIds = client.genLOD("high_model.fbx", reduceFaces, opts, rtx);

// Wait for all variants
for (String mid : modelIds) {
    ModelInfo r = client.waitModel(mid,
        WaitOptions.create().setTimeout(300), rtx);
    System.out.println(r.getModelId() + " " + r.getOutputModel());
}
```

---

## License

MIT License
