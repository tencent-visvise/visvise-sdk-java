package com.visvise.sdk;

import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.options.GenVideoMotionOptions;
import com.visvise.sdk.options.GenTextMotionOptions;
import com.visvise.sdk.options.WaitOptions;
import com.visvise.sdk.enums.OutputModelFormat;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Batch 3 tests for GenVideoMotion and GenTextMotion
 */
public class Batch3Test {

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
    public void testVideoMotionWithHand() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "animation_model.fbx");
        File videoFile = new File(ASSETS_DIR, "animation_video.mp4");
        if (!modelFile.exists() || !videoFile.exists()) {
            System.out.println("Skipping test: animation_model.fbx or animation_video.mp4 not found");
            return;
        }

        try {
            GenVideoMotionOptions opts = GenVideoMotionOptions.create()
                    .setAlgorithmModel("VISVISE-FramingAI-Base-V1.5.0")
                    .setWithHand(true)
                    .setMultipleTrack(false);

            String modelId = client.genVideoMotion(modelFile.getAbsolutePath(), videoFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: vm with_hand=True - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenVideoMotion with_hand=True failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testVideoMotionNoHandNoMulti() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File modelFile = new File(ASSETS_DIR, "animation_model.fbx");
        File videoFile = new File(ASSETS_DIR, "animation_video.mp4");
        if (!modelFile.exists() || !videoFile.exists()) {
            System.out.println("Skipping test: animation_model.fbx or animation_video.mp4 not found");
            return;
        }

        try {
            GenVideoMotionOptions opts = GenVideoMotionOptions.create()
                    .setAlgorithmModel("VISVISE-FramingAI-Base-V1.5.0")
                    .setWithHand(false)
                    .setMultipleTrack(false);

            String modelId = client.genVideoMotion(modelFile.getAbsolutePath(), videoFile.getAbsolutePath(), opts);
            assertNotNull("Model ID should not be null", modelId);
            System.out.println("PASS: vm hand=False multi=False - model_id=" + modelId);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelId, waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenVideoMotion hand=False multi=False failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testTextMotionWave() {
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
            GenTextMotionOptions opts = GenTextMotionOptions.create()
                    .setAlgorithmModel("VISVISE-TextMotion-V1.1.0");

            List<String> modelIds = client.genTextMotion(modelFile.getAbsolutePath(), "一个人在挥手打招呼", opts);
            assertNotNull("Model IDs should not be null", modelIds);
            System.out.println("PASS: tm prompt=挥手 - model_ids=" + modelIds);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelIds.get(0), waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenTextMotion prompt=挥手 failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }

    @Test
    public void testTextMotionStep() {
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
            GenTextMotionOptions opts = GenTextMotionOptions.create()
                    .setAlgorithmModel("VISVISE-TextMotion-V1.1.0")
                    .setOutputModelFormat(OutputModelFormat.GLB);

            List<String> modelIds = client.genTextMotion(modelFile.getAbsolutePath(), "一个人在原地踏步", opts);
            assertNotNull("Model IDs should not be null", modelIds);
            System.out.println("PASS: tm prompt=踏步 glb - model_ids=" + modelIds);

            // Wait for completion
            WaitOptions waitOpts = WaitOptions.create().setInterval(5).setTimeout(900);
            ModelInfo model = client.waitModel(modelIds.get(0), waitOpts);
            System.out.println("Model completed: status=" + model.getStatus() + " time_cost=" + model.getTimeCost());
        } catch (Exception e) {
            System.out.println("GenTextMotion prompt=踏步 glb failed: " + e.getMessage());
            throw new AssertionError(e);
        }
    }
}
