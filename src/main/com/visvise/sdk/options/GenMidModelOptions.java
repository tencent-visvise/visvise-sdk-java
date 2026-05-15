package com.visvise.sdk.options;

import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.OutputModelFormat;

/**
 * GenMidModelOptions defines optional parameters for GenMidModel
 */
public class GenMidModelOptions {
    private String name = "gen_mid_model";
    private String algorithmModel;
    private OutputModelFormat outputModelFormat = OutputModelFormat.FBX;
    private FaceType faceType = FaceType.TRIANGLE;
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

    public OutputModelFormat getOutputModelFormat() {
        return outputModelFormat;
    }

    public GenMidModelOptions setOutputModelFormat(OutputModelFormat outputModelFormat) {
        this.outputModelFormat = outputModelFormat;
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
