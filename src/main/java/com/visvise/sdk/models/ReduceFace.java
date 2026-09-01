package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;
import com.visvise.sdk.enums.FaceType;

import java.util.HashMap;
import java.util.Map;

/**
 * ReduceFace represents the LOD single level reduce face configuration
 */
public class ReduceFace {
    @SerializedName("reduce_level")
    private int reduceLevel;

    @SerializedName("reduce_percent")
    private int reducePercent;

    @SerializedName("face_type")
    private FaceType faceType;  // 1: Triangle, 2: Quad

    @SerializedName("face_num")
    private Integer faceNum;

    @SerializedName("face_tab")
    private Integer faceTab;

    public ReduceFace() {
    }

    public ReduceFace(int reduceLevel, int reducePercent, FaceType faceType) {
        this.reduceLevel = reduceLevel;
        this.reducePercent = reducePercent;
        this.faceType = faceType;
    }

    public int getReduceLevel() {
        return reduceLevel;
    }

    public void setReduceLevel(int reduceLevel) {
        this.reduceLevel = reduceLevel;
    }

    public int getReducePercent() {
        return reducePercent;
    }

    public void setReducePercent(int reducePercent) {
        this.reducePercent = reducePercent;
    }

    public FaceType getFaceType() {
        return faceType;
    }

    public void setFaceType(FaceType faceType) {
        this.faceType = faceType;
    }

    public Integer getFaceNum() {
        return faceNum;
    }

    public void setFaceNum(Integer faceNum) {
        this.faceNum = faceNum;
    }

    public Integer getFaceTab() {
        return faceTab;
    }

    public void setFaceTab(Integer faceTab) {
        this.faceTab = faceTab;
    }

    /**
     * Converts ReduceFace to Map for JSON serialization
     */
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("reduce_level", reduceLevel);
        m.put("reduce_percent", reducePercent);
        m.put("face_type", faceType.getValue());
        if (faceNum != null) {
            m.put("face_num", faceNum);
        }
        if (faceTab != null) {
            m.put("face_tab", faceTab);
        }
        return m;
    }
}
