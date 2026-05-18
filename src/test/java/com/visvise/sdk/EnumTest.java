package com.visvise.sdk;

import com.visvise.sdk.enums.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for enum types
 */
public class EnumTest {

    @Test
    public void testNodeType() {
        assertEquals(1, NodeType.RE_TOPOLOGY.getValue());
        assertEquals(2, NodeType.LOD.getValue());
        assertEquals(3, NodeType.IMG_TO_3D_HIGH.getValue());
        assertEquals(4, NodeType.ANIMATION.getValue());
        assertEquals(5, NodeType.RIGGING.getValue());
        assertEquals(6, NodeType.SKINNING.getValue());
        assertEquals(7, NodeType.IMG_TO_360.getValue());
        assertEquals(8, NodeType.TEXTURE.getValue());
        assertEquals(9, NodeType.UV.getValue());
        assertEquals(10, NodeType.MESH_REFINE.getValue());
        assertEquals(11, NodeType.IMG_TO_3D_MID.getValue());
        assertEquals(12, NodeType.IMG_TO_POSE.getValue());
        assertEquals(13, NodeType.IMG_TO_3D_LOW.getValue());
        assertEquals(14, NodeType.SEGMENT_2D.getValue());

        assertEquals(NodeType.IMG_TO_360, NodeType.fromValue(7));
        assertThrows(IllegalArgumentException.class, () -> NodeType.fromValue(99));

        System.out.println("PASS: NodeType enum works correctly");
    }

    @Test
    public void testModelStatus() {
        assertEquals(0, ModelStatus.INVALID.getValue());
        assertEquals(1, ModelStatus.PENDING.getValue());
        assertEquals(2, ModelStatus.RUNNING.getValue());
        assertEquals(3, ModelStatus.SUCCESS.getValue());
        assertEquals(4, ModelStatus.FAILED.getValue());

        assertEquals(ModelStatus.SUCCESS, ModelStatus.fromValue(3));
        assertThrows(IllegalArgumentException.class, () -> ModelStatus.fromValue(99));

        System.out.println("PASS: ModelStatus enum works correctly");
    }

    @Test
    public void testFaceType() {
        assertEquals(1, FaceType.TRIANGLE.getValue());
        assertEquals(2, FaceType.QUAD.getValue());

        assertEquals(FaceType.TRIANGLE, FaceType.fromValue(1));
        assertEquals(FaceType.QUAD, FaceType.fromValue(2));
        assertThrows(IllegalArgumentException.class, () -> FaceType.fromValue(99));

        System.out.println("PASS: FaceType enum works correctly");
    }

    @Test
    public void testDetailLevel() {
        assertEquals(1, DetailLevel.LOW.getValue());
        assertEquals(2, DetailLevel.MEDIUM.getValue());
        assertEquals(3, DetailLevel.HIGH.getValue());

        assertEquals(DetailLevel.LOW, DetailLevel.fromValue(1));
        assertEquals(DetailLevel.MEDIUM, DetailLevel.fromValue(2));
        assertEquals(DetailLevel.HIGH, DetailLevel.fromValue(3));
        assertThrows(IllegalArgumentException.class, () -> DetailLevel.fromValue(99));

        System.out.println("PASS: DetailLevel enum works correctly");
    }

    @Test
    public void testOutputModelFormat() {
        assertEquals("fbx", ModelFormat.FBX.getValue());
        assertEquals("obj", ModelFormat.OBJ.getValue());
        assertEquals("glb", ModelFormat.GLB.getValue());

        assertEquals(ModelFormat.FBX, ModelFormat.fromValue("fbx"));
        assertEquals(ModelFormat.OBJ, ModelFormat.fromValue("obj"));
        assertEquals(ModelFormat.GLB, ModelFormat.fromValue("glb"));
        assertThrows(IllegalArgumentException.class, () -> ModelFormat.fromValue("unknown"));

        System.out.println("PASS: OutputModelFormat enum works correctly");
    }

    @Test
    public void testMeshRefineMode() {
        assertEquals(1, MeshRefineMode.OPTIMIZE.getValue());
        assertEquals(2, MeshRefineMode.DENSIFY.getValue());

        assertEquals(MeshRefineMode.OPTIMIZE, MeshRefineMode.fromValue(1));
        assertEquals(MeshRefineMode.DENSIFY, MeshRefineMode.fromValue(2));
        assertThrows(IllegalArgumentException.class, () -> MeshRefineMode.fromValue(99));

        System.out.println("PASS: MeshRefineMode enum works correctly");
    }

    @Test
    public void testSegmentSplitType() {
        assertEquals(1, SegmentSplitType.FRONT_VIEW.getValue());
        assertEquals(2, SegmentSplitType.FOUR_VIEW.getValue());

        assertEquals(SegmentSplitType.FRONT_VIEW, SegmentSplitType.fromValue(1));
        assertEquals(SegmentSplitType.FOUR_VIEW, SegmentSplitType.fromValue(2));
        assertThrows(IllegalArgumentException.class, () -> SegmentSplitType.fromValue(99));

        System.out.println("PASS: SegmentSplitType enum works correctly");
    }

    @Test
    public void testSegmentGranularity() {
        assertEquals(1, SegmentGranularity.COARSE.getValue());
        assertEquals(2, SegmentGranularity.MEDIUM.getValue());
        assertEquals(3, SegmentGranularity.FINE.getValue());

        assertEquals(SegmentGranularity.COARSE, SegmentGranularity.fromValue(1));
        assertEquals(SegmentGranularity.MEDIUM, SegmentGranularity.fromValue(2));
        assertEquals(SegmentGranularity.FINE, SegmentGranularity.fromValue(3));
        assertThrows(IllegalArgumentException.class, () -> SegmentGranularity.fromValue(99));

        System.out.println("PASS: SegmentGranularity enum works correctly");
    }

    @Test
    public void testAnimationSubType() {
        assertEquals(1, AnimationSubType.VIDEO.getValue());
        assertEquals(2, AnimationSubType.TEXT.getValue());

        assertEquals(AnimationSubType.VIDEO, AnimationSubType.fromValue(1));
        assertEquals(AnimationSubType.TEXT, AnimationSubType.fromValue(2));
        assertThrows(IllegalArgumentException.class, () -> AnimationSubType.fromValue(99));

        System.out.println("PASS: AnimationSubType enum works correctly");
    }

    @Test
    public void testEnvironment() {
        assertEquals("https://ws.visvise.com.cn", Environment.PROD.getValue());
        assertEquals("https://qa-ws.visvise.com.cn", Environment.TEST.getValue());
        assertEquals("https://dev-ws.visvise.com.cn", Environment.DEV.getValue());

        assertEquals(Environment.PROD, Environment.fromValue("https://ws.visvise.com.cn"));
        assertEquals(Environment.TEST, Environment.fromValue("https://qa-ws.visvise.com.cn"));
        assertEquals(Environment.DEV, Environment.fromValue("https://dev-ws.visvise.com.cn"));
        assertThrows(IllegalArgumentException.class, () -> Environment.fromValue("unknown"));

        System.out.println("PASS: Environment enum works correctly");
    }
}
