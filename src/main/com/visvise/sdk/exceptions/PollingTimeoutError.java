package com.visvise.sdk.exceptions;

/**
 * PollingTimeoutError indicates waiting for model completion timeout
 */
public class PollingTimeoutError extends WeaverError {
    private final String modelId;
    private final int timeout;

    public PollingTimeoutError(String modelId, int timeout) {
        super(-2, String.format("Timeout waiting for model %s", modelId));
        this.modelId = modelId;
        this.timeout = timeout;
    }

    public String getModelId() {
        return modelId;
    }

    public int getTimeout() {
        return timeout;
    }

    @Override
    public String toString() {
        return String.format("Timeout waiting for model %s (timeout=%ds)", modelId, timeout);
    }
}
