package com.visvise.sdk.enums;

/**
 * FaceType represents the face type enum
 */
public enum FaceType {
    TRIANGLE(1, "Triangle"),
    QUAD(2, "Quad");

    private final int value;
    private final String description;

    FaceType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static FaceType fromValue(int value) {
        for (FaceType type_ : values()) {
            if (type_.value == value) {
                return type_;
            }
        }
        throw new IllegalArgumentException("Unknown FaceType value: " + value);
    }
}
