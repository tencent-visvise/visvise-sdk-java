package com.visvise.sdk.options;

import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.OutputModelFormat;

/**
 * GenLowModelOptions defines optional parameters for GenLowModel
 */
public class GenLowModelOptions {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_low_model";
    /** optional, algorithm model name */
    private String algorithmModel;
    /** optional, output format (default fbx) */
    private OutputModelFormat outputModelFormat = OutputModelFormat.FBX;
    /** optional, face type (default triangle) */
    private FaceType faceType = FaceType.TRIANGLE;
    /** optional, back view */
    private Object backView;
    /** optional, left view */
    private Object leftView;
    /** optional, right view */
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
