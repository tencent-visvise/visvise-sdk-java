package com.visvise.sdk.exceptions;

/**
 * ServerTimeoutError indicates server processing timeout (120032)
 */
public class ServerTimeoutError extends WeaverError {
    public ServerTimeoutError(int code, String message, String reqId) {
        super(code, message, reqId);
    }
}
