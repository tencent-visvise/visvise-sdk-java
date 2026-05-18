package com.visvise.sdk.enums;

/**
 * AnimationSubType represents the animation sub-type
 */
public enum AnimationSubType {
    VIDEO(1, "Video to animation"),
    TEXT(2, "Text to animation");

    private final int value;
    private final String description;

    AnimationSubType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static AnimationSubType fromValue(int value) {
        for (AnimationSubType type_ : values()) {
            if (type_.value == value) {
                return type_;
            }
        }
        throw new IllegalArgumentException("Unknown AnimationSubType value: " + value);
    }
}
