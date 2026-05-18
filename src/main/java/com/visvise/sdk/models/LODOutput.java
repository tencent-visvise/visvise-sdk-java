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
}
