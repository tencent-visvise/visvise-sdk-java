package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * FramingAIOutput represents the Framing AI output result
 */
public class FramingAIOutput {
    @SerializedName("text2_motion_result")
    private List<Text2Motion> text2MotionResult;

    public List<Text2Motion> getText2MotionResult() {
        return text2MotionResult;
    }

    public void setText2MotionResult(List<Text2Motion> text2MotionResult) {
        this.text2MotionResult = text2MotionResult;
    }
}
