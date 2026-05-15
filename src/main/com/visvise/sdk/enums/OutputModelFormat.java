package com.visvise.sdk.enums;

/**
 * OutputModelFormat represents the output model format
 */
public enum OutputModelFormat {
    FBX("fbx", "FBX format"),
    OBJ("obj", "OBJ format"),
    GLB("glb", "GLB format");

    private final String value;
    private final String description;

    OutputModelFormat(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static OutputModelFormat fromValue(String value) {
        for (OutputModelFormat format : values()) {
            if (format.value.equals(value)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unknown OutputModelFormat value: " + value);
    }
}
