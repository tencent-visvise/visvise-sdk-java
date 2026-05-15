package com.visvise.sdk.options;

import com.visvise.sdk.enums.SegmentGranularity;
import com.visvise.sdk.enums.SegmentSplitType;
import com.visvise.sdk.models.View;

/**
 * GenSegment2DOptions defines optional parameters for GenSegment2D
 */
public class GenSegment2DOptions {
    private String name = "gen_segment_2d";
    private String algorithmModel;
    private View inputView;
    private SegmentSplitType splitType;
    private SegmentGranularity granularity;
    private String prompt;
    private ThinkingCallback onThinking;
    private int readTimeout = 0;

    public GenSegment2DOptions() {
    }

    public String getName() {
        return name;
    }

    public GenSegment2DOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenSegment2DOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public View getInputView() {
        return inputView;
    }

    public GenSegment2DOptions setInputView(View inputView) {
        this.inputView = inputView;
        return this;
    }

    public SegmentSplitType getSplitType() {
        return splitType;
    }

    public GenSegment2DOptions setSplitType(SegmentSplitType splitType) {
        this.splitType = splitType;
        return this;
    }

    public SegmentGranularity getGranularity() {
        return granularity;
    }

    public GenSegment2DOptions setGranularity(SegmentGranularity granularity) {
        this.granularity = granularity;
        return this;
    }

    public String getPrompt() {
        return prompt;
    }

    public GenSegment2DOptions setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public ThinkingCallback getOnThinking() {
        return onThinking;
    }

    public GenSegment2DOptions setOnThinking(ThinkingCallback onThinking) {
        this.onThinking = onThinking;
        return this;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public GenSegment2DOptions setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public static GenSegment2DOptions create() {
        return new GenSegment2DOptions();
    }
}
