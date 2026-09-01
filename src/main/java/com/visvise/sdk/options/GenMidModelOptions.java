package com.visvise.sdk.options;

import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.ModelFormat;

/**
 * GenMidModelOptions defines optional parameters for GenMidModel
 */
public class GenMidModelOptions {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_mid_model";
    /** optional, algorithm model name */
    private String algorithmModel;
    /** optional, output format (default fbx) */
    private ModelFormat modelFormat = ModelFormat.FBX;
    /** optional, face type (default triangle) */
    private FaceType faceType = FaceType.TRIANGLE;
    /** optional, target face count (0-30000, 0 = auto) */
    private Integer faceNum;
    /** optional, 2D segmentation asset ID */
    private String segmentModelId;
    /** optional, 360 model asset ID */
    private String modelId360;
    /** optional, single component label of the 2D segmentation asset (used with segment_model_id) */
    private Integer componentLabel;
    /** optional, custom component grouping NPZ file (local path, COS URL, File, bytes, or InputStream) */
    private Object groupIds;
    /** optional, OBJ file containing all components (local path, COS URL, File, bytes, or InputStream) */
    private Object partMeshPath;
    /** optional, part_name to part_id mapping JSON file (local path, COS URL, File, bytes, or InputStream) */
    private Object labelToId;

    public GenMidModelOptions() {
    }

    public String getName() {
        return name;
    }

    public GenMidModelOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenMidModelOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public ModelFormat getOutputModelFormat() {
        return modelFormat;
    }

    public GenMidModelOptions setOutputModelFormat(ModelFormat modelFormat) {
        this.modelFormat = modelFormat;
        return this;
    }

    public FaceType getFaceType() {
        return faceType;
    }

    public GenMidModelOptions setFaceType(FaceType faceType) {
        this.faceType = faceType;
        return this;
    }

    public Integer getFaceNum() {
        return faceNum;
    }

    public GenMidModelOptions setFaceNum(Integer faceNum) {
        this.faceNum = faceNum;
        return this;
    }

    public String getSegmentModelId() {
        return segmentModelId;
    }

    public GenMidModelOptions setSegmentModelId(String segmentModelId) {
        this.segmentModelId = segmentModelId;
        return this;
    }

    public String getModelId360() {
        return modelId360;
    }

    public GenMidModelOptions setModelId360(String modelId360) {
        this.modelId360 = modelId360;
        return this;
    }

    public Integer getComponentLabel() {
        return componentLabel;
    }

    public GenMidModelOptions setComponentLabel(Integer componentLabel) {
        this.componentLabel = componentLabel;
        return this;
    }

    public Object getGroupIds() {
        return groupIds;
    }

    public GenMidModelOptions setGroupIds(Object groupIds) {
        this.groupIds = groupIds;
        return this;
    }

    public Object getPartMeshPath() {
        return partMeshPath;
    }

    public GenMidModelOptions setPartMeshPath(Object partMeshPath) {
        this.partMeshPath = partMeshPath;
        return this;
    }

    public Object getLabelToId() {
        return labelToId;
    }

    public GenMidModelOptions setLabelToId(Object labelToId) {
        this.labelToId = labelToId;
        return this;
    }

    public static GenMidModelOptions create() {
        return new GenMidModelOptions();
    }
}
