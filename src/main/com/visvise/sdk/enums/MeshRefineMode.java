package com.visvise.sdk.enums;

/**
 * MeshRefineMode represents the mesh refine mode
 */
public enum MeshRefineMode {
    OPTIMIZE(1, "Optimize"),
    DENSIFY(2, "Densify");

    private final int value;
    private final String description;

    MeshRefineMode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static MeshRefineMode fromValue(int value) {
        for (MeshRefineMode mode : values()) {
            if (mode.value == value) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Unknown MeshRefineMode value: " + value);
    }
}
