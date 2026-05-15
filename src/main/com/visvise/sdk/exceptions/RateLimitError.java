package com.visvise.sdk.exceptions;

/**
 * RateLimitError indicates too many requests (120040)
 */
public class RateLimitError extends WeaverError {
    public RateLimitError(int code, String message, String reqId) {
        super(code, message, reqId);
    }
}
