package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * CosCred represents the COS temporary credentials
 */
public class CosCred {
    @SerializedName("tmp_secret_id")
    private String tmpSecretId;

    @SerializedName("tmp_secret_key")
    private String tmpSecretKey;

    @SerializedName("session_token")
    private String sessionToken;

    public CosCred() {
    }

    public String getTmpSecretId() {
        return tmpSecretId;
    }

    public void setTmpSecretId(String tmpSecretId) {
        this.tmpSecretId = tmpSecretId;
    }

    public String getTmpSecretKey() {
        return tmpSecretKey;
    }

    public void setTmpSecretKey(String tmpSecretKey) {
        this.tmpSecretKey = tmpSecretKey;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
