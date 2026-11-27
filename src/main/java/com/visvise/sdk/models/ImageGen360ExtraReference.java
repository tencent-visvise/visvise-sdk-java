package com.visvise.sdk.models;

/**
 * ImageGen360ExtraReference 图生360 - 参考图。
 * path 支持本地路径 / VISVISE 平台 COS URL / 二进制内容，gen360 会自动上传到当前账号的 COS 目录。
 */
public class ImageGen360ExtraReference {
    /** 参考图，gen360 自动上传 */
    private Object path;
    /** 参考图说明 */
    private String prompt;

    public ImageGen360ExtraReference() {
    }

    public ImageGen360ExtraReference(Object path) {
        this.path = path;
    }

    public Object getPath() {
        return path;
    }

    public ImageGen360ExtraReference setPath(Object path) {
        this.path = path;
        return this;
    }

    public String getPrompt() {
        return prompt;
    }

    public ImageGen360ExtraReference setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }
}
