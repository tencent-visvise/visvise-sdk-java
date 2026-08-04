package com.visvise.sdk;

import com.visvise.sdk.api.VisviseAPI;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.NodeType;
import com.visvise.sdk.enums.PreprocessType;
import com.visvise.sdk.enums.StyleType;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.RemovePatternParam;
import com.visvise.sdk.models.StyleParam;
import com.visvise.sdk.models.UserQuota;
import com.visvise.sdk.options.ClientOptions;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Tests for atomic API methods
 */
public class AtomicAPITest {

    private String appId;
    private String secretKey;
    private String rtx;
    private VisviseClient client;

    private static final String ASSETS_DIR = "src/test/resources/assets";

    @Before
    public void setUp() {
        appId = System.getenv("VISVISE_APP_ID");
        secretKey = System.getenv("VISVISE_SECRET_KEY");
        rtx = System.getenv("VISVISE_RTX");
        if (appId != null && secretKey != null && rtx != null) {
            ClientOptions opts = ClientOptions.create()
                    .setEnv(Environment.DEV);
            client = new VisviseClient(appId, secretKey, opts);
        }
    }
    private boolean isConfigured() {
        return appId != null && !appId.isEmpty()
                && secretKey != null && !secretKey.isEmpty()
                && rtx != null && !rtx.isEmpty();
    }

    private String resolvePreprocessInput() throws WeaverError {
        File inputFile = new File(ASSETS_DIR, "preprocess.png");
        if (!inputFile.isFile()) {
            throw new IllegalStateException("preprocess.png not found in " + ASSETS_DIR);
        }
        return client.upload(inputFile.getAbsolutePath(), "", false, rtx);
    }

    private void deletePreprocessModel(String modelId) {
        if (modelId == null || modelId.isEmpty()) {
            return;
        }
        try {
            client.getAPI().deleteModel(modelId, rtx);
        } catch (WeaverError error) {
            System.out.println("Cleanup failed for " + modelId + ": " + error.getMessage());
        }
    }

    @Test
    public void testGetUserQuota() throws WeaverError {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }
        VisviseAPI api = client.getAPI();

