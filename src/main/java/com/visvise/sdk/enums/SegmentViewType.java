package com.visvise.sdk.enums;

/**
 * SegmentViewType represents the view type for 2D split re-edit operations.
 */
public enum SegmentViewType {
    MAIN(0, "Main view (front, default)"),
    LEFT(1, "Left view"),
    RIGHT(2, "Right view"),
    BACK(3, "Back view");

    private final int value;
    private final String description;

    SegmentViewType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static SegmentViewType fromValue(int value) {
        for (SegmentViewType type_ : values()) {
            if (type_.value == value) {
                return type_;
            }
        }
        throw new IllegalArgumentException("Unknown SegmentViewType value: " + value);
    }
}
