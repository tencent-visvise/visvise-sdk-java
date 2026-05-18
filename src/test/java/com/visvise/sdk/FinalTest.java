package com.visvise.sdk;

import com.visvise.sdk.api.VisviseAPI;
import com.visvise.sdk.enums.DetailLevel;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.ModelFormat;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.models.ReduceFace;
import com.visvise.sdk.models.View;
import com.visvise.sdk.options.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Final tests: query yesterday models, complete batch2, and animation tests
 */
public class FinalTest {

    private String appId;
    private String secretKey;
    private String rtx;
    private VisviseClient client;

    private static final String ASSETS_DIR = "src/test/resources/assets";
    private static final String MV_BASE = "https://visvise-weaver-bj-rel-1311802504.cos.accelerate.myqcloud.com/weaver/user-p_5sxfmuvwtfj58ssbt97q2k2kc83697p/Model2026042300225069";

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

    /**
     * TestFinal_QueryYesterdayModels: query yesterday's batch2 model_id results
     */
    @Test
    public void testQueryYesterdayModels() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        VisviseAPI api = client.getAPI();

        String[][] yesterdayModels = {
                {"mid face_type=1 fbx", "Model2026042300225326"},
                {"rtp detail=2 face=2", "Model2026042300225337"},
                {"mr mode=optimize", "Model2026042300225347"},
                {"uv smooth=True", "Model2026042300226324"},
                {"tex res=1024", "Model2026042300225360"},
        };

