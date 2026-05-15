package com.visvise.sdk.exceptions;

/**
 * UserNotFoundError indicates user not found (120017)
 */
public class UserNotFoundError extends WeaverError {
    public UserNotFoundError(int code, String message, String reqId) {
        super(code, message, reqId);
    }
}
