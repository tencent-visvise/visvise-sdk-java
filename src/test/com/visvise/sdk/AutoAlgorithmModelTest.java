package com.visvise.sdk;

import com.visvise.sdk.enums.*;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.models.ReduceFace;
import com.visvise.sdk.models.View;
import com.visvise.sdk.options.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for auto algorithm model selection (without specifying algorithm_model)
 */
public class AutoAlgorithmModelTest {

    private String appId;
    private String secretKey;
    private String uid;
    private VisviseClient client;

    private static final String ASSETS_DIR = "src/test/resources/assets";

    @Before
    public void setUp() {
        appId = System.getenv("VISVISE_APP_ID");
        secretKey = System.getenv("VISVISE_SECRET_KEY");
        uid = System.getenv("VISVISE_UID");

        if (appId != null && secretKey != null && uid != null) {
            ClientOptions opts = ClientOptions.create()
                    .setEnv(Environment.DEV)
                    .setDebug(true);
            client = new VisviseClient(appId, secretKey, uid, opts);
        }
    }

    private boolean isConfigured() {
        return appId != null && !appId.isEmpty()
                && secretKey != null && !secretKey.isEmpty()
                && uid != null && !uid.isEmpty();
    }

    // TestAutoAlgorithmModel_Gen360 tests gen_360 without algorithm_model parameter
    @Test
    @Ignore("Requires test assets and API credentials")
    public void testGen360WithoutAlgorithmModel() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File mainViewFile = new File(ASSETS_DIR, "main_view.png");
        if (!mainViewFile.exists()) {
            System.out.println("Skipping test: main_view.png not found");
            return;
        }

        try {
            Gen360Options opts = Gen360Options.create();

            String modelId = client.gen360(mainViewFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: gen_360 (no algorithm_model) - model_id=" + modelId);
        } catch (Exception e) {
            throw new AssertionError("Gen360 without algorithm_model failed: " + e.getMessage());
        }
    }

    // TestAutoAlgorithmModel_GenHighModel tests gen_high_model without algorithm_model parameter
    @Test
    @Ignore("Requires test assets and API credentials")
    public void testGenHighModelWithoutAlgorithmModel() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File mainViewFile = new File(ASSETS_DIR, "main_view.png");
        if (!mainViewFile.exists()) {
            System.out.println("Skipping test: main_view.png not found");
            return;
        }

        try {
            GenHighModelOptions opts = GenHighModelOptions.create()
                    .setOutputModelFormat(OutputModelFormat.FBX)
                    .setFaceType(FaceType.TRIANGLE);

            String modelId = client.genHighModel(mainViewFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: gen_high_model (no algorithm_model) - model_id=" + modelId);
        } catch (Exception e) {
            throw new AssertionError("GenHighModel without algorithm_model failed: " + e.getMessage());
        }
    }

    // TestAutoAlgorithmModel_GenMidModel tests gen_mid_model without algorithm_model parameter
    @Test
    @Ignore("Requires test assets and API credentials")
    public void testGenMidModelWithoutAlgorithmModel() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File mainViewFile = new File(ASSETS_DIR, "main_view.png");
        File backViewFile = new File(ASSETS_DIR, "back_view.png");
        File leftViewFile = new File(ASSETS_DIR, "left_view.png");
        File rightViewFile = new File(ASSETS_DIR, "right_view.png");
        if (!mainViewFile.exists()) {
            System.out.println("Skipping test: main_view.png not found");
            return;
        }

        try {
            GenMidModelOptions opts = GenMidModelOptions.create()
                    .setOutputModelFormat(OutputModelFormat.FBX)
                    .setFaceType(FaceType.TRIANGLE);

            String modelId = client.genMidModel(
                    mainViewFile.getAbsolutePath(),
                    backViewFile.exists() ? backViewFile.getAbsolutePath() : "",
                    leftViewFile.exists() ? leftViewFile.getAbsolutePath() : "",
                    rightViewFile.exists() ? rightViewFile.getAbsolutePath() : "",
                    opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: gen_mid_model (no algorithm_model) - model_id=" + modelId);
        } catch (Exception e) {
            throw new AssertionError("GenMidModel without algorithm_model failed: " + e.getMessage());
        }
    }

