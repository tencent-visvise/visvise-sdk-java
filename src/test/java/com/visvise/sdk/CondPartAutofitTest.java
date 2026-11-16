package com.visvise.sdk;

import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.CondPartAutofitResult;
import com.visvise.sdk.options.ClientOptions;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Tests for cond_part_autofit (裸模拆分)
 */
public class CondPartAutofitTest {

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
            // 同步算法最长 5 分钟，客户端超时至少 300s
            ClientOptions opts = ClientOptions.create()
                    .setEnv(Environment.DEV)
                    .setTimeout(360);
            client = new VisviseClient(appId, secretKey, opts);
        }
    }

    private boolean isConfigured() {
        return appId != null && !appId.isEmpty()
                && secretKey != null && !secretKey.isEmpty()
                && rtx != null && !rtx.isEmpty();
    }

    @Test
    public void testCondPartAutofit() throws WeaverError {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }
        File original = new File(ASSETS_DIR, "original_model.obj");
        File ref = new File(ASSETS_DIR, "ref_model.obj");
        if (!original.isFile() || !ref.isFile()) {
            System.out.println("Skipping test: original_model.obj / ref_model.obj not found in " + ASSETS_DIR);
            return;
        }

        CondPartAutofitResult result = client.condPartAutofit(original.getAbsolutePath(), ref.getAbsolutePath(), null, rtx);
        assertNotNull(result);
        assertNotNull(result.getOutputObjPath());
        assertFalse(result.getOutputObjPath().isEmpty());
        System.out.println("PASS: cond_part_autofit - output_obj_path=" + result.getOutputObjPath()
                + " background_color=" + result.getBackgroundColor());
    }
}
