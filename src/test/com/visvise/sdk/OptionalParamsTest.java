package com.visvise.sdk;

import com.visvise.sdk.enums.*;
import com.visvise.sdk.options.*;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Tests for optional parameters of all Gen* methods
 */
public class OptionalParamsTest {

    @Test
    public void testGen360Options() {
        Gen360Options opts = Gen360Options.create()
                .setName("my_360")
                .setAlgorithmModel("VISVISE-MultiView-V1.0.0")
                .setOutputModelFormat(ModelFormat.FBX)
                .setFaceType(FaceType.TRIANGLE)
                .setEnableAPose(true)
                .setStyle("anime");

        assertEquals("my_360", opts.getName());
        assertEquals("VISVISE-MultiView-V1.0.0", opts.getAlgorithmModel());
        assertEquals(ModelFormat.FBX, opts.getOutputModelFormat());
        assertEquals(FaceType.TRIANGLE, opts.getFaceType());
        assertTrue(opts.getEnableAPose());
        assertEquals("anime", opts.getStyle());

        System.out.println("PASS: Gen360Options chain builder works");
    }

    @Test
    public void testGenHighModelOptions() {
        GenHighModelOptions opts = GenHighModelOptions.create()
                .setName("my_high_model")
                .setAlgorithmModel("hunyuan3D-v3.1")
                .setOutputModelFormat(ModelFormat.FBX)
                .setFaceType(FaceType.TRIANGLE)
                .setFaceNum(500000);

        assertEquals("my_high_model", opts.getName());
        assertEquals(500000, opts.getFaceNum().intValue());

        System.out.println("PASS: GenHighModelOptions chain builder works");
    }

    @Test
    public void testGenMidModelOptions() {
        GenMidModelOptions opts = GenMidModelOptions.create()
                .setName("my_mid_model")
                .setAlgorithmModel("VISVISE-MeshGen-V1.0.0")
                .setOutputModelFormat(ModelFormat.OBJ)
                .setFaceType(FaceType.QUAD)
                .setSegmentModelId("Model2026...");

        assertEquals("my_mid_model", opts.getName());
        assertEquals(ModelFormat.OBJ, opts.getOutputModelFormat());
        assertEquals(FaceType.QUAD, opts.getFaceType());
        assertEquals("Model2026...", opts.getSegmentModelId());

        System.out.println("PASS: GenMidModelOptions chain builder works");
    }

    @Test
    public void testGenLowModelOptions() {
        GenLowModelOptions opts = GenLowModelOptions.create()
                .setName("my_low_model")
                .setAlgorithmModel("Tripo-v1.0-fast")
                .setOutputModelFormat(ModelFormat.GLB)
                .setFaceType(FaceType.TRIANGLE);

        assertEquals("my_low_model", opts.getName());
        assertEquals(ModelFormat.GLB, opts.getOutputModelFormat());

        System.out.println("PASS: GenLowModelOptions chain builder works");
    }

    @Test
    public void testGenMeshRefineOptions() {
        GenMeshRefineOptions opts = GenMeshRefineOptions.create()
                .setName("my_mesh_refine")
                .setAlgorithmModel("VISVISE-MeshRefine-V1.0.0")
                .setInputModelFormat(ModelFormat.FBX)
                .setMode(MeshRefineMode.OPTIMIZE);

        assertEquals("my_mesh_refine", opts.getName());
        assertEquals(MeshRefineMode.OPTIMIZE, opts.getMode());

        // Test densify mode
        opts.setMode(MeshRefineMode.DENSIFY);
        assertEquals(MeshRefineMode.DENSIFY, opts.getMode());

        System.out.println("PASS: GenMeshRefineOptions chain builder works");
    }

    @Test
    public void testGenRetopologyOptions() {
        // Hunyuan model example
        GenRetopologyOptions opts = GenRetopologyOptions.create()
                .setName("my_retopo")
                .setAlgorithmModel("hunyuan3D-RTP-v1.5")
                .setOutputModelFormat(ModelFormat.FBX)
                .setFaceType(FaceType.QUAD)
                .setDetailLevel(DetailLevel.MEDIUM);

        assertEquals("my_retopo", opts.getName());
        assertEquals(DetailLevel.MEDIUM, opts.getDetailLevel());

        // Test high detail
        opts.setDetailLevel(DetailLevel.HIGH);
        assertEquals(DetailLevel.HIGH, opts.getDetailLevel());

        // VISVISE model example
        GenRetopologyOptions opts2 = GenRetopologyOptions.create()
                .setAlgorithmModel("VISVISE-RTP-V1.0.0")
                .setFaceNum(50000);

        assertEquals(50000, opts2.getFaceNum().intValue());

        System.out.println("PASS: GenRetopologyOptions chain builder works");
    }

    @Test
    public void testGenLODOptions() {
        GenLODOptions opts = GenLODOptions.create()
                .setName("my_lod")
                .setAlgorithmModel("VISVISE-LOD-V1.0.0")
                .setOutputModelFormat(ModelFormat.FBX)
                .setGenTimes(3);

        assertEquals("my_lod", opts.getName());
        assertEquals(3, opts.getGenTimes());

        // Test different gen times
        opts.setGenTimes(1);
        assertEquals(1, opts.getGenTimes());

        System.out.println("PASS: GenLODOptions chain builder works");
    }

    @Test
    public void testGenUVOptions() {
        GenUVOptions opts = GenUVOptions.create()
                .setName("my_uv")
                .setAlgorithmModel("hunyuan3D-UV-v2.0")
                .setEnableAutoSmoothing(true);

        assertEquals("my_uv", opts.getName());
        assertTrue(opts.getEnableAutoSmoothing());

        // Test false
        opts.setEnableAutoSmoothing(false);
        assertFalse(opts.getEnableAutoSmoothing());

        System.out.println("PASS: GenUVOptions chain builder works");
    }

