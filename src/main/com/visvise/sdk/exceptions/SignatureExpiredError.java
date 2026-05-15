package com.visvise.sdk.exceptions;

/**
 * SignatureExpiredError indicates signature expired, timestamp deviation too large (412)
 */
public class SignatureExpiredError extends WeaverError {
    public SignatureExpiredError(int code, String message, String reqId) {
        super(code, message, reqId);
    }
}
