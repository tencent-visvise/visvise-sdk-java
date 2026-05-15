package com.visvise.sdk.exceptions;

/**
 * NetworkError indicates network failure (connection timeout, DNS resolution failed, etc.)
 */
public class NetworkError extends WeaverError {
    public NetworkError(String message) {
        super(-1, message);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s", getCode(), getMessage());
    }
}
