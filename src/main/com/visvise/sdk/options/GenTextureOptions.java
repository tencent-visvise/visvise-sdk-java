package com.visvise.sdk.options;

import com.visvise.sdk.models.View;

/**
 * GenTextureOptions defines optional parameters for GenTexture
 */
public class GenTextureOptions {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_texture";
    /** optional, algorithm model name */
    private String algorithmModel;
    /** optional, reference view (required with prompt) */
    private View inputView;
    /** optional, resolution (e.g. 1024, 2048) */
    private Integer resolution;
    /** optional, also unwrap UV */
    private Boolean unwarpUV;
    /** optional, text prompt for texture */
    private String prompt;

    public GenTextureOptions() {
    }

    public String getName() {
        return name;
    }

    public GenTextureOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenTextureOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public View getInputView() {
        return inputView;
    }

    public GenTextureOptions setInputView(View inputView) {
        this.inputView = inputView;
        return this;
    }

    public Integer getResolution() {
        return resolution;
    }

    public GenTextureOptions setResolution(Integer resolution) {
        this.resolution = resolution;
        return this;
    }

    public Boolean getUnwarpUV() {
        return unwarpUV;
    }

    public GenTextureOptions setUnwarpUV(Boolean unwarpUV) {
        this.unwarpUV = unwarpUV;
        return this;
    }

    public String getPrompt() {
        return prompt;
    }

    public GenTextureOptions setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public static GenTextureOptions create() {
        return new GenTextureOptions();
    }
}
