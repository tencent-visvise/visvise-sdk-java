package com.visvise.sdk.options;

import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.OutputModelFormat;

/**
 * GenHighModelOptions defines optional parameters for GenHighModel
 */
public class GenHighModelOptions {
    private String name = "gen_high_model";
    private String algorithmModel;
    private OutputModelFormat outputModelFormat = OutputModelFormat.FBX;
    private FaceType faceType = FaceType.TRIANGLE;
    private Integer faceNum;
    private Object backView;
    private Object leftView;
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

    public OutputModelFormat getOutputModelFormat() {
        return outputModelFormat;
    }

    public GenHighModelOptions setOutputModelFormat(OutputModelFormat outputModelFormat) {
        this.outputModelFormat = outputModelFormat;
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
