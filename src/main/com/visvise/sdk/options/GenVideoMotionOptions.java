package com.visvise.sdk.options;

import com.visvise.sdk.enums.OutputModelFormat;

/**
 * GenVideoMotionOptions defines optional parameters for GenVideoMotion
 */
public class GenVideoMotionOptions {
    private String name = "gen_video_motion";
    private String algorithmModel;
    private OutputModelFormat outputModelFormat = OutputModelFormat.FBX;
    private Boolean withHand;
    private Boolean multipleTrack;
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
