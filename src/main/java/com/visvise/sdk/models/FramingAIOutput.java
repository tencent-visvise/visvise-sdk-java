package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * FramingAIOutput represents the Framing AI output result
 */
public class FramingAIOutput {
    @SerializedName("text2_motion_result")
    private List<Text2Motion> text2MotionResult;

    @SerializedName("rewrite_prompts")
    private List<String> rewritePrompts;

    @SerializedName("rewrite_applied")
    private boolean rewriteApplied;

    public List<Text2Motion> getText2MotionResult() {
        return text2MotionResult;
    }

    public void setText2MotionResult(List<Text2Motion> text2MotionResult) {
        this.text2MotionResult = text2MotionResult;
    }

    public List<String> getRewritePrompts() {
        return rewritePrompts;
    }

    public void setRewritePrompts(List<String> rewritePrompts) {
        this.rewritePrompts = rewritePrompts;
    }

    public boolean getRewriteApplied() {
        return rewriteApplied;
    }

    public void setRewriteApplied(boolean rewriteApplied) {
        this.rewriteApplied = rewriteApplied;
    }
}
