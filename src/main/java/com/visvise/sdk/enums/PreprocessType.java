package com.visvise.sdk.enums;

/**
 * PreprocessType represents the 2D preprocess type.
 */
public enum PreprocessType {
    STYLIZED(1, "Stylized"),
    PATTERNED(2, "Pattern removal");

    private final int value;
    private final String description;

    PreprocessType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static PreprocessType fromValue(int value) {
        for (PreprocessType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown PreprocessType value: " + value);
    }
}
