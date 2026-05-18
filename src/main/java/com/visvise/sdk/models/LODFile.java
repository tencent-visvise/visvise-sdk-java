package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * LODFile represents a single LOD level output
 */
public class LODFile {
    @SerializedName("reduce_level")
    private int reduceLevel;

    @SerializedName("download_url")
    private String downloadUrl;

    @SerializedName("preview_img")
    private String previewImg;

    public int getReduceLevel() {
        return reduceLevel;
    }

    public void setReduceLevel(int reduceLevel) {
        this.reduceLevel = reduceLevel;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPreviewImg() {
        return previewImg;
    }

    public void setPreviewImg(String previewImg) {
        this.previewImg = previewImg;
    }
}
