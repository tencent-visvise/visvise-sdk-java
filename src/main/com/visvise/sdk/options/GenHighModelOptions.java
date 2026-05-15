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
