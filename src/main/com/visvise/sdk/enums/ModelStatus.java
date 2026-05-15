package com.visvise.sdk.enums;

/**
 * ModelStatus represents the model asset status code
 */
public enum ModelStatus {
    INVALID(0, "Invalid"),
    PENDING(1, "Waiting for generation"),
    RUNNING(2, "Generating"),
    SUCCESS(3, "Generation succeeded"),
    FAILED(4, "Generation failed");

    private final int value;
    private final String description;

    ModelStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static ModelStatus fromValue(int value) {
        for (ModelStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown ModelStatus value: " + value);
    }
}
