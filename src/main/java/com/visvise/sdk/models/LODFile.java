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

    /**
     * 候选标识 version1/version2/...，按算法返回的原始次序编号；
     * 与 LODOutput.qcBestVersion 对应，用于定位质检选中了哪个候选
     */
    @SerializedName("version")
    private String version;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
