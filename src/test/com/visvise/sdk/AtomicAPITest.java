package com.visvise.sdk;

import com.visvise.sdk.api.VisviseAPI;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.UserQuota;
import com.visvise.sdk.options.ClientOptions;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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

    @Test
    public void testGetUserQuota() throws WeaverError {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }
        VisviseAPI api = client.getAPI();

        UserQuota quota = api.getUserQuota(rtx);
        assertNotNull(quota);
        assertTrue(quota.getQuota() >= 0);
        System.out.println("PASS: get_user_quota - quota=" + quota.getQuota() + " server_ts=" + quota.getServerTs());
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
