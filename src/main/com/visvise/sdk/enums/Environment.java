package com.visvise.sdk.enums;

/**
 * Environment represents the API environment
 */
public enum Environment {
    PROD("https://ws.visvise.com.cn", "Production environment"),
    TEST("https://qa-ws.visvise.com.cn", "Test environment"),
    DEV("https://dev-ws.visvise.com.cn", "Development environment");

    private final String value;
    private final String description;

    Environment(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static Environment fromValue(String value) {
        for (Environment env : values()) {
            if (env.value.equals(value)) {
                return env;
            }
        }
        throw new IllegalArgumentException("Unknown Environment value: " + value);
    }
}
