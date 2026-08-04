package com.visvise.sdk.options;

/**
 * GenStyleTransferOptions defines optional parameters for the high-level style transfer workflow.
 */
public class GenStyleTransferOptions {
    private String name = "gen_style_transfer";
    private String algorithmModel;

    public GenStyleTransferOptions() {
    }

    public String getName() {
        return name;
    }

    public GenStyleTransferOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenStyleTransferOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public static GenStyleTransferOptions create() {
        return new GenStyleTransferOptions();
    }
}
