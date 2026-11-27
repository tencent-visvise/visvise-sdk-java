package com.visvise.sdk.enums;

/**
 * ImageCutoutMode represents the image cutout mode (image_cutout)
 */
public enum ImageCutoutMode {
    DIRECT(1, "直接抠图（输出透明背景）"),
    SOLID_COLOR(2, "纯色底抠图"),
    REPLACE(3, "换底并抠图");

    private final int value;
    private final String description;

    ImageCutoutMode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static ImageCutoutMode fromValue(int value) {
        for (ImageCutoutMode mode : values()) {
            if (mode.value == value) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Unknown ImageCutoutMode value: " + value);
    }
}
