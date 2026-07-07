package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * UserQuota represents the get_user_quota API response
 */
public class UserQuota {
    @SerializedName("model_quota")
    private int modelQuota;

    @SerializedName("animation_quota")
    private int animationQuota;

    @SerializedName("server_ts")
    private long serverTs;

    public int getModelQuota() {
        return modelQuota;
    }

    public void setModelQuota(int modelQuota) {
        this.modelQuota = modelQuota;
    }

    public int getAnimationQuota() {
        return animationQuota;
    }

    public void setAnimationQuota(int animationQuota) {
        this.animationQuota = animationQuota;
    }

    public long getServerTs() {
        return serverTs;
    }

    public void setServerTs(long serverTs) {
        this.serverTs = serverTs;
    }
}
