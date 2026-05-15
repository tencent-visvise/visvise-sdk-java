package com.visvise.sdk.exceptions;

/**
 * InvalidParamsError indicates invalid request parameters (120008)
 */
public class InvalidParamsError extends WeaverError {
    public InvalidParamsError(int code, String message, String reqId) {
        super(code, message, reqId);
    }
}
