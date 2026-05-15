package com.visvise.sdk.exceptions;

/**
 * WeaverError is the base error type for all SDK errors
 */
public class WeaverError extends Exception {
    private final int code;
    private final String reqId;
    private final String message;

    public WeaverError(int code, String message, String reqId) {
        super(buildMessage(code, message, reqId));
        this.code = code;
        this.message = message;
        this.reqId = reqId;
    }

    public WeaverError(int code, String message) {
        this(code, message, "");
    }

    private static String buildMessage(int code, String message, String reqId) {
        if (reqId != null && !reqId.isEmpty()) {
            return String.format("[%d] %s (req_id=%s)", code, message, reqId);
        }
        return String.format("[%d] %s", code, message);
    }

    public int getCode() {
        return code;
    }

    public String getReqId() {
        return reqId;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
