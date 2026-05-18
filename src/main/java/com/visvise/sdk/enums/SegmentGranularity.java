package com.visvise.sdk.enums;

/**
 * SegmentGranularity represents the 2D segmentation granularity
 */
public enum SegmentGranularity {
    COARSE(1, "Coarse (x50%)"),
    MEDIUM(2, "Medium (x70%, default)"),
    FINE(3, "Fine (x100%)");

    private final int value;
    private final String description;

    SegmentGranularity(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static SegmentGranularity fromValue(int value) {
        for (SegmentGranularity granularity : values()) {
            if (granularity.value == value) {
                return granularity;
            }
        }
        throw new IllegalArgumentException("Unknown SegmentGranularity value: " + value);
    }
}
