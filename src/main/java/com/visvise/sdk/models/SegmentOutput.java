package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * SegmentOutput represents the 2D split output. Only assets with node_type=14 (2D split) have it.
 *
 * Each view is filled according to the views that actually took part in the split:
 * main view is always present; top / bottom are only present when skip360Preprocess=true
 * and the corresponding view was passed in.
 *
 * The per-view data type is {@link SegmentData} (named SegmentViewData in the Python SDK).
 */
public class SegmentOutput {
    @SerializedName("main_view")
    private SegmentData mainView;

    @SerializedName("left_view")
    private SegmentData leftView;

    @SerializedName("right_view")
    private SegmentData rightView;

    @SerializedName("back_view")
    private SegmentData backView;

    @SerializedName("top_view")
    private SegmentData topView;

    @SerializedName("bottom_view")
    private SegmentData bottomView;

    /** original views sent to the algorithm */
    @SerializedName("origin_view")
    private View originView;

    /** color preview images of each view */
    @SerializedName("color_imgs")
    private SegmentColorImgs colorImgs;

    /** algorithm split output zip (presigned, valid for 24h) */
    @SerializedName("zip_path")
    private String zipPath;

    public SegmentData getMainView() {
        return mainView;
    }

    public void setMainView(SegmentData mainView) {
        this.mainView = mainView;
    }

    public SegmentData getLeftView() {
        return leftView;
    }

    public void setLeftView(SegmentData leftView) {
        this.leftView = leftView;
    }

    public SegmentData getRightView() {
        return rightView;
    }

    public void setRightView(SegmentData rightView) {
        this.rightView = rightView;
    }

    public SegmentData getBackView() {
        return backView;
    }

    public void setBackView(SegmentData backView) {
        this.backView = backView;
    }

    public SegmentData getTopView() {
        return topView;
    }

    public void setTopView(SegmentData topView) {
        this.topView = topView;
    }

    public SegmentData getBottomView() {
        return bottomView;
    }

    public void setBottomView(SegmentData bottomView) {
        this.bottomView = bottomView;
    }

    public View getOriginView() {
        return originView;
    }

    public void setOriginView(View originView) {
        this.originView = originView;
    }

    public SegmentColorImgs getColorImgs() {
        return colorImgs;
    }

    public void setColorImgs(SegmentColorImgs colorImgs) {
        this.colorImgs = colorImgs;
    }

    public String getZipPath() {
        return zipPath;
    }

    public void setZipPath(String zipPath) {
        this.zipPath = zipPath;
    }
}
