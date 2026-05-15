package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * Text2Motion represents a single text-to-motion output
 */
public class Text2Motion {
    @SerializedName("output_model")
    private String outputModel;

    @SerializedName("preview_img")
    private String previewImg;

    public String getOutputModel() {
        return outputModel;
    }

    public void setOutputModel(String outputModel) {
        this.outputModel = outputModel;
    }

    public String getPreviewImg() {
        return previewImg;
    }

    public void setPreviewImg(String previewImg) {
        this.previewImg = previewImg;
    }
}
