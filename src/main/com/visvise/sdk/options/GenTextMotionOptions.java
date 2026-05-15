package com.visvise.sdk.options;

import com.visvise.sdk.enums.OutputModelFormat;

/**
 * GenTextMotionOptions defines optional parameters for GenTextMotion
 */
public class GenTextMotionOptions {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_text_motion";
    /** optional, algorithm model name */
    private String algorithmModel;
    /** optional, output format (default fbx) */
    private OutputModelFormat outputModelFormat = OutputModelFormat.FBX;

    public GenTextMotionOptions() {
    }

    public String getName() {
        return name;
    }

    public GenTextMotionOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenTextMotionOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public OutputModelFormat getOutputModelFormat() {
        return outputModelFormat;
    }

    public GenTextMotionOptions setOutputModelFormat(OutputModelFormat outputModelFormat) {
        this.outputModelFormat = outputModelFormat;
        return this;
    }

    public static GenTextMotionOptions create() {
        return new GenTextMotionOptions();
    }
}
