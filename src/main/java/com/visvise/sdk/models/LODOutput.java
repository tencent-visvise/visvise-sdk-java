package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * LODOutput represents the LOD output file collection
 */
public class LODOutput {
    @SerializedName("lod_files")
    private List<LODFile> lodFiles;

    @SerializedName("zip_file")
    private String zipFile;

    @SerializedName("del_times")
    private int delTimes;

    @SerializedName("del_card_indexs")
    private List<Integer> delCardIndexs;

    public List<LODFile> getLodFiles() {
        return lodFiles;
    }

    public void setLodFiles(List<LODFile> lodFiles) {
        this.lodFiles = lodFiles;
    }

    public String getZipFile() {
        return zipFile;
    }

    public void setZipFile(String zipFile) {
        this.zipFile = zipFile;
    }

    public int getDelTimes() {
        return delTimes;
    }

    public void setDelTimes(int delTimes) {
        this.delTimes = delTimes;
    }

    public List<Integer> getDelCardIndexs() {
        return delCardIndexs;
    }

    public void setDelCardIndexs(List<Integer> delCardIndexs) {
        this.delCardIndexs = delCardIndexs;
    }
}
