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
    /** optional, 2D segmentation asset ID */
    private String segmentModelId;

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

    public String getSegmentModelId() {
        return segmentModelId;
    }

    public GenMidModelOptions setSegmentModelId(String segmentModelId) {
        this.segmentModelId = segmentModelId;
        return this;
    }

    public static GenMidModelOptions create() {
        return new GenMidModelOptions();
    }
}
