package com.visvise.sdk.options;

import com.visvise.sdk.enums.DetailLevel;
import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.ModelFormat;

/**
 * GenRetopologyOptions defines optional parameters for GenRetopology
 */
public class GenRetopologyOptions {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_retopology";
    /** optional, algorithm model name */
    private String algorithmModel;
    /** optional, output format (default fbx) */
    private ModelFormat modelFormat = ModelFormat.FBX;
    /** optional, face type (default quad) */
    private FaceType faceType = FaceType.QUAD;
    /** optional, for Hunyuan models: DetailLevel.LOW/MEDIUM/HIGH */
    private DetailLevel detailLevel;
    /** optional, for VISVISE models: target face count */
    private Integer faceNum;
    /** optional, 平滑强度，当前只支持 0.0（不平滑）/ 1.0（完全平滑），不传默认 1.0 */
    private Double smoothStrength;
    /**
     * optional, 父模型资产 ID：两段式自研（VV）重拓扑必传，
     * 指向第一段「重拓扑-连通体」资产（node_type=19）的 model_id
     */
    private String parentModelId;

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

    public ModelFormat getOutputModelFormat() {
        return modelFormat;
    }

    public GenRetopologyOptions setOutputModelFormat(ModelFormat modelFormat) {
        this.modelFormat = modelFormat;
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

    public Double getSmoothStrength() {
        return smoothStrength;
    }

    public GenRetopologyOptions setSmoothStrength(Double smoothStrength) {
        this.smoothStrength = smoothStrength;
        return this;
    }

    public String getParentModelId() {
        return parentModelId;
    }

    public GenRetopologyOptions setParentModelId(String parentModelId) {
        this.parentModelId = parentModelId;
        return this;
    }

    public static GenRetopologyOptions create() {
        return new GenRetopologyOptions();
    }
}
