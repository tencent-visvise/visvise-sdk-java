package com.visvise.sdk;

import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.util.FileTypeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @author: huangci
 * @since 2026/5/19
 **/
public class SniffExtensionRealAssetsTest {

    private String appId;
    private String secretKey;
    private String rtx;
    private VisviseClient client;
    private static final String ASSETS_DIR = "src/test/resources/assets";


    private static final Map<String, String> EXPECTED_EXTENSIONS = new HashMap<>();

    static {
        EXPECTED_EXTENSIONS.put("main_view.png", ".png");       // contains JPEG data
        EXPECTED_EXTENSIONS.put("back_view.png", ".png");
        EXPECTED_EXTENSIONS.put("left_view.png", ".png");
        EXPECTED_EXTENSIONS.put("right_view.png", ".png");
        EXPECTED_EXTENSIONS.put("pose_ref.png", ".png");
        EXPECTED_EXTENSIONS.put("tex_ref_front.jpg", ".jpg");
        EXPECTED_EXTENSIONS.put("tex_ref_back.jpg", ".jpg");
        EXPECTED_EXTENSIONS.put("tex_ref_left.jpg", ".jpg");
        EXPECTED_EXTENSIONS.put("high_model.fbx", ".fbx");
        EXPECTED_EXTENSIONS.put("animation_model.fbx", ".fbx");
        EXPECTED_EXTENSIONS.put("rigging_model.fbx", ".fbx");
        EXPECTED_EXTENSIONS.put("skinning_model.fbx", ".fbx");
        EXPECTED_EXTENSIONS.put("tex_model.obj", ".obj");
        EXPECTED_EXTENSIONS.put("animation_video.mp4", ".mp4");
    }

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
    public void testSniffExtensionWithRealAssets() throws IOException {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }
        List<String> failures = new ArrayList<>();
        Path assetsPath = Paths.get(ASSETS_DIR);

        for (Map.Entry<String, String> entry : EXPECTED_EXTENSIONS.entrySet()) {
            String fileName = entry.getKey();
            String expectedExt = entry.getValue();

            Path filePath = assetsPath.resolve(fileName);

            if (!Files.exists(filePath)) {
                System.out.println("SKIP: " + fileName + " not found, skipping");
                continue;
            }

            // 只读取前 2KB
            byte[] head = readFirstBytes(filePath, 2048);

            String actualExt = FileTypeUtil.sniffExtension(head, ".bin");
            String generatedName = client.genRandomFilename(".bin");

            long sizeBytes = Files.size(filePath);
            double sizeKB = sizeBytes / 1024.0;

            boolean passed = expectedExt.equals(actualExt);
            String status = passed ? "PASS" : "FAIL";

            System.out.printf(
                    "  %s %s (%.1f KB) -> %s (expected: %s) uuid: %s%n",
                    status, fileName, sizeKB, actualExt, expectedExt, generatedName
            );

            if (!passed) {
                failures.add(fileName + ": got=" + actualExt + ", want=" + expectedExt);
            }
        }

        if (!failures.isEmpty()) {
            System.err.println(failures.size() + " test cases failed:");
            failures.forEach(f -> System.err.println("  - " + f));
            Assert.fail(failures.size() + " test cases failed");
        }
    }

    // 读取文件前 N 个字节
    private byte[] readFirstBytes(Path path, int maxLen) throws IOException {
        try (InputStream in = Files.newInputStream(path)) {
            byte[] buffer = new byte[maxLen];
            int n = in.read(buffer);
            if (n <= 0) {
                return new byte[0];
            }
            byte[] result = new byte[n];
            System.arraycopy(buffer, 0, result, 0, n);
            return result;
        }
    }
}


