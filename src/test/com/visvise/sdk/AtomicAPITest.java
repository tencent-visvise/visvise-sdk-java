package com.visvise.sdk;

import com.visvise.sdk.api.VisviseAPI;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.UserQuota;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.WaitOptions;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for atomic API methods
 */
public class AtomicAPITest {

    private String getAppId() {
        return System.getenv("VISVISE_APP_ID");
    }

    private String getSecretKey() {
        return System.getenv("VISVISE_SECRET_KEY");
    }

    private String getUid() {
        return System.getenv("VISVISE_UID");
    }

    private boolean hasCredentials() {
        return getAppId() != null && !getAppId().isEmpty()
                && getSecretKey() != null && !getSecretKey().isEmpty()
                && getUid() != null && !getUid().isEmpty();
    }

    private VisviseClient createClient() {
        ClientOptions opts = ClientOptions.create()
                .setEnv(Environment.DEV)
                .setDebug(true);
        return new VisviseClient(getAppId(), getSecretKey(), getUid(), opts);
    }

    @Test
    public void testGetUserQuota() throws WeaverError {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        VisviseAPI api = client.getAPI();

        UserQuota quota = api.getUserQuota();
        assertNotNull(quota);
        assertTrue(quota.getQuota() >= 0);
        System.out.println("PASS: get_user_quota - quota=" + quota.getQuota() + " server_ts=" + quota.getServerTs());
    }

    @Test
    public void testListAlgorithmModel() throws WeaverError {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        VisviseAPI api = client.getAPI();

        // Test Image to 360
        List<String> models = api.listAlgorithmModel(7, null);
        assertNotNull(models);
        assertFalse(models.isEmpty());
        System.out.println("PASS: list_algorithm_model node_type=7 (Image to 360) - first=" + models.get(0));

        // Test Image to High-poly
        models = api.listAlgorithmModel(3, null);
        assertNotNull(models);
        assertFalse(models.isEmpty());
        System.out.println("PASS: list_algorithm_model node_type=3 (Image to High-poly) - first=" + models.get(0));

        // Test Video to Animation
        int subType = 1;
        models = api.listAlgorithmModel(4, subType);
        assertNotNull(models);
        System.out.println("PASS: list_algorithm_model node_type=4 sub_type=1 (Video to Animation)");

        // Test Text to Animation
        subType = 2;
        models = api.listAlgorithmModel(4, subType);
        assertNotNull(models);
        System.out.println("PASS: list_algorithm_model node_type=4 sub_type=2 (Text to Animation)");

        // Test Rigging
        models = api.listAlgorithmModel(5, null);
        assertNotNull(models);
        System.out.println("PASS: list_algorithm_model node_type=5 (Rigging)");

        // Test LOD
        models = api.listAlgorithmModel(2, null);
        assertNotNull(models);
        System.out.println("PASS: list_algorithm_model node_type=2 (LOD)");
    }

    @Test
    public void testGetText2MotionPromptList() throws WeaverError {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        VisviseAPI api = client.getAPI();

        // Test Chinese prompts
        List<String> prompts = api.getText2MotionPromptList("zh");
        assertNotNull(prompts);
        assertFalse(prompts.isEmpty());
        System.out.println("PASS: get_text2motion_prompt_list lang=zh - count=" + prompts.size() + " first=" + prompts.get(0));

        // Test English prompts
        prompts = api.getText2MotionPromptList("en");
        assertNotNull(prompts);
        assertFalse(prompts.isEmpty());
        System.out.println("PASS: get_text2motion_prompt_list lang=en - count=" + prompts.size() + " first=" + prompts.get(0));
    }

    @Test
    public void testWaitOptionsDefaults() {
        WaitOptions opts = WaitOptions.defaults();
        assertEquals(2.0, opts.getInterval());
        assertEquals(600, opts.getTimeout());
        assertEquals(2000, opts.getIntervalMillis());
        System.out.println("PASS: WaitOptions defaults work correctly");
    }

    @Test
    public void testClientOptionsDefaults() {
        ClientOptions opts = ClientOptions.create();
        assertEquals(Environment.PROD, opts.getEnv());
        assertEquals(30, opts.getTimeout());
        assertFalse(opts.isDebug());
        System.out.println("PASS: ClientOptions defaults work correctly");
    }
}
