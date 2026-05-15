package com.visvise.sdk.options;

import com.visvise.sdk.enums.OutputModelFormat;

/**
 * GenPoseOptions defines optional parameters for GenPose
 */
public class GenPoseOptions {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_pose";
    /** optional, algorithm model name */
    private String algorithmModel;
    /** optional, output format (default fbx) */
    private OutputModelFormat outputModelFormat = OutputModelFormat.FBX;

    public GenPoseOptions() {
    }

    public String getName() {
        return name;
    }

    public GenPoseOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenPoseOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public OutputModelFormat getOutputModelFormat() {
        return outputModelFormat;
    }

    public GenPoseOptions setOutputModelFormat(OutputModelFormat outputModelFormat) {
        this.outputModelFormat = outputModelFormat;
        return this;
    }

    public static GenPoseOptions create() {
        return new GenPoseOptions();
    }
}
