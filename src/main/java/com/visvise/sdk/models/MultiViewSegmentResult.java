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

    @SerializedName("client_id")
    private String clientId;

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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public View getOriginView() {
        return originView;
    }

    public void setOriginView(View originView) {
        this.originView = originView;
    }
}
