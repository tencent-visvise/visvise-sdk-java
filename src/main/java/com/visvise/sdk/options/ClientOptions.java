package com.visvise.sdk.options;

import com.visvise.sdk.enums.Environment;

/**
 * ClientOptions defines optional parameters for creating a Client.
 * Use ClientOptions.create() to create with default values, then chain setters.
 *
 * <p>Example:
 * <pre>
 * ClientOptions opts = ClientOptions.create()
 *     .setEnv(Environment.DEV)
 *     .setDebug(true);
 * VisviseClient client = new VisviseClient("app_id", "secret_key", "uid", opts);
 * </pre>
 */
public class ClientOptions {
    /** optional, environment (default PROD) */
    private Environment env = Environment.PROD;
    /** optional, HTTP timeout in seconds (default 30) */
    private int timeout = 30;

    public ClientOptions() {
    }

    public Environment getEnv() {
        return env;
    }

    public ClientOptions setEnv(Environment env) {
        this.env = env;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public ClientOptions setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public static ClientOptions create() {
        return new ClientOptions();
    }
}
