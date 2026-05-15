package com.visvise.sdk;

import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.models.View;
import com.visvise.sdk.options.*;
import com.visvise.sdk.enums.OutputModelFormat;
import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.DetailLevel;
import com.visvise.sdk.enums.MeshRefineMode;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotNull;

/**
 * Batch 2 tests for GenMidModel, GenRetopology, GenMeshRefine, GenUV, GenTexture
 */
public class Batch2Test {

    private String appId;
    private String secretKey;
    private String uid;
    private VisviseClient client;

    private static final String ASSETS_DIR = "src/test/resources/assets";
    private static final String MV_BASE = "https://visvise-weaver-bj-rel-1311802504.cos.accelerate.myqcloud.com/weaver/user-p_5sxfmuvwtfj58ssbt97q2k2kc83697p/Model2026042300225069";

    @Before
    public void setUp() {
        appId = System.getenv("VISVISE_APP_ID");
        secretKey = System.getenv("VISVISE_SECRET_KEY");
        uid = System.getenv("VISVISE_UID");

        if (appId != null && secretKey != null && uid != null) {
            client = new VisviseClient(appId, secretKey, uid);
        }
    }

    private boolean isConfigured() {
        return appId != null && !appId.isEmpty()
                && secretKey != null && !secretKey.isEmpty()
                && uid != null && !uid.isEmpty();
    }

    @Test
    public void testMidModelFaceType1() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        try {
            GenMidModelOptions opts = GenMidModelOptions.create()
                    .setAlgorithmModel("VISVISE-MeshGen-V1.0.0")
                    .setOutputModelFormat(OutputModelFormat.FBX)
                    .setFaceType(FaceType.TRIANGLE);

            String modelId = client.genMidModel(
                    MV_BASE + "/example_gen_360_MultiView(2)_MainView.png",
                    MV_BASE + "/example_gen_360_MultiView(2)_BackView.png",
                    MV_BASE + "/example_gen_360_MultiView(2)_LeftView.png",
                    MV_BASE + "/example_gen_360_MultiView(2)_RightView.png",
                    opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: mid face_type=1 fbx - model_id=" + modelId);

            // Wait for completion
            ModelInfo model = client.waitModel(modelId,  WaitOptions.create().setInterval(5).setTimeout(900));
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenMidModel face_type=1 fbx failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testMidModelFaceType2() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        try {
            GenMidModelOptions opts = GenMidModelOptions.create()
                    .setAlgorithmModel("VISVISE-MeshGen-V1.0.0")
                    .setOutputModelFormat(OutputModelFormat.FBX)
                    .setFaceType(FaceType.QUAD);

            String modelId = client.genMidModel(
                    MV_BASE + "/example_gen_360_MultiView(2)_MainView.png",
                    MV_BASE + "/example_gen_360_MultiView(2)_BackView.png",
                    MV_BASE + "/example_gen_360_MultiView(2)_LeftView.png",
                    MV_BASE + "/example_gen_360_MultiView(2)_RightView.png",
                    opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: mid face_type=2 fbx - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenMidModel face_type=2 fbx failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testRetopologyDetailLevel2Face2() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "tex_model.obj");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: tex_model.obj not found");
            return;
        }

        try {
            GenRetopologyOptions opts = GenRetopologyOptions.create()
                    .setAlgorithmModel("hunyuan3D-RTP-v1.5")
                    .setOutputModelFormat(OutputModelFormat.FBX)
                    .setFaceType(FaceType.QUAD)
                    .setDetailLevel(DetailLevel.MEDIUM);

            String modelId = client.genRetopology(modelFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: rtp detail=2 face=2 - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenRetopology detail=2 face=2 failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testRetopologyDetailLevel3Face1() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "tex_model.obj");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: tex_model.obj not found");
            return;
        }

        try {
            GenRetopologyOptions opts = GenRetopologyOptions.create()
                    .setAlgorithmModel("hunyuan3D-RTP-v1.5")
                    .setOutputModelFormat(OutputModelFormat.FBX)
                    .setFaceType(FaceType.TRIANGLE)
                    .setDetailLevel(DetailLevel.HIGH);

            String modelId = client.genRetopology(modelFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: rtp detail=3 face=1 - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenRetopology detail=3 face=1 failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testMeshRefinePreserveTrue() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "tex_model.obj");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: tex_model.obj not found");
            return;
        }

        try {
            GenMeshRefineOptions opts = GenMeshRefineOptions.create()
                    .setAlgorithmModel("VISVISE-MeshRefine-V1.0.0")
                    .setMode(MeshRefineMode.OPTIMIZE);

            String modelId = client.genMeshRefine(modelFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: mr mode=optimize - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenMeshRefine mode=optimize failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testMeshRefinePreserveFalse() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "tex_model.obj");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: tex_model.obj not found");
            return;
        }

        try {
            GenMeshRefineOptions opts = GenMeshRefineOptions.create()
                    .setAlgorithmModel("VISVISE-MeshRefine-V1.0.0")
                    .setMode(MeshRefineMode.DENSIFY);

            String modelId = client.genMeshRefine(modelFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: mr mode=densify - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenMeshRefine mode=densify failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testUVSmoothTrue() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "tex_model.obj");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: tex_model.obj not found");
            return;
        }

        try {
            GenUVOptions opts = GenUVOptions.create()
                    .setAlgorithmModel("hunyuan3D-UV-v2.0")
                    .setEnableAutoSmoothing(true);

            String modelId = client.genUV(modelFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: uv smooth=True - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenUV smooth=True failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testUVSmoothFalse() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "tex_model.obj");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: tex_model.obj not found");
            return;
        }

        try {
            GenUVOptions opts = GenUVOptions.create()
                    .setAlgorithmModel("hunyuan3D-UV-v2.0")
                    .setEnableAutoSmoothing(false);

            String modelId = client.genUV(modelFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: uv smooth=False - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenUV smooth=False failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testTextureRes1024() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "tex_model.obj");
        File refFrontFile = new File(ASSETS_DIR, "tex_ref_front.jpg");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: tex_model.obj not found");
            return;
        }

        try {
            View view = new View();
            if (refFrontFile.exists()) {
                view.setMainView(refFrontFile.getAbsolutePath());
            }

            GenTextureOptions opts = GenTextureOptions.create()
                    .setAlgorithmModel("hunyuan3D-TEX-v2.0")
                    .setInputView(view)
                    .setResolution(1024)
                    .setUnwarpUV(false);

            String modelId = client.genTexture(modelFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: tex res=1024 - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenTexture res=1024 failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testTextureRes2048() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "tex_model.obj");
        File refFrontFile = new File(ASSETS_DIR, "tex_ref_front.jpg");
        if (!modelFile.exists()) {
            System.out.println("Skipping test: tex_model.obj not found");
            return;
        }

        try {
            View view = new View();
            if (refFrontFile.exists()) {
                view.setMainView(refFrontFile.getAbsolutePath());
            }

            GenTextureOptions opts = GenTextureOptions.create()
                    .setAlgorithmModel("hunyuan3D-TEX-v2.0")
                    .setInputView(view)
                    .setResolution(2048)
                    .setUnwarpUV(true);

            String modelId = client.genTexture(modelFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: tex res=2048 uv=True - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenTexture res=2048 uv=True failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }
}
