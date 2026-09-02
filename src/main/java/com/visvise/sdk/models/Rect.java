package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * Rect represents a rectangle selection (2D split re-edit).
 */
public class Rect {
    @SerializedName("left_top_pixel")
    private Pixel leftTopPixel;

    @SerializedName("right_bottom_pixel")
    private Pixel rightBottomPixel;

    public Rect() {
    }

    public Rect(Pixel leftTopPixel, Pixel rightBottomPixel) {
        this.leftTopPixel = leftTopPixel;
        this.rightBottomPixel = rightBottomPixel;
    }

    public Pixel getLeftTopPixel() {
        return leftTopPixel;
    }

    public void setLeftTopPixel(Pixel leftTopPixel) {
        this.leftTopPixel = leftTopPixel;
    }

    public Pixel getRightBottomPixel() {
        return rightBottomPixel;
    }

    public void setRightBottomPixel(Pixel rightBottomPixel) {
        this.rightBottomPixel = rightBottomPixel;
    }
}
