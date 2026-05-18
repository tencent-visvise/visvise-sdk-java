package com.visvise.sdk.exceptions;

/**
 * ModelGenerationError indicates model generation failed (async task status=4)
 */
public class ModelGenerationError extends WeaverError {
    private final String modelId;

    public ModelGenerationError(String message, int code, String modelId, String reqId) {
        super(code, message, reqId);
        this.modelId = modelId;
    }

    public String getModelId() {
        return modelId;
    }

    @Override
    public String toString() {
        if (modelId != null && !modelId.isEmpty()) {
            return String.format("[%d] %s (model_id=%s)", getCode(), getMessage(), modelId);
        }
        return String.format("[%d] %s", getCode(), getMessage());
    }
}
