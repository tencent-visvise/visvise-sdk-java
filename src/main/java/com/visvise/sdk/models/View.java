package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

/**
 * View represents the multi-view structure
 */
public class View {
    @SerializedName("main_view")
    private String mainView;

    @SerializedName("back_view")
    private String backView;

    @SerializedName("left_view")
    private String leftView;

    @SerializedName("right_view")
    private String rightView;

    public View() {
    }

    public View(String mainView) {
        this.mainView = mainView;
    }

    public String getMainView() {
        return mainView;
    }

    public void setMainView(String mainView) {
        this.mainView = mainView;
    }

    public String getBackView() {
        return backView;
    }

    public void setBackView(String backView) {
        this.backView = backView;
    }

    public String getLeftView() {
        return leftView;
    }

    public void setLeftView(String leftView) {
        this.leftView = leftView;
    }

    public String getRightView() {
        return rightView;
    }

    public void setRightView(String rightView) {
        this.rightView = rightView;
    }

    public View withMainView(String mainView) {
        this.mainView = mainView;
        return this;
    }

    public View withBackView(String backView) {
        this.backView = backView;
        return this;
    }

    public View withLeftView(String leftView) {
        this.leftView = leftView;
        return this;
    }

    public View withRightView(String rightView) {
        this.rightView = rightView;
        return this;
    }

    /**
     * Converts View to Map for JSON serialization
     */
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        if (mainView != null && !mainView.isEmpty()) {
            m.put("main_view", mainView);
        }
        if (backView != null && !backView.isEmpty()) {
            m.put("back_view", backView);
        }
        if (leftView != null && !leftView.isEmpty()) {
            m.put("left_view", leftView);
        }
        if (rightView != null && !rightView.isEmpty()) {
            m.put("right_view", rightView);
        }
        return m;
    }
}