    // TestAutoAlgorithmModel_GenLowModel tests gen_low_model without algorithm_model parameter
    @Test
    @Ignore("Requires test assets and API credentials")
    public void testGenLowModelWithoutAlgorithmModel() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File mainViewFile = new File(ASSETS_DIR, "main_view.png");
        if (!mainViewFile.exists()) {
            System.out.println("Skipping test: main_view.png not found");
            return;
        }

        try {
            GenLowModelOptions opts = GenLowModelOptions.create()
                    .setOutputModelFormat(OutputModelFormat.FBX)
                    .setFaceType(FaceType.TRIANGLE);

            String modelId = client.genLowModel(mainViewFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: gen_low_model (no algorithm_model) - model_id=" + modelId);
        } catch (Exception e) {
            throw new AssertionError("GenLowModel without algorithm_model failed: " + e.getMessage());
        }
    }

    // TestAutoAlgorithmModel_GenMeshRefine tests gen_mesh_refine without algorithm_model parameter
    @Test
    @Ignore("Requires test assets and API credentials")
    public void testGenMeshRefineWithoutAlgorithmModel() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "high_model.fbx");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: high_model.fbx not found");
            return;
        }

        try {
            GenMeshRefineOptions opts = GenMeshRefineOptions.create();

            String modelId = client.genMeshRefine(modelFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: gen_mesh_refine (no algorithm_model) - model_id=" + modelId);
        } catch (Exception e) {
            throw new AssertionError("GenMeshRefine without algorithm_model failed: " + e.getMessage());
        }
    }

    // TestAutoAlgorithmModel_GenRetopology tests gen_retopology without algorithm_model parameter
    @Test
    @Ignore("Requires test assets and API credentials")
    public void testGenRetopologyWithoutAlgorithmModel() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "high_model.fbx");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: high_model.fbx not found");
            return;
        }

        try {
            GenRetopologyOptions opts = GenRetopologyOptions.create()
                    .setOutputModelFormat(OutputModelFormat.FBX)
                    .setFaceType(FaceType.QUAD)
                    .setDetailLevel(DetailLevel.MEDIUM);

            String modelId = client.genRetopology(modelFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: gen_retopology (no algorithm_model) - model_id=" + modelId);
        } catch (Exception e) {
            throw new AssertionError("GenRetopology without algorithm_model failed: " + e.getMessage());
        }
    }

    // TestAutoAlgorithmModel_GenLOD tests gen_lod without algorithm_model parameter
    @Test
    @Ignore("Requires test assets and API credentials")
    public void testGenLODWithoutAlgorithmModel() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "high_model.fbx");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: high_model.fbx not found");
            return;
        }

        try {
            List<ReduceFace> reduceFaces = Arrays.asList(
                    new ReduceFace(1, 50, FaceType.QUAD));
            GenLODOptions opts = GenLODOptions.create()
                    .setOutputModelFormat(OutputModelFormat.FBX)
                    .setGenTimes(1);

            List<String> modelIds = client.genLOD(modelFile.getAbsolutePath(), reduceFaces, opts);
            assertNotNull("Model IDs should not be null", modelIds);
            System.out.println("PASS: gen_lod (no algorithm_model) - model_ids=" + modelIds);
        } catch (Exception e) {
            throw new AssertionError("GenLOD without algorithm_model failed: " + e.getMessage());
        }
    }

    // TestAutoAlgorithmModel_GenUV tests gen_uv without algorithm_model parameter
    @Test
    @Ignore("Requires test assets and API credentials")
    public void testGenUVWithoutAlgorithmModel() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "high_model.fbx");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: high_model.fbx not found");
            return;
        }

        try {
            GenUVOptions opts = GenUVOptions.create()
                    .setEnableAutoSmoothing(false);

            String modelId = client.genUV(modelFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: gen_uv (no algorithm_model) - model_id=" + modelId);
        } catch (Exception e) {
            throw new AssertionError("GenUV without algorithm_model failed: " + e.getMessage());
        }
    }

    // TestAutoAlgorithmModel_GenTexture tests gen_texture without algorithm_model parameter
    @Test
    @Ignore("Requires test assets and API credentials")
    public void testGenTextureWithoutAlgorithmModel() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "high_model.fbx");
        File mainViewFile = new File(ASSETS_DIR, "main_view.png");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: high_model.fbx not found");
            return;
        }

        try {
            View view = new View();
            if (mainViewFile.exists()) {
                view.setMainView(mainViewFile.getAbsolutePath());
            }

            GenTextureOptions opts = GenTextureOptions.create()
                    .setInputView(view);

            String modelId = client.genTexture(modelFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: gen_texture (no algorithm_model) - model_id=" + modelId);
        } catch (Exception e) {
            throw new AssertionError("GenTexture without algorithm_model failed: " + e.getMessage());
        }
    }

    // TestAutoAlgorithmModel_GenRigging tests gen_rigging without algorithm_model parameter
    @Test
    @Ignore("Requires test assets and API credentials")
    public void testGenRiggingWithoutAlgorithmModel() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "high_model.fbx");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: high_model.fbx not found");
            return;
        }

        try {
            GenRiggingOptions opts = GenRiggingOptions.create();

            String modelId = client.genRigging(modelFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: gen_rigging (no algorithm_model) - model_id=" + modelId);
        } catch (Exception e) {
            throw new AssertionError("GenRigging without algorithm_model failed: " + e.getMessage());
        }
    }

    // TestAutoAlgorithmModel_GenSkinning tests gen_skinning without algorithm_model parameter
    @Test
    @Ignore("Requires test assets and API credentials")
    public void testGenSkinningWithoutAlgorithmModel() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "skinning_model.fbx");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: skinning_model.fbx not found");
            return;
        }

        try {
            GenSkinningOptions opts = GenSkinningOptions.create(
                    Arrays.asList("Body_Mesh"),
                    Arrays.asList("Bip001", "Bip001 Pelvis"));

            String modelId = client.genSkinning(modelFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: gen_skinning (no algorithm_model) - model_id=" + modelId);
        } catch (Exception e) {
            throw new AssertionError("GenSkinning without algorithm_model failed: " + e.getMessage());
        }
    }

    // TestAutoAlgorithmModel_GenVideoMotion tests gen_video_motion without algorithm_model parameter
    @Test
    @Ignore("Requires test assets and API credentials")
    public void testGenVideoMotionWithoutAlgorithmModel() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "animation_model.fbx");
        File videoFile = new File(ASSETS_DIR, "animation_video.mp4");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: animation_model.fbx not found");
            return;
        }

        try {
            GenVideoMotionOptions opts = GenVideoMotionOptions.create()
                    .setOutputModelFormat(OutputModelFormat.FBX)
                    .setWithHand(false)
                    .setMultipleTrack(false);

            String modelId = client.genVideoMotion(modelFile.getAbsolutePath(),
                    videoFile.exists() ? videoFile.getAbsolutePath() : "", opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: gen_video_motion (no algorithm_model) - model_id=" + modelId);
        } catch (Exception e) {
            throw new AssertionError("GenVideoMotion without algorithm_model failed: " + e.getMessage());
        }
    }

    // TestAutoAlgorithmModel_GenTextMotion tests gen_text_motion without algorithm_model parameter
    @Test
    @Ignore("Requires test assets and API credentials")
    public void testGenTextMotionWithoutAlgorithmModel() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "animation_model.fbx");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: animation_model.fbx not found");
            return;
        }

        try {
            GenTextMotionOptions opts = GenTextMotionOptions.create();

            List<String> modelIds = client.genTextMotion(modelFile.getAbsolutePath(), "一个人在原地踏步", opts);
            assertNotNull("Model IDs should not be null", modelIds);
            System.out.println("PASS: gen_text_motion (no algorithm_model) - model_ids=" + modelIds);
        } catch (Exception e) {
            throw new AssertionError("GenTextMotion without algorithm_model failed: " + e.getMessage());
        }
    }

    // TestAutoAlgorithmModel_GenPose tests gen_pose without algorithm_model parameter
    @Test
    @Ignore("Requires test assets and API credentials")
    public void testGenPoseWithoutAlgorithmModel() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "animation_model.fbx");
        File mainViewFile = new File(ASSETS_DIR, "main_view.png");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: animation_model.fbx not found");
            return;
        }

        try {
            GenPoseOptions opts = GenPoseOptions.create();

            List<String> images = mainViewFile.exists()
                    ? Collections.singletonList(mainViewFile.getAbsolutePath())
                    : Collections.emptyList();

            List<String> modelIds = client.genPose(modelFile.getAbsolutePath(), images, opts);
            assertNotNull("Model IDs should not be null", modelIds);
            System.out.println("PASS: gen_pose (no algorithm_model) - model_ids=" + modelIds);
        } catch (Exception e) {
            throw new AssertionError("GenPose without algorithm_model failed: " + e.getMessage());
        }
    }
}
