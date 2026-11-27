package com.visvise.sdk.models;

/**
 * ImageGen360RevisionViews 图生360 续改参数中的视图。
 * 各视图支持本地路径 / VISVISE 平台 COS URL / 二进制内容，gen360 会自动上传到当前账号的 COS 目录。
 */
public class ImageGen360RevisionViews {
    private Object mainView;
    private Object backView;
    private Object leftView;
    private Object rightView;
    private Object topView;
    private Object bottomView;
    private Object frontView;
    private Object frontLeftView;
    private Object frontRightView;

    public Object getMainView() {
        return mainView;
    }

    public ImageGen360RevisionViews setMainView(Object mainView) {
        this.mainView = mainView;
        return this;
    }

    public Object getBackView() {
        return backView;
    }

    public ImageGen360RevisionViews setBackView(Object backView) {
        this.backView = backView;
        return this;
    }

    public Object getLeftView() {
        return leftView;
    }

    public ImageGen360RevisionViews setLeftView(Object leftView) {
        this.leftView = leftView;
        return this;
    }

    public Object getRightView() {
        return rightView;
    }

    public ImageGen360RevisionViews setRightView(Object rightView) {
        this.rightView = rightView;
        return this;
    }

    public Object getTopView() {
        return topView;
    }

    public ImageGen360RevisionViews setTopView(Object topView) {
        this.topView = topView;
        return this;
    }

    public Object getBottomView() {
        return bottomView;
    }

    public ImageGen360RevisionViews setBottomView(Object bottomView) {
        this.bottomView = bottomView;
        return this;
    }

    public Object getFrontView() {
        return frontView;
    }

    public ImageGen360RevisionViews setFrontView(Object frontView) {
        this.frontView = frontView;
        return this;
    }

    public Object getFrontLeftView() {
        return frontLeftView;
    }

    public ImageGen360RevisionViews setFrontLeftView(Object frontLeftView) {
        this.frontLeftView = frontLeftView;
        return this;
    }

    public Object getFrontRightView() {
        return frontRightView;
    }

    public ImageGen360RevisionViews setFrontRightView(Object frontRightView) {
        this.frontRightView = frontRightView;
        return this;
    }
}
