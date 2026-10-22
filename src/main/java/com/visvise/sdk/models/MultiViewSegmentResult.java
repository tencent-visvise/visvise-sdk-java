package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * MultiViewSegmentResult represents the multi-view result (2D split re-edit).
 */
public class MultiViewSegmentResult {
    @SerializedName("main_view_data")
    private OperatorResult mainViewData;

    @SerializedName("left_view_data")
    private OperatorResult leftViewData;

    @SerializedName("right_view_data")
    private OperatorResult rightViewData;

    @SerializedName("back_view_data")
    private OperatorResult backViewData;

    @SerializedName("top_view_data")
    private OperatorResult topViewData;

    @SerializedName("bottom_view_data")
    private OperatorResult bottomViewData;

    @SerializedName("client_id")
    private String clientId;

    /** algorithm split output zip (presigned, valid for 24h); empty on re-edit interfaces */
    @SerializedName("zip_path")
    private String zipPath;

    @SerializedName("origin_view")
    private View originView;

    public OperatorResult getMainViewData() {
        return mainViewData;
    }

    public void setMainViewData(OperatorResult mainViewData) {
        this.mainViewData = mainViewData;
    }

    public OperatorResult getLeftViewData() {
        return leftViewData;
    }

    public void setLeftViewData(OperatorResult leftViewData) {
        this.leftViewData = leftViewData;
    }

    public OperatorResult getRightViewData() {
        return rightViewData;
    }

    public void setRightViewData(OperatorResult rightViewData) {
        this.rightViewData = rightViewData;
    }

    public OperatorResult getBackViewData() {
        return backViewData;
    }

    public void setBackViewData(OperatorResult backViewData) {
        this.backViewData = backViewData;
    }

    public OperatorResult getTopViewData() {
        return topViewData;
    }

    public void setTopViewData(OperatorResult topViewData) {
        this.topViewData = topViewData;
    }

    public OperatorResult getBottomViewData() {
        return bottomViewData;
    }

    public void setBottomViewData(OperatorResult bottomViewData) {
        this.bottomViewData = bottomViewData;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getZipPath() {
        return zipPath;
    }

    public void setZipPath(String zipPath) {
        this.zipPath = zipPath;
    }

    public View getOriginView() {
        return originView;
    }

    public void setOriginView(View originView) {
        this.originView = originView;
    }
}
