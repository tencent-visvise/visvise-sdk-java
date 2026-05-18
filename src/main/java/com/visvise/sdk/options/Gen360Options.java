package com.visvise.sdk.options;

import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.ModelFormat;

/**
 * Gen360Options defines optional parameters for Gen360
 */
public class Gen360Options {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_360";
    /** optional, algorithm model name; auto-selected if empty */
    private String algorithmModel;
    /** optional, output format (default fbx) */
    private ModelFormat modelFormat = ModelFormat.FBX;
    /** optional, face type (default triangle) */
    private FaceType faceType = FaceType.TRIANGLE;
    /** optional, enable A-Pose */
    private Boolean enableAPose;
    /** optional, style type (VISVISE proprietary models only)  灰模/超写实/Q版卡通/像素风格 */
    private String style;
    /** optional, back view to improve quality */
    private Object backView;
    /** optional, left view */
    private Object leftView;
    /** optional, right view */
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

    public ModelFormat getOutputModelFormat() {
        return modelFormat;
    }

    public Gen360Options setOutputModelFormat(ModelFormat modelFormat) {
        this.modelFormat = modelFormat;
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