    @Test
    public void testGenTextureOptions() {
        com.visvise.sdk.models.View view = new com.visvise.sdk.models.View();
        view.setMainView("https://example.com/ref.png");

        GenTextureOptions opts = GenTextureOptions.create()
                .setName("my_texture")
                .setAlgorithmModel("hunyuan3D-TEX-v2.0")
                .setInputView(view)
                .setResolution(2048)
                .setUnwarpUV(true)
                .setPrompt("high quality, realistic");

        assertEquals("my_texture", opts.getName());
        assertEquals(2048, opts.getResolution().intValue());
        assertTrue(opts.getUnwarpUV());
        assertEquals("high quality, realistic", opts.getPrompt());

        System.out.println("PASS: GenTextureOptions chain builder works");
    }

    @Test
    public void testGenRiggingOptions() {
        GenRiggingOptions opts = GenRiggingOptions.create()
                .setName("my_rigging")
                .setAlgorithmModel("VISVISE-GoRigging-V1.0.0")
                .setMeshCategory("humanoid");

        assertEquals("my_rigging", opts.getName());
        assertEquals("humanoid", opts.getMeshCategory());

        // Test tetrapod
        opts.setMeshCategory("tetrapod");
        assertEquals("tetrapod", opts.getMeshCategory());

        System.out.println("PASS: GenRiggingOptions chain builder works");
    }

    @Test
    public void testGenSkinningOptions() {
        GenSkinningOptions opts = GenSkinningOptions.create(
                Arrays.asList("Body_Mesh", "Hair_Mesh"),
                Arrays.asList("Bip001", "Bip001 Pelvis")
        ).setName("my_skinning")
                .setAlgorithmModel("VISVISE-GoSkinning-V1.0.0");

        assertEquals("my_skinning", opts.getName());
        assertEquals(2, opts.getMeshNames().size());
        assertEquals(2, opts.getJointNames().size());

        System.out.println("PASS: GenSkinningOptions chain builder works");
    }

    @Test
    public void testGenVideoMotionOptions() {
        GenVideoMotionOptions opts = GenVideoMotionOptions.create()
                .setName("my_video_motion")
                .setAlgorithmModel("VISVISE-FramingAI-Base-V1.5.0")
                .setOutputModelFormat(ModelFormat.FBX)
                .setWithHand(true)
                .setMultipleTrack(false)
                .setRotateAxisAngle(0, 0, 0);

        assertEquals("my_video_motion", opts.getName());
        assertTrue(opts.getWithHand());
        assertFalse(opts.getMultipleTrack());
        assertArrayEquals(new double[]{0, 0, 0}, opts.getRotateAxisAngle(), 0.001);

        System.out.println("PASS: GenVideoMotionOptions chain builder works");
    }

    @Test
    public void testGenTextMotionOptions() {
        GenTextMotionOptions opts = GenTextMotionOptions.create()
                .setName("my_text_motion")
                .setAlgorithmModel("VISVISE-TextMotion-V1.1.0")
                .setOutputModelFormat(ModelFormat.FBX);

        assertEquals("my_text_motion", opts.getName());
        assertEquals(ModelFormat.FBX, opts.getOutputModelFormat());

        System.out.println("PASS: GenTextMotionOptions chain builder works");
    }

    @Test
    public void testGenPoseOptions() {
        GenPoseOptions opts = GenPoseOptions.create()
                .setName("my_pose")
                .setAlgorithmModel("VISVISE-PosingAI-V1.0.0")
                .setOutputModelFormat(ModelFormat.FBX);

        assertEquals("my_pose", opts.getName());

        System.out.println("PASS: GenPoseOptions chain builder works");
    }

    @Test
    public void testGenSegment2DOptions() {
        com.visvise.sdk.models.View view = new com.visvise.sdk.models.View();
        view.setMainView("https://example.com/view.png");

        GenSegment2DOptions opts = GenSegment2DOptions.create()
                .setName("my_segment")
                .setAlgorithmModel("VISVISE-Seg2D-V1.0.0")
                .setInputView(view)
                .setSplitType(SegmentSplitType.FRONT_VIEW)
                .setGranularity(SegmentGranularity.MEDIUM)
                .setPrompt("segment by body parts")
                .setReadTimeout(120);

        assertEquals("my_segment", opts.getName());
        assertEquals(SegmentSplitType.FRONT_VIEW, opts.getSplitType());
        assertEquals(SegmentGranularity.MEDIUM, opts.getGranularity());
        assertEquals("segment by body parts", opts.getPrompt());
        assertEquals(120, opts.getReadTimeout());

        // Test four view
        opts.setSplitType(SegmentSplitType.FOUR_VIEW);
        assertEquals(SegmentSplitType.FOUR_VIEW, opts.getSplitType());

        // Test granularity variations
        opts.setGranularity(SegmentGranularity.COARSE);
        assertEquals(SegmentGranularity.COARSE, opts.getGranularity());

        opts.setGranularity(SegmentGranularity.FINE);
        assertEquals(SegmentGranularity.FINE, opts.getGranularity());

        System.out.println("PASS: GenSegment2DOptions chain builder works");
    }

    @Test
    public void testThinkingCallback() {
        final boolean[] called = {false};
        ThinkingCallback callback = content -> {
            called[0] = true;
            System.out.println("[Thinking] " + content);
        };

        callback.onThinking("Test thinking content");
        assertTrue(called[0]);

        System.out.println("PASS: ThinkingCallback works");
    }
}
