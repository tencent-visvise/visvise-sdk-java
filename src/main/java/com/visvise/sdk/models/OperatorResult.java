package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * OperatorResult represents a single-view operation result (2D split re-edit).
 */
public class OperatorResult {
    @SerializedName("client_id")
    private String clientId;

    @SerializedName("segment_data")
    private SegmentData segmentData;

    @SerializedName("enable_revoke")
    private boolean enableRevoke;

    @SerializedName("enable_redo")
    private boolean enableRedo;

    @SerializedName("origin_view")
    private View originView;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public SegmentData getSegmentData() {
        return segmentData;
    }

    public void setSegmentData(SegmentData segmentData) {
        this.segmentData = segmentData;
    }

    public boolean isEnableRevoke() {
        return enableRevoke;
    }

    public void setEnableRevoke(boolean enableRevoke) {
        this.enableRevoke = enableRevoke;
    }

    public boolean isEnableRedo() {
        return enableRedo;
    }

    public void setEnableRedo(boolean enableRedo) {
        this.enableRedo = enableRedo;
    }

    public View getOriginView() {
        return originView;
    }

    public void setOriginView(View originView) {
        this.originView = originView;
    }
}
