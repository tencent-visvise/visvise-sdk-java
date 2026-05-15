package com.visvise.sdk.models;

import com.google.gson.annotations.SerializedName;
import com.visvise.sdk.enums.ModelStatus;

/**
 * ModelInfo represents the model asset information
 */
public class ModelInfo {
    @SerializedName("model_id")
    private String modelId;

    private String name;

    private int status;

    @SerializedName("node_type")
    private int nodeType;

    @SerializedName("create_ts")
    private long createTs;

    @SerializedName("create_user")
    private String createUser;

    @SerializedName("preview_img")
    private String previewImg;

    @SerializedName("output_model")
    private String outputModel;

    @SerializedName("input_model")
    private String inputModel;

    @SerializedName("input_video")
    private String inputVideo;

    @SerializedName("time_cost")
    private int timeCost;

    @SerializedName("remaining_time")
    private int remainingTime;

    @SerializedName("wait_time")
    private int waitTime;

    @SerializedName("failed_reason")
    private FailedReason failedReason;

    @SerializedName("lod_output")
    private LODOutput lodOutput;

    @SerializedName("image_gen_360_output")
    private ImageGen360Output imageGen360Output;

    @SerializedName("framing_ai_output")
    private FramingAIOutput framingAIOutput;

    @SerializedName("algorithm_model")
    private String algorithmModel;

    // Getters and setters
    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public long getCreateTs() {
        return createTs;
    }

    public void setCreateTs(long createTs) {
        this.createTs = createTs;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getPreviewImg() {
        return previewImg;
    }

    public void setPreviewImg(String previewImg) {
        this.previewImg = previewImg;
    }

    public String getOutputModel() {
        return outputModel;
    }

    public void setOutputModel(String outputModel) {
        this.outputModel = outputModel;
    }

    public String getInputModel() {
        return inputModel;
    }

    public void setInputModel(String inputModel) {
        this.inputModel = inputModel;
    }

    public String getInputVideo() {
        return inputVideo;
    }

    public void setInputVideo(String inputVideo) {
        this.inputVideo = inputVideo;
    }

    public int getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(int timeCost) {
        this.timeCost = timeCost;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public FailedReason getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(FailedReason failedReason) {
        this.failedReason = failedReason;
    }

    public LODOutput getLodOutput() {
        return lodOutput;
    }

    public void setLodOutput(LODOutput lodOutput) {
        this.lodOutput = lodOutput;
    }

    public ImageGen360Output getImageGen360Output() {
        return imageGen360Output;
    }

    public void setImageGen360Output(ImageGen360Output imageGen360Output) {
        this.imageGen360Output = imageGen360Output;
    }

    public FramingAIOutput getFramingAIOutput() {
        return framingAIOutput;
    }

    public void setFramingAIOutput(FramingAIOutput framingAIOutput) {
        this.framingAIOutput = framingAIOutput;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public void setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
    }

    /**
     * Returns true if the model generation succeeded
     */
    public boolean isSuccess() {
        return status == ModelStatus.SUCCESS.getValue();
    }

    /**
     * Returns true if the model generation failed
     */
    public boolean isFailed() {
        return status == ModelStatus.FAILED.getValue();
    }

    /**
     * Returns true if the model is pending or running
     */
    public boolean isPending() {
        return status == ModelStatus.PENDING.getValue() || status == ModelStatus.RUNNING.getValue();
    }
}
