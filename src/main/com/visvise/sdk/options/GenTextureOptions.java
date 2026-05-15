package com.visvise.sdk.options;

import com.visvise.sdk.models.View;

/**
 * GenTextureOptions defines optional parameters for GenTexture
 */
public class GenTextureOptions {
    private String name = "gen_texture";
    private String algorithmModel;
    private View inputView;
    private Integer resolution;
    private Boolean unwarpUV;
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
