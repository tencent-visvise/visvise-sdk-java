package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

/**
 * CondPartAutofitResult represents the cond_part_autofit (裸模拆分) API response.
 */
public class CondPartAutofitResult {
    /** 输出 obj 的 COS 路径（带临时签名，24h 有效） */
    @SerializedName("output_obj_path")
    private String outputObjPath;

    /** 最终使用的底色（英文色名）；算法未返回时为空字符串 */
    @SerializedName("background_color")
    private String backgroundColor;

    public String getOutputObjPath() {
        return outputObjPath;
    }

    public void setOutputObjPath(String outputObjPath) {
        this.outputObjPath = outputObjPath;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
