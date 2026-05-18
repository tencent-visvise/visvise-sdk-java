package com.visvise.sdk.enums;

/**
 * @version V1.0
 * @author: huangci
 * @since 2026/5/18
 **/
public enum ImageGen360Style {
    GRAY_MODEL("灰模", "gray model"),
    PHOTOREAL("超写实", "Quad"),
    Q_TOON("Q版卡通", "Q-Toon"),
    PIXEL("像素风格", "Pixel");

    private final String value;
    private final String description;

    ImageGen360Style(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static ImageGen360Style fromValue(String value) {
        for (ImageGen360Style type_ : values()) {
            if (type_.value == value) {
                return type_;
            }
        }
        throw new IllegalArgumentException("Unknown FaceType value: " + value);
    }
}
