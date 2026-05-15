package com.visvise.sdk.options;

import com.visvise.sdk.enums.Environment;

/**
 * ClientOptions defines optional parameters for creating a Client.
 * Use newClientOptions() to create with default values, then chain setters.
 */
public class ClientOptions {
    private Environment env = Environment.PROD;
    private int timeout = 30;
    private boolean debug = false;

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

    public boolean isDebug() {
        return debug;
    }

    public ClientOptions setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public static ClientOptions create() {
        return new ClientOptions();
    }
}
