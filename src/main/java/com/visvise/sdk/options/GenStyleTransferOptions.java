package com.visvise.sdk.options;

import com.visvise.sdk.enums.StyleType;

/**
 * GenStyleTransferOptions defines optional parameters for the high-level style transfer workflow.
 */
public class GenStyleTransferOptions {
    private String name = "gen_style_transfer";
    private String algorithmModel;
    private StyleType styleType = StyleType.GRAYSCALE;

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

    public StyleType getStyleType() {
        return styleType;
    }

    public GenStyleTransferOptions setStyleType(StyleType styleType) {
        this.styleType = styleType;
        return this;
    }

    public static GenStyleTransferOptions create() {
        return new GenStyleTransferOptions();
    }
}
