package com.visvise.sdk.options;

import com.visvise.sdk.enums.OutputModelFormat;

/**
 * GenLODOptions defines optional parameters for GenLOD
 */
public class GenLODOptions {
    private String name = "gen_lod";
    private String algorithmModel;
    private OutputModelFormat outputModelFormat = OutputModelFormat.FBX;
    private int genTimes = 3;

    public GenLODOptions() {
    }

    public String getName() {
        return name;
    }

    public GenLODOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenLODOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public OutputModelFormat getOutputModelFormat() {
        return outputModelFormat;
    }

    public GenLODOptions setOutputModelFormat(OutputModelFormat outputModelFormat) {
        this.outputModelFormat = outputModelFormat;
        return this;
    }

    public int getGenTimes() {
        return genTimes;
    }

    public GenLODOptions setGenTimes(int genTimes) {
        this.genTimes = genTimes;
        return this;
    }

    public static GenLODOptions create() {
        return new GenLODOptions();
    }
}
