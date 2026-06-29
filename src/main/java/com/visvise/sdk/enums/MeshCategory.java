package com.visvise.sdk.enums;

/**
 * MeshCategory represents the mesh category for rigging
 */
public enum MeshCategory {
    HUMANOID("humanoid", "Humanoid (default)"),
    TETRAPOD("tetrapod", "Tetrapod (four-legged)"),
    OTHER("other", "Other");

    private final String value;
    private final String description;

    MeshCategory(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static MeshCategory fromValue(String value) {
        for (MeshCategory cat : values()) {
            if (cat.value.equals(value)) {
                return cat;
            }
        }
        throw new IllegalArgumentException("Unknown MeshCategory value: " + value);
    }
}
