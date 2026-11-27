package com.visvise.sdk.models;

import java.util.Map;

/**
 * Gen3DModelRequest 生成3D模型资产（gen_3d_model）的请求参数
 */
public class Gen3DModelRequest {
    /** 模型资产名称（必选） */
    private String name;
    /** 节点类型（必选） */
    private int nodeType;
    /** 生成参数（必选），按节点类型填对应的子参数 */
    private Map<String, Object> params;
    /** 父模型资产 ID（可选），两段式重拓扑时指向 node_type=19 资产 */
    private String parentModelId;
    /** 输入视图（可选） */
    private View inputView;
    /** 输入模型 COS 地址（zip，可选） */
    private String inputModel;
    /** 输入模型格式 fbx/obj/glb（可选） */
    private String inputModelFormat;
    /** 输入视频 COS 地址（可选） */
    private String inputVideo;

    public String getName() {
        return name;
    }

    public Gen3DModelRequest setName(String name) {
        this.name = name;
        return this;
    }

    public int getNodeType() {
        return nodeType;
    }

    public Gen3DModelRequest setNodeType(int nodeType) {
        this.nodeType = nodeType;
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public Gen3DModelRequest setParams(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    public String getParentModelId() {
        return parentModelId;
    }

    public Gen3DModelRequest setParentModelId(String parentModelId) {
        this.parentModelId = parentModelId;
        return this;
    }

    public View getInputView() {
        return inputView;
    }

    public Gen3DModelRequest setInputView(View inputView) {
        this.inputView = inputView;
        return this;
    }

    public String getInputModel() {
        return inputModel;
    }

    public Gen3DModelRequest setInputModel(String inputModel) {
        this.inputModel = inputModel;
        return this;
    }

    public String getInputModelFormat() {
        return inputModelFormat;
    }

    public Gen3DModelRequest setInputModelFormat(String inputModelFormat) {
        this.inputModelFormat = inputModelFormat;
        return this;
    }

    public String getInputVideo() {
        return inputVideo;
    }

    public Gen3DModelRequest setInputVideo(String inputVideo) {
        this.inputVideo = inputVideo;
        return this;
    }
}
