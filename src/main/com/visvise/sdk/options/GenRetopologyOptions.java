package com.visvise.sdk.options;

import com.visvise.sdk.enums.DetailLevel;
import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.OutputModelFormat;

/**
 * GenRetopologyOptions defines optional parameters for GenRetopology
 */
public class GenRetopologyOptions {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_retopology";
    /** optional, algorithm model name */
    private String algorithmModel;
    /** optional, output format (default fbx) */
    private OutputModelFormat outputModelFormat = OutputModelFormat.FBX;
    /** optional, face type (default quad) */
    private FaceType faceType = FaceType.QUAD;
    /** optional, for Hunyuan models: DetailLevel.LOW/MEDIUM/HIGH */
    private DetailLevel detailLevel;
    /** optional, for VISVISE models: target face count */
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
