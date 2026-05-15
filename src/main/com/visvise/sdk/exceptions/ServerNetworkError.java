package com.visvise.sdk.exceptions;

/**
 * ServerNetworkError indicates server network error (120028)
 */
public class ServerNetworkError extends WeaverError {
    public ServerNetworkError(int code, String message, String reqId) {
        super(code, message, reqId);
    }
}
