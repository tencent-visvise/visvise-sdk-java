package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * ImageCutoutResult represents the image_cutout API response
 */
public class ImageCutoutResult {
    /**
     * 抠图结果图片的 COS URL 列表（带临时签名，24h 有效），按输入图片顺序返回
     */
    @SerializedName("image_list")
    private List<String> imageList;

    /**
     * 最终使用的底色（英文色名）；算法未返回时沿用请求传入的值
     */
    @SerializedName("background_color")
    private String backgroundColor;

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
