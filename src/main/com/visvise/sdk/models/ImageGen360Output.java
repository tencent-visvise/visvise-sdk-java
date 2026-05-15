package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * ImageGen360Output represents the image to 360 output result
 */
public class ImageGen360Output {
    @SerializedName("output_view")
    private View outputView;

    @SerializedName("horizontal_view_video")
    private String horizontalViewVideo;

    public View getOutputView() {
        return outputView;
    }

    public void setOutputView(View outputView) {
        this.outputView = outputView;
    }

    public String getHorizontalViewVideo() {
        return horizontalViewVideo;
    }

    public void setHorizontalViewVideo(String horizontalViewVideo) {
        this.horizontalViewVideo = horizontalViewVideo;
    }
}
