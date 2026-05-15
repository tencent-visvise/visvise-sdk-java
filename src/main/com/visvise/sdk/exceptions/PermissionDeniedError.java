package com.visvise.sdk.exceptions;

/**
 * PermissionDeniedError indicates user permission denied (120018)
 */
public class PermissionDeniedError extends WeaverError {
    public PermissionDeniedError(int code, String message, String reqId) {
        super(code, message, reqId);
    }
}
