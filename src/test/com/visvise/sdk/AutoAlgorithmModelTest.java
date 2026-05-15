package com.visvise.sdk;

import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.options.ClientOptions;
import org.junit.Test;
import org.junit.Ignore;

import static org.junit.Assert.*;

/**
 * Tests for auto algorithm model selection
 */
public class AutoAlgorithmModelTest {

    private String getAppId() {
        return System.getenv("VISVISE_APP_ID");
    }

    private String getSecretKey() {
        return System.getenv("VISVISE_SECRET_KEY");
    }

    private String getUid() {
        return System.getenv("VISVISE_UID");
    }

    private String getAssetsDir() {
        return "src/test/resources/assets";
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

    // Note: These tests require actual API credentials and test assets
    // They are designed to test the auto algorithm model selection feature

    @Test
    @Ignore("Requires test assets")
    public void testGen360WithoutAlgorithmModel() {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        // Test would create Gen360Options without algorithmModel
        // and verify auto-selection works
        System.out.println("Note: Auto-select Gen360 algorithm model");
    }

    @Test
    @Ignore("Requires test assets")
    public void testGenHighModelWithoutAlgorithmModel() {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        // Test would create GenHighModelOptions without algorithmModel
        // and verify auto-selection works
        System.out.println("Note: Auto-select GenHighModel algorithm model");
    }

    @Test
    @Ignore("Requires test assets")
    public void testGenMidModelWithoutAlgorithmModel() {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        // Test would create GenMidModelOptions without algorithmModel
        // and verify auto-selection works
        System.out.println("Note: Auto-select GenMidModel algorithm model");
    }

    @Test
    @Ignore("Requires test assets")
    public void testGenLowModelWithoutAlgorithmModel() {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        // Test would create GenLowModelOptions without algorithmModel
        // and verify auto-selection works
        System.out.println("Note: Auto-select GenLowModel algorithm model");
    }

    @Test
    @Ignore("Requires test assets")
    public void testGenMeshRefineWithoutAlgorithmModel() {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        System.out.println("Note: Auto-select GenMeshRefine algorithm model");
    }

    @Test
    @Ignore("Requires test assets")
    public void testGenRetopologyWithoutAlgorithmModel() {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        System.out.println("Note: Auto-select GenRetopology algorithm model");
    }

    @Test
    @Ignore("Requires test assets")
    public void testGenLODWithoutAlgorithmModel() {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        System.out.println("Note: Auto-select GenLOD algorithm model");
    }

    @Test
    @Ignore("Requires test assets")
    public void testGenUVWithoutAlgorithmModel() {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        System.out.println("Note: Auto-select GenUV algorithm model");
    }

    @Test
    @Ignore("Requires test assets")
    public void testGenTextureWithoutAlgorithmModel() {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        System.out.println("Note: Auto-select GenTexture algorithm model");
    }

    @Test
    @Ignore("Requires test assets")
    public void testGenRiggingWithoutAlgorithmModel() {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        System.out.println("Note: Auto-select GenRigging algorithm model");
    }

    @Test
    @Ignore("Requires test assets")
    public void testGenSkinningWithoutAlgorithmModel() {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        System.out.println("Note: Auto-select GenSkinning algorithm model");
    }

    @Test
    @Ignore("Requires test assets")
    public void testGenVideoMotionWithoutAlgorithmModel() {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        System.out.println("Note: Auto-select GenVideoMotion algorithm model");
    }

    @Test
    @Ignore("Requires test assets")
    public void testGenTextMotionWithoutAlgorithmModel() {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        System.out.println("Note: Auto-select GenTextMotion algorithm model");
    }

    @Test
    @Ignore("Requires test assets")
    public void testGenPoseWithoutAlgorithmModel() {
        if (!hasCredentials()) {
            return;
        }
        VisviseClient client = createClient();
        System.out.println("Note: Auto-select GenPose algorithm model");
    }
}
