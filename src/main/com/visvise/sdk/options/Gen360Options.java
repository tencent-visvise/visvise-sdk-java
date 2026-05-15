package com.visvise.sdk.options;

import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.OutputModelFormat;
import com.visvise.sdk.models.View;

/**
 * Gen360Options defines optional parameters for Gen360
 */
public class Gen360Options {
    private String name = "gen_360";
    private String algorithmModel;
    private OutputModelFormat outputModelFormat = OutputModelFormat.FBX;
    private FaceType faceType = FaceType.TRIANGLE;
    private Boolean enableAPose;
    private String style;
    private Object backView;
    private Object leftView;
    private Object rightView;

    public Gen360Options() {
    }

    public String getName() {
        return name;
    }

    public Gen360Options setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public Gen360Options setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public OutputModelFormat getOutputModelFormat() {
        return outputModelFormat;
    }

    public Gen360Options setOutputModelFormat(OutputModelFormat outputModelFormat) {
        this.outputModelFormat = outputModelFormat;
        return this;
    }

    public FaceType getFaceType() {
        return faceType;
    }

    public Gen360Options setFaceType(FaceType faceType) {
        this.faceType = faceType;
        return this;
    }

    public Boolean getEnableAPose() {
        return enableAPose;
    }

    public Gen360Options setEnableAPose(Boolean enableAPose) {
        this.enableAPose = enableAPose;
        return this;
    }

    public String getStyle() {
        return style;
    }

    public Gen360Options setStyle(String style) {
        this.style = style;
        return this;
    }

    public Object getBackView() {
        return backView;
    }

    public Gen360Options setBackView(Object backView) {
        this.backView = backView;
        return this;
    }

    public Object getLeftView() {
        return leftView;
    }

    public Gen360Options setLeftView(Object leftView) {
        this.leftView = leftView;
        return this;
    }

    public Object getRightView() {
        return rightView;
    }

    public Gen360Options setRightView(Object rightView) {
        this.rightView = rightView;
        return this;
    }

    public static Gen360Options create() {
        return new Gen360Options();
    }
}
