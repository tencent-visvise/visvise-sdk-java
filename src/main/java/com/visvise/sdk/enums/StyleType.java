package com.visvise.sdk.enums;

/**
 * StyleType represents the source image style type.
 */
public enum StyleType {
    GRAYSCALE(1, "Grayscale"),
    PIXEL(2, "Pixel"),
    REALISTIC(3, "Realistic"),
    CARTOON(4, "Cartoon figurine");

    private final int value;
    private final String description;

    StyleType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static StyleType fromValue(int value) {
        for (StyleType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown StyleType value: " + value);
    }
}
