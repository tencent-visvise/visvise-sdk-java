package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * ModelAutoMergeResult represents the model_auto_merge (模型自动合并) API response.
 */
public class ModelAutoMergeResult {
    /** 合并后模型的 COS 路径（带临时签名，24h 有效） */
    @SerializedName("output_obj_path")
    private String outputObjPath;

    /** 算法耗时，单位秒 */
    @SerializedName("elapsed_time")
    private double elapsedTime;

    /** 参考模型补齐的部件名列表；未传 ref_mesh_path 时为空 */
    @SerializedName("ref_filled_parts")
    private List<String> refFilledParts;

    /** 部件名 -> 该部件与参考部件的 bbox IoU */
    @SerializedName("part_bbox_iou")
    private Map<String, Double> partBboxIou;

    /** 所有部件的平均 bbox IoU */
    @SerializedName("mean_bbox_iou")
    private double meanBboxIou;

    public String getOutputObjPath() {
        return outputObjPath;
    }

    public void setOutputObjPath(String outputObjPath) {
        this.outputObjPath = outputObjPath;
    }

    public double getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(double elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public List<String> getRefFilledParts() {
        return refFilledParts;
    }

    public void setRefFilledParts(List<String> refFilledParts) {
        this.refFilledParts = refFilledParts;
    }

    public Map<String, Double> getPartBboxIou() {
        return partBboxIou;
    }

    public void setPartBboxIou(Map<String, Double> partBboxIou) {
        this.partBboxIou = partBboxIou;
    }

    public double getMeanBboxIou() {
        return meanBboxIou;
    }

    public void setMeanBboxIou(double meanBboxIou) {
        this.meanBboxIou = meanBboxIou;
    }
}
