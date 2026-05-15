package com.visvise.sdk.exceptions;

/**
 * QuotaExceededError indicates daily generation quota exceeded (120020)
 */
public class QuotaExceededError extends WeaverError {
    public QuotaExceededError(int code, String message, String reqId) {
        super(code, message, reqId);
    }
}
