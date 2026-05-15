package com.visvise.sdk.models;

/**
 * SSEResult represents an SSE event frame
 */
public class SSEResult {
    private String event;
    private Object data;

    public SSEResult() {
    }

    public SSEResult(String event, Object data) {
        this.event = event;
        this.data = data;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
