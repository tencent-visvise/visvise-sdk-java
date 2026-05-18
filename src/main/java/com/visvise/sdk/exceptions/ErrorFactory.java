package com.visvise.sdk.exceptions;

/**
 * ErrorFactory creates and returns the appropriate error based on the error code
 */
public class ErrorFactory {

    public static WeaverError raiseForCode(int code, String msg, String reqId) {
        WeaverError weaverErr = new WeaverError(code, msg, reqId);

        switch (code) {
            case 410:
                return new SignatureError(code, msg, reqId);
            case 411:
                return new SignatureExpiredError(code, msg, reqId);
            case 120008:
                return new InvalidParamsError(code, msg, reqId);
            case 120017:
                return new UserNotFoundError(code, msg, reqId);
            case 120018:
                return new PermissionDeniedError(code, msg, reqId);
            case 120020:
                return new QuotaExceededError(code, msg, reqId);
            case 120027:
                return new ProjectPermissionError(code, msg, reqId);
            case 120028:
                return new ServerNetworkError(code, msg, reqId);
            case 120032:
                return new ServerTimeoutError(code, msg, reqId);
            case 120040:
                return new RateLimitError(code, msg, reqId);
            default:
                return weaverErr;
        }
    }

    public static NetworkError newNetworkError(String message) {
        return new NetworkError(message);
    }

    public static ModelGenerationError newModelGenerationError(String message, int code, String modelId, String reqId) {
        return new ModelGenerationError(message, code, modelId, reqId);
    }

    public static PollingTimeoutError newPollingTimeoutError(String modelId, int timeout) {
        return new PollingTimeoutError(modelId, timeout);
    }

    private ErrorFactory() {
        // Prevent instantiation
    }
}
