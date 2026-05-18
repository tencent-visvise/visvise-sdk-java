package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * GetCosCredResult represents the get_cos_cred API response
 */
public class GetCosCredResult {
    private CosCred cred;

    @SerializedName("start_time")
    private long startTime;

    @SerializedName("expired_time")
    private long expiredTime;

    private String bucket;

    private String region;

    @SerializedName("path_prefix")
    private String pathPrefix;

    public CosCred getCred() {
        return cred;
    }

    public void setCred(CosCred cred) {
        this.cred = cred;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPathPrefix() {
        return pathPrefix;
    }

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }
}
