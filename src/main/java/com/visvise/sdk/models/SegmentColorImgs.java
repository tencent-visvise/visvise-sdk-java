package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * SegmentColorImgs represents the color preview images of each view (2D split).
 *
 * All URLs are COS presigned URLs, valid for 24h.
 */
public class SegmentColorImgs {
    @SerializedName("main_view")
    private String mainView;

    @SerializedName("back_view")
    private String backView;

    @SerializedName("left_view")
    private String leftView;

    @SerializedName("right_view")
    private String rightView;

    @SerializedName("top_view")
    private String topView;

    @SerializedName("bottom_view")
    private String bottomView;

    public String getMainView() {
        return mainView;
    }

    public void setMainView(String mainView) {
        this.mainView = mainView;
    }

    public String getBackView() {
        return backView;
    }

    public void setBackView(String backView) {
        this.backView = backView;
    }

    public String getLeftView() {
        return leftView;
    }

    public void setLeftView(String leftView) {
        this.leftView = leftView;
    }

    public String getRightView() {
        return rightView;
    }

    public void setRightView(String rightView) {
        this.rightView = rightView;
    }

    public String getTopView() {
        return topView;
    }

    public void setTopView(String topView) {
        this.topView = topView;
    }

    public String getBottomView() {
        return bottomView;
    }

    public void setBottomView(String bottomView) {
        this.bottomView = bottomView;
    }
}
