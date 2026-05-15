package com.visvise.sdk.enums;

/**
 * SegmentSplitType represents the 2D segmentation split type
 */
public enum SegmentSplitType {
    FRONT_VIEW(1, "Front view (default)"),
    FOUR_VIEW(2, "Four view");

    private final int value;
    private final String description;

    SegmentSplitType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static SegmentSplitType fromValue(int value) {
        for (SegmentSplitType type_ : values()) {
            if (type_.value == value) {
                return type_;
            }
        }
        throw new IllegalArgumentException("Unknown SegmentSplitType value: " + value);
    }
}
