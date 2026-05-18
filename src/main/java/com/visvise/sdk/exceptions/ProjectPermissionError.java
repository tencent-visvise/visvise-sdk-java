package com.visvise.sdk.exceptions;

/**
 * ProjectPermissionError indicates project permission not authorized (120027)
 */
public class ProjectPermissionError extends WeaverError {
    public ProjectPermissionError(int code, String message, String reqId) {
        super(code, message, reqId);
    }
}
