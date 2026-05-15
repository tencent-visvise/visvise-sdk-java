package com.visvise.sdk.options;

import com.visvise.sdk.enums.DetailLevel;
import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.OutputModelFormat;

/**
 * GenRetopologyOptions defines optional parameters for GenRetopology
 */
public class GenRetopologyOptions {
    private String name = "gen_retopology";
    private String algorithmModel;
    private OutputModelFormat outputModelFormat = OutputModelFormat.FBX;
    private FaceType faceType = FaceType.QUAD;
    private DetailLevel detailLevel;
    private Integer faceNum;

    public GenRetopologyOptions() {
    }

    public String getName() {
        return name;
    }

    public GenRetopologyOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenRetopologyOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public OutputModelFormat getOutputModelFormat() {
        return outputModelFormat;
    }

    public GenRetopologyOptions setOutputModelFormat(OutputModelFormat outputModelFormat) {
        this.outputModelFormat = outputModelFormat;
        return this;
    }

    public FaceType getFaceType() {
        return faceType;
    }

    public GenRetopologyOptions setFaceType(FaceType faceType) {
        this.faceType = faceType;
        return this;
    }

    public DetailLevel getDetailLevel() {
        return detailLevel;
    }

    public GenRetopologyOptions setDetailLevel(DetailLevel detailLevel) {
        this.detailLevel = detailLevel;
        return this;
    }

    public Integer getFaceNum() {
        return faceNum;
    }

    public GenRetopologyOptions setFaceNum(Integer faceNum) {
        this.faceNum = faceNum;
        return this;
    }

    public static GenRetopologyOptions create() {
        return new GenRetopologyOptions();
    }
}
