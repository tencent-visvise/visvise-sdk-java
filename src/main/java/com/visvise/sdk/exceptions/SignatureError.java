package com.visvise.sdk.exceptions;

/**
 * SignatureError indicates signature mismatch (411)
 */
public class SignatureError extends WeaverError {
    public SignatureError(int code, String message, String reqId) {
        super(code, message, reqId);
    }
}
