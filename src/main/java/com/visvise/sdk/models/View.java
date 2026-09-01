package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

/**
 * View represents the multi-view structure (9 fields, aligned with proto {@code View})
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

    @SerializedName("top_view")
    private String topView;

    @SerializedName("bottom_view")
    private String bottomView;

    @SerializedName("front_view")
    private String frontView;

    @SerializedName("front_left_view")
    private String frontLeftView;

    @SerializedName("front_right_view")
    private String frontRightView;

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

    public String getTopView() {
        return topView;
    }

    public void setTopView(String topView) {
        this.topView = topView;
    }

    public String getBottomView() {
        return bottomView;
    }

    public void setBottomView(String bottomView) {
        this.bottomView = bottomView;
    }

    public String getFrontView() {
        return frontView;
    }

    public void setFrontView(String frontView) {
        this.frontView = frontView;
    }

    public String getFrontLeftView() {
        return frontLeftView;
    }

    public void setFrontLeftView(String frontLeftView) {
        this.frontLeftView = frontLeftView;
    }

    public String getFrontRightView() {
        return frontRightView;
    }

    public void setFrontRightView(String frontRightView) {
        this.frontRightView = frontRightView;
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

    public View withTopView(String topView) {
        this.topView = topView;
        return this;
    }

    public View withBottomView(String bottomView) {
        this.bottomView = bottomView;
        return this;
    }

    public View withFrontView(String frontView) {
        this.frontView = frontView;
        return this;
    }

    public View withFrontLeftView(String frontLeftView) {
        this.frontLeftView = frontLeftView;
        return this;
    }

    public View withFrontRightView(String frontRightView) {
        this.frontRightView = frontRightView;
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
        if (topView != null && !topView.isEmpty()) {
            m.put("top_view", topView);
        }
        if (bottomView != null && !bottomView.isEmpty()) {
            m.put("bottom_view", bottomView);
        }
        if (frontView != null && !frontView.isEmpty()) {
            m.put("front_view", frontView);
        }
        if (frontLeftView != null && !frontLeftView.isEmpty()) {
            m.put("front_left_view", frontLeftView);
        }
        if (frontRightView != null && !frontRightView.isEmpty()) {
            m.put("front_right_view", frontRightView);
        }
        return m;
    }
}
