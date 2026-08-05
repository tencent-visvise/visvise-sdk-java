package com.visvise.sdk.options;

/**
 * GenPatterAutoRemoveOptions defines optional parameters for the high-level pattern auto-removal workflow.
 */
public class GenPatterAutoRemoveOptions {
    private String name = "gen_patter_auto_remove";
    private String algorithmModel;

    public GenPatterAutoRemoveOptions() {
    }

    public String getName() {
        return name;
    }

    public GenPatterAutoRemoveOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenPatterAutoRemoveOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public static GenPatterAutoRemoveOptions create() {
        return new GenPatterAutoRemoveOptions();
    }
}
