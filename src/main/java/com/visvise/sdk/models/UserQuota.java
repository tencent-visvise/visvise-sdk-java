package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * UserQuota represents the get_user_quota API response
 */
public class UserQuota {
    private int quota;

    @SerializedName("server_ts")
    private long serverTs;

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public long getServerTs() {
        return serverTs;
    }

    public void setServerTs(long serverTs) {
        this.serverTs = serverTs;
    }
}
