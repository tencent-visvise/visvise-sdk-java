package com.visvise.sdk;

import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.models.ReduceFace;
import com.visvise.sdk.options.Gen360Options;
import com.visvise.sdk.options.GenHighModelOptions;
import com.visvise.sdk.options.GenLowModelOptions;
import com.visvise.sdk.options.GenLODOptions;
import com.visvise.sdk.options.WaitOptions;
import com.visvise.sdk.enums.ModelFormat;
import com.visvise.sdk.enums.FaceType;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Batch 1 tests for Gen360, GenHighModel, GenLowModel, GenLOD
 */
public class Batch1Test {

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
            client = new VisviseClient(appId, secretKey, uid);
        }
    }

    private boolean isConfigured() {
        return appId != null && !appId.isEmpty()
                && secretKey != null && !secretKey.isEmpty()
                && uid != null && !uid.isEmpty();
    }

    @Test
    public void testGen360APose() {
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
            Gen360Options opts = Gen360Options.create()
                    .setAlgorithmModel("VISVISE-MultiView-V1.0.0")
                    .setEnableAPose(true);

            String modelId = client.gen360(mainViewFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: gen_360 a_pose=True - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(3).setTimeout(600);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("Gen360 a_pose=True failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testGen360NoAPose() {
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
            Gen360Options opts = Gen360Options.create()
                    .setAlgorithmModel("VISVISE-MultiView-V1.0.0")
                    .setEnableAPose(false);

            String modelId = client.gen360(mainViewFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: gen_360 a_pose=False - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(3).setTimeout(600);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("Gen360 a_pose=False failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testHighModelFaceNum() {
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
                    .setAlgorithmModel("Tripo-v3.1-ultra")
                    .setOutputModelFormat(ModelFormat.FBX)
                    .setFaceType(FaceType.TRIANGLE)
                    .setFaceNum(100000);

            String modelId = client.genHighModel(mainViewFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: high face_num=100000 - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenHighModel face_num=100000 failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testHighModelFaceType2() {
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
                    .setAlgorithmModel("Tripo-v3.1-ultra")
                    .setOutputModelFormat(ModelFormat.GLB)
                    .setFaceType(FaceType.QUAD);

            String modelId = client.genHighModel(mainViewFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: high face_type=2 glb - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenHighModel face_type=2 glb failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testLowModelFaceType1Back() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File mainViewFile = new File(ASSETS_DIR, "main_view.png");
        File backViewFile = new File(ASSETS_DIR, "back_view.png");
        if (!mainViewFile.exists()) {
            System.out.println("Skipping test: main_view.png not found");
            return;
        }

        try {
            GenLowModelOptions opts = GenLowModelOptions.create()
                    .setAlgorithmModel("Tripo-v1.0-快速生成")
                    .setOutputModelFormat(ModelFormat.FBX)
                    .setFaceType(FaceType.TRIANGLE);
            if (backViewFile.exists()) {
                opts.setBackView(backViewFile.getAbsolutePath());
            }

            String modelId = client.genLowModel(mainViewFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: low face_type=1 back - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenLowModel face_type=1 back failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testLowModelFaceType2() {
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
                    .setAlgorithmModel("Tripo-v1.0-快速生成")
                    .setOutputModelFormat(ModelFormat.FBX)
                    .setFaceType(FaceType.QUAD);

            String modelId = client.genLowModel(mainViewFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: low face_type=2 fbx - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenLowModel face_type=2 fbx failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testLODGenTimes() {
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
            java.util.List<ReduceFace> reduceFaces = Collections.singletonList(
                    new ReduceFace(1, 50, FaceType.QUAD));
            GenLODOptions opts = GenLODOptions.create()
                    .setAlgorithmModel("VISVISE-LOD-V1.0.0")
                    .setOutputModelFormat(ModelFormat.FBX)
                    .setGenTimes(1);

            java.util.List<String> modelIds = client.genLOD(modelFile.getAbsolutePath(), reduceFaces, opts);
            assertNotNull("Model IDs should not be null", modelIds);
            System.out.println("PASS: lod gen_times=1 - model_ids=" + modelIds);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelIds.get(0), waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenLOD gen_times=1 failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }
}
