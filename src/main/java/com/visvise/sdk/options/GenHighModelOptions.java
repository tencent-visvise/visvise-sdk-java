package com.visvise.sdk.options;

import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.ModelFormat;

/**
 * GenHighModelOptions defines optional parameters for GenHighModel
 */
public class GenHighModelOptions {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_high_model";
    /** optional, algorithm model name; auto-selected if empty */
    private String algorithmModel;
    /** optional, output format (default fbx) */
    private ModelFormat modelFormat = ModelFormat.FBX;
    /** optional, face type (default triangle) */
    private FaceType faceType = FaceType.TRIANGLE;
    /** optional, target face count (1000-1500000) */
    private Integer faceNum;
    /** optional, enable PBR material (default false) */
    private Boolean enablePbr = false;
    /** optional, whether to force target face count; if false, adjusts by geometry error (default false) */
    private Boolean strictMode = false;
    /** optional, back view to improve quality */
    private Object backView;
    /** optional, left view */
    private Object leftView;
    /** optional, right view */
    private Object rightView;

    public GenHighModelOptions() {
    }

    public String getName() {
        return name;
    }

    public GenHighModelOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenHighModelOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public ModelFormat getOutputModelFormat() {
        return modelFormat;
    }

    public GenHighModelOptions setOutputModelFormat(ModelFormat modelFormat) {
        this.modelFormat = modelFormat;
        return this;
    }

    public FaceType getFaceType() {
        return faceType;
    }

    public GenHighModelOptions setFaceType(FaceType faceType) {
        this.faceType = faceType;
        return this;
    }

    public Integer getFaceNum() {
        return faceNum;
    }

    public GenHighModelOptions setFaceNum(Integer faceNum) {
        this.faceNum = faceNum;
        return this;
    }

    public Boolean getEnablePbr() {
        return enablePbr;
    }

    public GenHighModelOptions setEnablePbr(Boolean enablePbr) {
        this.enablePbr = enablePbr;
        return this;
    }

    public Boolean getStrictMode() {
        return strictMode;
    }

    public GenHighModelOptions setStrictMode(Boolean strictMode) {
        this.strictMode = strictMode;
        return this;
    }

    public Object getBackView() {
        return backView;
    }

    public GenHighModelOptions setBackView(Object backView) {
        this.backView = backView;
        return this;
    }

    public Object getLeftView() {
        return leftView;
    }

    public GenHighModelOptions setLeftView(Object leftView) {
        this.leftView = leftView;
        return this;
    }

    public Object getRightView() {
        return rightView;
    }

    public GenHighModelOptions setRightView(Object rightView) {
        this.rightView = rightView;
        return this;
    }

    public static GenHighModelOptions create() {
        return new GenHighModelOptions();
    }
}
