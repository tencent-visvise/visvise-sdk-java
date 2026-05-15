package com.visvise.sdk.options;

/**
 * GenUVOptions defines optional parameters for GenUV
 */
public class GenUVOptions {
    private String name = "gen_uv";
    private String algorithmModel;
    private Boolean enableAutoSmoothing;

    public GenUVOptions() {
    }

    public String getName() {
        return name;
    }

    public GenUVOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenUVOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public Boolean getEnableAutoSmoothing() {
        return enableAutoSmoothing;
    }

    public GenUVOptions setEnableAutoSmoothing(Boolean enableAutoSmoothing) {
        this.enableAutoSmoothing = enableAutoSmoothing;
        return this;
    }

    public static GenUVOptions create() {
        return new GenUVOptions();
    }
}