        for (String[] m : yesterdayModels) {
            String name = m[0];
            String id = m[1];
            try {
                VisviseAPI.ModelListResult result = api.getModelList(
                        Collections.singletonList(id), null, null, "", 10, 1, rtx);
                if (!result.getModels().isEmpty()) {
                    ModelInfo model = result.getModels().get(0);
                    System.out.printf("PASS: [%s] model_id=%s status=%d time_cost=%d%n",
                            name, model.getModelId(), model.getStatus(), model.getTimeCost());
                } else {
                    System.out.printf("Model %s not found (already deleted)%n", id);
                }
            } catch (Exception e) {
                System.out.printf("Query model %s failed: %s%n", id, e.getMessage());
            }
        }
    }

    /**
     * TestFinal_CompleteBatch2: complete batch2 remaining test cases
     */
    @Test
    public void testCompleteBatch2() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        // GenMidModel face_type=2 fbx
        try {
            GenMidModelOptions opts = GenMidModelOptions.create()
                    .setAlgorithmModel("VISVISE-MeshGen-V1.0.0")
                    .setOutputModelFormat(ModelFormat.FBX)
                    .setFaceType(FaceType.QUAD)
                    .setName("opt_mid_b_final");

            String modelId = client.genMidModel(
                    MV_BASE + "/example_gen_360_MultiView(2)_MainView.png",
                    MV_BASE + "/example_gen_360_MultiView(2)_BackView.png",
                    MV_BASE + "/example_gen_360_MultiView(2)_LeftView.png",
                    MV_BASE + "/example_gen_360_MultiView(2)_RightView.png",
                    opts, rtx);
            assertNotNull("Model ID should not be null", modelId);
            System.out.printf("Submitted [mid face_type=2 fbx] -> %s%n", modelId);
        } catch (Exception e) {
            System.out.printf("GenMidModel face_type=2 fbx failed: %s%n", e.getMessage());
            throw new AssertionError(e);
        }

        // GenRetopology detail_level=3 face_type=1
        File modelFile = new File(ASSETS_DIR, "tex_model.obj");
        if (!modelFile.exists()) {
            System.out.println("Skipping remaining batch2 tests: tex_model.obj not found");
            return;
        }

        try {
            GenRetopologyOptions opts2 = GenRetopologyOptions.create()
                    .setAlgorithmModel("hunyuan3D-RTP-v1.5")
                    .setOutputModelFormat(ModelFormat.FBX)
                    .setFaceType(FaceType.TRIANGLE)
                    .setName("opt_rtp_b_final")
                    .setDetailLevel(DetailLevel.HIGH);

            String modelId = client.genRetopology(modelFile.getAbsolutePath(), opts2, rtx);
            assertNotNull("Model ID should not be null", modelId);
            System.out.printf("Submitted [rtp detail=3 face=1] -> %s%n", modelId);
        } catch (Exception e) {
            System.out.printf("GenRetopology detail=3 face=1 failed: %s%n", e.getMessage());
            throw new AssertionError(e);
        }

        // GenUV smooth=False
        try {
            GenUVOptions opts3 = GenUVOptions.create()
                    .setAlgorithmModel("hunyuan3D-UV-v2.0")
                    .setName("opt_uv_b_final")
                    .setEnableAutoSmoothing(false);

            String modelId = client.genUV(modelFile.getAbsolutePath(), opts3, rtx);
            assertNotNull("Model ID should not be null", modelId);
            System.out.printf("Submitted [uv smooth=False] -> %s%n", modelId);
        } catch (Exception e) {
            System.out.printf("GenUV smooth=False failed: %s%n", e.getMessage());
            throw new AssertionError(e);
        }

        // GenTexture res=2048 unwarp_uv=True
        File refFrontFile = new File(ASSETS_DIR, "tex_ref_front.jpg");
        try {
            View view = new View();
            if (refFrontFile.exists()) {
                view.setMainView(refFrontFile.getAbsolutePath());
            }

            GenTextureOptions opts4 = GenTextureOptions.create()
                    .setAlgorithmModel("hunyuan3D-TEX-v2.0")
                    .setName("opt_tex_b_final")
                    .setInputView(view)
                    .setResolution(2048)
                    .setUnwarpUV(true);

            String modelId = client.genTexture(modelFile.getAbsolutePath(), opts4, rtx);
            assertNotNull("Model ID should not be null", modelId);
            System.out.printf("Submitted [tex res=2048 uv=True] -> %s%n", modelId);
        } catch (Exception e) {
            System.out.printf("GenTexture res=2048 uv=True failed: %s%n", e.getMessage());
            throw new AssertionError(e);
        }

        // GenLOD gen_times=1
        try {
            List<ReduceFace> reduceFaces = Arrays.asList(
                    new ReduceFace(1, 50, FaceType.QUAD));

            GenLODOptions opts5 = GenLODOptions.create()
                    .setAlgorithmModel("VISVISE-LOD-V1.0.0")
                    .setOutputModelFormat(ModelFormat.FBX)
                    .setName("opt_lod_a_final")
                    .setGenTimes(1);

            List<String> modelIds = client.genLOD(modelFile.getAbsolutePath(), reduceFaces, opts5, rtx);
            assertNotNull("Model IDs should not be null", modelIds);
            System.out.printf("Submitted [lod gen_times=1] -> %s%n", modelIds);
        } catch (Exception e) {
            System.out.printf("GenLOD gen_times=1 failed: %s%n", e.getMessage());
            throw new AssertionError(e);
        }
    }

    /**
     * TestFinal_AnimationTests: batch3 animation tests
     */
    @Test
    public void testAnimationTests() {
        if (!isConfigured()) {
            System.out.println("Skipping test: VISVISE credentials not configured");
            return;
        }

        File animModelFile = new File(ASSETS_DIR, "animation_model.fbx");
        File videoFile = new File(ASSETS_DIR, "animation_video.mp4");
        if (!animModelFile.exists()) {
            System.out.println("Skipping test: animation_model.fbx not found");
            return;
        }

        // GenVideoMotion with_hand=True
        if (videoFile.exists()) {
            try {
                GenVideoMotionOptions opts = GenVideoMotionOptions.create()
                        .setAlgorithmModel("VISVISE-FramingAI-Base-V1.5.0")
                        .setName("opt_vm_a_final")
                        .setWithHand(true)
                        .setMultipleTrack(false);

                String modelId = client.genVideoMotion(
                        animModelFile.getAbsolutePath(), videoFile.getAbsolutePath(), opts, rtx);
                assertNotNull("Model ID should not be null", modelId);
                System.out.printf("Submitted [vm with_hand=True] -> %s%n", modelId);
            } catch (Exception e) {
                System.out.printf("GenVideoMotion with_hand=True failed: %s%n", e.getMessage());
                throw new AssertionError(e);
            }

            // GenVideoMotion hand=False multi=False
            try {
                GenVideoMotionOptions opts2 = GenVideoMotionOptions.create()
                        .setAlgorithmModel("VISVISE-FramingAI-Base-V1.5.0")
                        .setName("opt_vm_b_final")
                        .setWithHand(false)
                        .setMultipleTrack(false);

                String modelId = client.genVideoMotion(
                        animModelFile.getAbsolutePath(), videoFile.getAbsolutePath(), opts2, rtx);
                assertNotNull("Model ID should not be null", modelId);
                System.out.printf("Submitted [vm hand=False multi=False] -> %s%n", modelId);
            } catch (Exception e) {
                System.out.printf("GenVideoMotion hand=False multi=False failed: %s%n", e.getMessage());
                throw new AssertionError(e);
            }
        }

        // GenTextMotion prompt=挥手
        try {
            GenTextMotionOptions opts3 = GenTextMotionOptions.create()
                    .setAlgorithmModel("VISVISE-TextMotion-V1.1.0")
                    .setName("opt_tm_a_final");

            List<String> modelIds = client.genTextMotion(
                    animModelFile.getAbsolutePath(), "一个人在挥手打招呼", opts3, rtx);
            assertNotNull("Model IDs should not be null", modelIds);
            System.out.printf("Submitted [tm prompt=挥手] -> %s%n", modelIds);
        } catch (Exception e) {
            System.out.printf("GenTextMotion prompt=挥手 failed: %s%n", e.getMessage());
            throw new AssertionError(e);
        }

        // GenTextMotion prompt=踏步 glb
        try {
            GenTextMotionOptions opts4 = GenTextMotionOptions.create()
                    .setAlgorithmModel("VISVISE-TextMotion-V1.1.0")
                    .setOutputModelFormat(ModelFormat.GLB)
                    .setName("opt_tm_b_final");

            List<String> modelIds = client.genTextMotion(
                    animModelFile.getAbsolutePath(), "一个人在原地踏步", opts4, rtx);
            assertNotNull("Model IDs should not be null", modelIds);
            System.out.printf("Submitted [tm prompt=踏步 glb] -> %s%n", modelIds);
        } catch (Exception e) {
            System.out.printf("GenTextMotion prompt=踏步 glb failed: %s%n", e.getMessage());
            throw new AssertionError(e);
        }
    }
}
