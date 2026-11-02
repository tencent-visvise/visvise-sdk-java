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

    /**
     * LOD 质检择优（enable_qc=true）时的结果字段，未开启质检时均为空。
     * 空=未开启质检 / completed=全部候选出分 / partial=部分候选出分 /
     * failed=质检跑了但无候选出分 / call_failed=质检服务没调通
     */
    @SerializedName("qc_status")
    private String qcStatus;

    /** 质检选中的最优候选 version，可与 LODFile.version 比对定位；兜底时为空 */
    @SerializedName("qc_best_version")
    private String qcBestVersion;

    /** 质检选中的单个候选（算法侧产物，实测为只含该候选的 zip）；兜底时为第一个候选 */
    @SerializedName("qc_file")
    private String qcFile;

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

    public String getQcStatus() {
        return qcStatus;
    }

    public void setQcStatus(String qcStatus) {
        this.qcStatus = qcStatus;
    }

    public String getQcBestVersion() {
        return qcBestVersion;
    }

    public void setQcBestVersion(String qcBestVersion) {
        this.qcBestVersion = qcBestVersion;
    }

    public String getQcFile() {
        return qcFile;
    }

    public void setQcFile(String qcFile) {
        this.qcFile = qcFile;
    }
}
