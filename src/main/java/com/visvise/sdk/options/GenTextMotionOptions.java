package com.visvise.sdk.options;

import com.visvise.sdk.enums.ModelFormat;
import com.visvise.sdk.models.MotionSegment;

import java.util.List;

/**
 * GenTextMotionOptions defines optional parameters for GenTextMotion
 */
public class GenTextMotionOptions {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_text_motion";
    /** optional, algorithm model name */
    private String algorithmModel;
    /** optional, output format (default fbx) */
    private ModelFormat modelFormat = ModelFormat.FBX;
    /** optional, multi-segment timeline (takes priority over prompt) */
    private List<MotionSegment> segments;
    /** optional, enable rewrite option (default true) */
    private Boolean enableRewrite;
    /** optional, animation duration in seconds (single-segment prompt mode only) */
    private Integer duration;
    /** optional, enable loop playback */
    private Boolean enableLoop;
    /** optional, loop frames (1~20) */
    private Integer loopFrames;

    public GenTextMotionOptions() {
    }

    public String getName() {
        return name;
    }

    public GenTextMotionOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenTextMotionOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public ModelFormat getOutputModelFormat() {
        return modelFormat;
    }

    public GenTextMotionOptions setOutputModelFormat(ModelFormat modelFormat) {
        this.modelFormat = modelFormat;
        return this;
    }

    public List<MotionSegment> getSegments() {
        return segments;
    }

    public GenTextMotionOptions setSegments(List<MotionSegment> segments) {
        this.segments = segments;
        return this;
    }

    public Boolean getEnableRewrite() {
        return enableRewrite;
    }

    public GenTextMotionOptions setEnableRewrite(Boolean enableRewrite) {
        this.enableRewrite = enableRewrite;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public GenTextMotionOptions setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public Boolean getEnableLoop() {
        return enableLoop;
    }

    public GenTextMotionOptions setEnableLoop(Boolean enableLoop) {
        this.enableLoop = enableLoop;
        return this;
    }

    public Integer getLoopFrames() {
        return loopFrames;
    }

    public GenTextMotionOptions setLoopFrames(Integer loopFrames) {
        this.loopFrames = loopFrames;
        return this;
    }

    public static GenTextMotionOptions create() {
        return new GenTextMotionOptions();
    }
}
