package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * FeedbackItem represents a single feedback entry for a generated result.
 */
public class FeedbackItem {
    @SerializedName("result_index")
    private int resultIndex;

    @SerializedName("feedback_type")
    private int feedbackType;

    private List<String> tags;

    private String content;

    public int getResultIndex() {
        return resultIndex;
    }

    public void setResultIndex(int resultIndex) {
        this.resultIndex = resultIndex;
    }

    public int getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(int feedbackType) {
        this.feedbackType = feedbackType;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
