package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * SegmentData represents the segmentation result data (2D split).
 */
public class SegmentData {
    private List<SegmentComponent> components;

    @SerializedName("mask_image")
    private String maskImage;

    private List<Integer> shape;

    @SerializedName("data_path")
    private String dataPath;

    @SerializedName("mask_image_path")
    private String maskImagePath;

    public List<SegmentComponent> getComponents() {
        return components;
    }

    public void setComponents(List<SegmentComponent> components) {
        this.components = components;
    }

    public String getMaskImage() {
        return maskImage;
    }

    public void setMaskImage(String maskImage) {
        this.maskImage = maskImage;
    }

    public List<Integer> getShape() {
        return shape;
    }

    public void setShape(List<Integer> shape) {
        this.shape = shape;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getMaskImagePath() {
        return maskImagePath;
    }

    public void setMaskImagePath(String maskImagePath) {
        this.maskImagePath = maskImagePath;
    }
}
