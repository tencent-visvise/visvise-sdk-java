package com.visvise.sdk.models;

/**
 * ImageGen360Revision 图生360 - 续改参数。
 * caption 为上一次返回的 caption 全文（原样回传），prompt 为本次修改指令。
 */
public class ImageGen360Revision {
    private String caption;
    private String prompt;
    private ImageGen360RevisionViews views;

    public String getCaption() {
        return caption;
    }

    public ImageGen360Revision setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public String getPrompt() {
        return prompt;
    }

    public ImageGen360Revision setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public ImageGen360RevisionViews getViews() {
        return views;
    }

    public ImageGen360Revision setViews(ImageGen360RevisionViews views) {
        this.views = views;
        return this;
    }
}
