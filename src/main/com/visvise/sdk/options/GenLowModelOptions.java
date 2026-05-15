package com.visvise.sdk.options;

import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.OutputModelFormat;

/**
 * GenLowModelOptions defines optional parameters for GenLowModel
 */
public class GenLowModelOptions {
    private String name = "gen_low_model";
    private String algorithmModel;
    private OutputModelFormat outputModelFormat = OutputModelFormat.FBX;
    private FaceType faceType = FaceType.TRIANGLE;
    private Object backView;
    private Object leftView;
    private Object rightView;

    public GenLowModelOptions() {
    }

    public String getName() {
        return name;
    }

    public GenLowModelOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenLowModelOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public OutputModelFormat getOutputModelFormat() {
        return outputModelFormat;
    }

    public GenLowModelOptions setOutputModelFormat(OutputModelFormat outputModelFormat) {
        this.outputModelFormat = outputModelFormat;
        return this;
    }

    public FaceType getFaceType() {
        return faceType;
    }

    public GenLowModelOptions setFaceType(FaceType faceType) {
        this.faceType = faceType;
        return this;
    }

    public Object getBackView() {
        return backView;
    }

    public GenLowModelOptions setBackView(Object backView) {
        this.backView = backView;
        return this;
    }

    public Object getLeftView() {
        return leftView;
    }

    public GenLowModelOptions setLeftView(Object leftView) {
        this.leftView = leftView;
        return this;
    }

    public Object getRightView() {
        return rightView;
    }

    public GenLowModelOptions setRightView(Object rightView) {
        this.rightView = rightView;
        return this;
    }

    public static GenLowModelOptions create() {
        return new GenLowModelOptions();
    }
}
