package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * SaveRTPConnectedResult represents the save_rtp_connected API response
 */
public class SaveRTPConnectedResult {
    /**
     * 新创建的 rtp-连通体资产 ID（node_type=19，status=3），
     * 作为第二段「重拓扑」（node_type=1）的 parent_model_id 使用
     */
    @SerializedName("model_id")
    private String modelId;

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
}
