package com.visvise.sdk;

import com.visvise.sdk.enums.*;
import com.visvise.sdk.models.ReduceFace;
import com.visvise.sdk.models.View;
import com.visvise.sdk.options.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Tests for complete workflow examples
 */
public class WorkflowTest {

    @Test
    public void testGen360Workflow() {
        // Simulate the workflow: Image -> Gen360 -> Get Multi-View Output
        Gen360Options opts = Gen360Options.create()
                .setAlgorithmModel("VISVISE-MultiView-V1.0.0")
                .setName("example_gen_360")
                .setEnableAPose(true);

        assertNotNull(opts);
        assertEquals("VISVISE-MultiView-V1.0.0", opts.getAlgorithmModel());
        assertEquals("example_gen_360", opts.getName());
        assertTrue(opts.getEnableAPose());

        // Simulate getting output view from ModelInfo
        View outputView = new View();
        outputView.setMainView("https://example.com/main_view.png");
        outputView.setBackView("https://example.com/back_view.png");
        outputView.setLeftView("https://example.com/left_view.png");
        outputView.setRightView("https://example.com/right_view.png");

        assertNotNull(outputView.getMainView());
        assertNotNull(outputView.getBackView());
        assertNotNull(outputView.getLeftView());
        assertNotNull(outputView.getRightView());

        // Use output view for GenHighModel
        GenHighModelOptions highOpts = GenHighModelOptions.create()
                .setBackView(outputView.getBackView())
                .setLeftView(outputView.getLeftView())
                .setRightView(outputView.getRightView());

        assertNotNull(highOpts.getBackView());
        assertNotNull(highOpts.getLeftView());
        assertNotNull(highOpts.getRightView());

        System.out.println("PASS: Gen360 workflow simulation works");
    }

    @Test
    public void testAnimationPipelineWorkflow() {
        // Simulate: Rigging -> Skinning -> Animation pipeline
        GenRiggingOptions riggingOpts = GenRiggingOptions.create()
                .setAlgorithmModel("VISVISE-GoRigging-V1.0.0")
                .setMeshCategory("humanoid");

        assertNotNull(riggingOpts);
        assertEquals("humanoid", riggingOpts.getMeshCategory());

        // Simulate rigged model output
        String riggedModelUrl = "https://example.com/rigged_model.fbx";

        // Skinning step
        GenSkinningOptions skinningOpts = GenSkinningOptions.create(
                Arrays.asList("Body_Mesh", "Hair_Mesh"),
                Arrays.asList("Bip001", "Bip001 Pelvis")
        ).setAlgorithmModel("VISVISE-GoSkinning-V1.0.0");

        assertNotNull(skinningOpts);
        assertEquals(2, skinningOpts.getMeshNames().size());
        assertEquals(2, skinningOpts.getJointNames().size());

        // Video Motion step
        GenVideoMotionOptions motionOpts = GenVideoMotionOptions.create()
                .setAlgorithmModel("VISVISE-FramingAI-Base-V1.5.0")
                .setWithHand(true)
                .setMultipleTrack(false)
                .setRotateAxisAngle(0, 0, 0);

        assertNotNull(motionOpts);
        assertTrue(motionOpts.getWithHand());
        assertFalse(motionOpts.getMultipleTrack());
        assertArrayEquals(new double[]{0, 0, 0}, motionOpts.getRotateAxisAngle(), 0.001);

        System.out.println("PASS: Animation pipeline workflow simulation works");
    }

    @Test
    public void testLODWorkflow() {
        // Simulate LOD generation with multi-shot
        ReduceFace rf1 = new ReduceFace(1, 50, FaceType.QUAD);
        ReduceFace rf2 = new ReduceFace(2, 25, FaceType.QUAD);

        assertEquals(1, rf1.getReduceLevel());
        assertEquals(50, rf1.getReducePercent());
        assertEquals(FaceType.QUAD, rf1.getFaceType());

        GenLODOptions opts = GenLODOptions.create()
                .setAlgorithmModel("VISVISE-LOD-V1.0.0")
                .setOutputModelFormat(ModelFormat.FBX)
                .setGenTimes(3);

        assertNotNull(opts);
        assertEquals(3, opts.getGenTimes());

        System.out.println("PASS: LOD workflow simulation works");
    }

    @Test
    public void testRetopologyWorkflow() {
        // Hunyuan model workflow
        GenRetopologyOptions hunyuanOpts = GenRetopologyOptions.create()
                .setAlgorithmModel("hunyuan3D-RTP-v1.5")
                .setOutputModelFormat(ModelFormat.FBX)
                .setFaceType(FaceType.QUAD)
                .setDetailLevel(DetailLevel.MEDIUM);

        assertEquals(hunyuanOpts.getDetailLevel(), DetailLevel.MEDIUM);

        // VISVISE model workflow
        GenRetopologyOptions visviseOpts = GenRetopologyOptions.create()
                .setAlgorithmModel("VISVISE-RTP-V1.0.0")
                .setFaceNum(50000);

        assertEquals(50000, visviseOpts.getFaceNum().intValue());
        assertNull(visviseOpts.getDetailLevel());

        System.out.println("PASS: Retopology workflow simulation works");
    }

    @Test
    public void testTextureWorkflow() {
        // Texture generation with reference view
        View refView = new View();
        refView.setMainView("https://example.com/ref_front.jpg");
        refView.setBackView("https://example.com/ref_back.jpg");

        GenTextureOptions opts = GenTextureOptions.create()
                .setAlgorithmModel("hunyuan3D-TEX-v2.0")
                .setInputView(refView)
                .setResolution(2048)
                .setUnwarpUV(true)
                .setPrompt("high quality, realistic");

        assertNotNull(opts.getInputView());
        assertEquals(Optional.of(2048), opts.getResolution());
        assertTrue(opts.getUnwarpUV());
        assertEquals("high quality, realistic", opts.getPrompt());

        // Text prompt only workflow
        GenTextureOptions textOnlyOpts = GenTextureOptions.create()
                .setAlgorithmModel("hunyuan3D-TEX-v2.0")
                .setPrompt("cartoon style, bright colors");

        assertNull(textOnlyOpts.getInputView());
        assertEquals("cartoon style, bright colors", textOnlyOpts.getPrompt());

        System.out.println("PASS: Texture workflow simulation works");
    }

    @Test
    public void testSegment2DWorkflow() {
        // 2D Segmentation workflow
        GenSegment2DOptions opts = GenSegment2DOptions.create()
                .setAlgorithmModel("VISVISE-Seg2D-V1.0.0")
                .setSplitType(SegmentSplitType.FOUR_VIEW)
                .setGranularity(SegmentGranularity.FINE)
                .setPrompt("segment by body parts");

        assertEquals(SegmentSplitType.FOUR_VIEW, opts.getSplitType());
        assertEquals(SegmentGranularity.FINE, opts.getGranularity());
        assertEquals("segment by body parts", opts.getPrompt());

        // Use segment result for GenMidModel
        String segModelId = "Model2026...";

        GenMidModelOptions midOpts = GenMidModelOptions.create()
                .setSegmentModelId(segModelId)
                .setAlgorithmModel("VISVISE-MeshGen-V1.0.0");

        assertEquals(segModelId, midOpts.getSegmentModelId());

        System.out.println("PASS: Segment2D workflow simulation works");
    }
}