        UserQuota quota = api.getUserQuota(rtx);
        assertNotNull(quota);
        assertTrue(quota.getModelQuota() >= 0);
        System.out.println("PASS: get_user_quota - model_quota=" + quota.getModelQuota() + " animation_quota=" + quota.getAnimationQuota() + " server_ts=" + quota.getServerTs());
    }

    @Test
    public void testListAlgorithmModel() throws WeaverError {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }
        VisviseAPI api = client.getAPI();

        // Test Image to 360
        List<String> models = api.listAlgorithmModel(7, null, rtx);
        assertNotNull(models);
        assertFalse(models.isEmpty());
        System.out.println("PASS: list_algorithm_model node_type=7 (Image to 360) - first=" + models.get(0));

        // Test Image to High-poly
        models = api.listAlgorithmModel(3, null, rtx);
        assertNotNull(models);
        assertFalse(models.isEmpty());
        System.out.println("PASS: list_algorithm_model node_type=3 (Image to High-poly) - first=" + models.get(0));

        // Test Video to Animation
        int subType = 1;
        models = api.listAlgorithmModel(4, subType, rtx);
        assertNotNull(models);
        System.out.println("PASS: list_algorithm_model node_type=4 sub_type=1 (Video to Animation)- first=" + models.get(0));

        // Test Text to Animation
        subType = 2;
        models = api.listAlgorithmModel(4, subType, rtx);
        assertNotNull(models);
        System.out.println("PASS: list_algorithm_model node_type=4 sub_type=2 (Text to Animation)- first=" + models.get(0));

        // Test Rigging
        models = api.listAlgorithmModel(5, null, rtx);
        assertNotNull(models);
        System.out.println("PASS: list_algorithm_model node_type=5 (Rigging)- first=" + models.get(0));

        // Test LOD
        models = api.listAlgorithmModel(2, null, rtx);
        assertNotNull(models);
        System.out.println("PASS: list_algorithm_model node_type=2 (LOD)- first=" + models.get(0));

        // Test 2D preprocess
        models = api.listAlgorithmModel(16, null, rtx);
        assertNotNull(models);
        assertFalse(models.isEmpty());
        System.out.println("PASS: list_algorithm_model node_type=16 (2D preprocess)- first=" + models.get(0));
    }

    @Test
    public void testStyleTransfer() throws WeaverError {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        String resultImage = client.getAPI().styleTransfer(resolvePreprocessInput(), StyleType.GRAYSCALE, rtx);
        assertNotNull(resultImage);
        assertFalse(resultImage.isEmpty());
        System.out.println("PASS: style_transfer - output_url=" + resultImage.substring(0, Math.min(80, resultImage.length())) + "...");
    }

    @Test
    public void testPatterAutoRemove() throws WeaverError {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        String resultImage = client.getAPI().patterAutoRemove(resolvePreprocessInput(), rtx);
        assertNotNull(resultImage);
        assertFalse(resultImage.isEmpty());
        System.out.println("PASS: patter_auto_remove - output_url=" + resultImage.substring(0, Math.min(80, resultImage.length())) + "...");
    }

    @Test
    public void testGenPreprocessStylized() throws WeaverError {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        VisviseAPI api = client.getAPI();
        String inputUrl = resolvePreprocessInput();
        List<String> models = api.listAlgorithmModel(NodeType.PREPROCESS_2D.getValue(), null, rtx);
        assertNotNull(models);
        assertFalse(models.isEmpty());
        String resultImage = api.styleTransfer(inputUrl, StyleType.GRAYSCALE, rtx);
        Map<String, Object> params = new HashMap<>();
        params.put("preprocess_type", PreprocessType.STYLIZED.getValue());
        if (models.get(0) != null && !models.get(0).isEmpty()) {
            params.put("algorithm_model", models.get(0));
        }
        params.put("style_param", new StyleParam(StyleType.GRAYSCALE, resultImage).toMap());
        String modelId = api.genPreprocess(
                "atomic_2d_style_" + System.nanoTime(), inputUrl, params, rtx);
        assertNotNull(modelId);
        assertFalse(modelId.isEmpty());
        try {
            System.out.println("PASS: gen_preprocess stylized - model_id=" + modelId);
        } finally {
            deletePreprocessModel(modelId);
        }
    }

    @Test
    public void testGenPreprocessPatterned() throws WeaverError {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        VisviseAPI api = client.getAPI();
        String inputUrl = resolvePreprocessInput();
        List<String> models = api.listAlgorithmModel(NodeType.PREPROCESS_2D.getValue(), null, rtx);
        assertNotNull(models);
        assertFalse(models.isEmpty());
        String resultImage = api.patterAutoRemove(inputUrl, rtx);
        Map<String, Object> params = new HashMap<>();
        params.put("preprocess_type", PreprocessType.PATTERNED.getValue());
        if (models.get(0) != null && !models.get(0).isEmpty()) {
            params.put("algorithm_model", models.get(0));
        }
        params.put("remove_pattern_param", new RemovePatternParam(resultImage).toMap());
        String modelId = api.genPreprocess(
                "atomic_2d_pattern_" + System.nanoTime(), inputUrl, params, rtx);
        assertNotNull(modelId);
        assertFalse(modelId.isEmpty());
        try {
            System.out.println("PASS: gen_preprocess patterned - model_id=" + modelId);
        } finally {
            deletePreprocessModel(modelId);
        }
    }

    @Test
    public void testGetText2MotionPromptList() throws WeaverError {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }
        VisviseAPI api = client.getAPI();

        // Test Chinese prompts
        List<String> prompts = api.getText2MotionPromptList("zh", rtx);
        assertNotNull(prompts);
        assertFalse(prompts.isEmpty());
        System.out.println("PASS: get_text2motion_prompt_list lang=zh - count=" + prompts.size() + " first=" + prompts.get(0));

        // Test English prompts
        prompts = api.getText2MotionPromptList("en", rtx);
        assertNotNull(prompts);
        assertFalse(prompts.isEmpty());
        System.out.println("PASS: get_text2motion_prompt_list lang=en - count=" + prompts.size() + " first=" + prompts.get(0));
    }
}
