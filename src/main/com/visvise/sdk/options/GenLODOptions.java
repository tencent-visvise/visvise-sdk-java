package com.visvise.sdk.options;

import com.visvise.sdk.enums.ModelFormat;

/**
 * GenLODOptions defines optional parameters for GenLOD
 */
public class GenLODOptions {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_lod";
    /** optional, algorithm model name */
    private String algorithmModel;
    /** optional, output format (default fbx) */
    private ModelFormat modelFormat = ModelFormat.FBX;
    /** optional, number of generations (default 3) */
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

    public ModelFormat getOutputModelFormat() {
        return modelFormat;
    }

    public GenLODOptions setOutputModelFormat(ModelFormat modelFormat) {
        this.modelFormat = modelFormat;
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
