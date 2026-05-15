package com.visvise.sdk.options;

import com.visvise.sdk.enums.OutputModelFormat;

/**
 * GenVideoMotionOptions defines optional parameters for GenVideoMotion
 */
public class GenVideoMotionOptions {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_video_motion";
    /** optional, algorithm model name */
    private String algorithmModel;
    /** optional, output format (default fbx) */
    private OutputModelFormat outputModelFormat = OutputModelFormat.FBX;
    /** optional, enable hand capture */
    private Boolean withHand;
    /** optional, enable multi-person capture */
    private Boolean multipleTrack;
    /** optional, rotation axis-angle [x, y, z] (radians) */
    private double[] rotateAxisAngle;

    public GenVideoMotionOptions() {
    }

    public String getName() {
        return name;
    }

    public GenVideoMotionOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenVideoMotionOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public OutputModelFormat getOutputModelFormat() {
        return outputModelFormat;
    }

    public GenVideoMotionOptions setOutputModelFormat(OutputModelFormat outputModelFormat) {
        this.outputModelFormat = outputModelFormat;
        return this;
    }

    public Boolean getWithHand() {
        return withHand;
    }

    public GenVideoMotionOptions setWithHand(Boolean withHand) {
        this.withHand = withHand;
        return this;
    }

    public Boolean getMultipleTrack() {
        return multipleTrack;
    }

    public GenVideoMotionOptions setMultipleTrack(Boolean multipleTrack) {
        this.multipleTrack = multipleTrack;
        return this;
    }

    public double[] getRotateAxisAngle() {
        return rotateAxisAngle;
    }

    public GenVideoMotionOptions setRotateAxisAngle(double x, double y, double z) {
        this.rotateAxisAngle = new double[]{x, y, z};
        return this;
    }

    public static GenVideoMotionOptions create() {
        return new GenVideoMotionOptions();
    }
}
