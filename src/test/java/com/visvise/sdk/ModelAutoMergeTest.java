package com.visvise.sdk;

import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.ModelAutoMergeResult;
import com.visvise.sdk.options.ClientOptions;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Tests for model_auto_merge (模型自动合并)
 */
public class ModelAutoMergeTest {

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
    public void testModelAutoMerge() throws WeaverError {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }
        File partsZip = new File(ASSETS_DIR, "parts.zip");
        if (!partsZip.isFile()) {
            System.out.println("Skipping test: parts.zip not found in " + ASSETS_DIR);
            return;
        }

        // refMesh 为空：只做部件合并，不使用参考模型补齐缺失部件
        ModelAutoMergeResult result = client.modelAutoMerge(partsZip.getAbsolutePath(), null, false, rtx);
        assertNotNull(result);
        assertNotNull(result.getOutputObjPath());
        assertFalse(result.getOutputObjPath().isEmpty());
        System.out.println("PASS: model_auto_merge - output_obj_path=" + result.getOutputObjPath()
                + " elapsed_time=" + result.getElapsedTime() + " mean_bbox_iou=" + result.getMeanBboxIou());
    }
}
