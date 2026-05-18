package com.visvise.sdk.enums;

/**
 * DetailLevel represents the detail level enum for re-topology
 */
public enum DetailLevel {
    LOW(1, "Low"),
    MEDIUM(2, "Medium"),
    HIGH(3, "High");

    private final int value;
    private final String description;

    DetailLevel(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static DetailLevel fromValue(int value) {
        for (DetailLevel level : values()) {
            if (level.value == value) {
                return level;
            }
        }
        throw new IllegalArgumentException("Unknown DetailLevel value: " + value);
    }
}
