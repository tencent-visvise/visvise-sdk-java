package com.visvise.sdk.enums;

/**
 * NodeType represents the node type enum
 */
public enum NodeType {
    RE_TOPOLOGY(1, "Re-topology"),
    LOD(2, "LOD"),
    IMG_TO_3D_HIGH(3, "Image to 3D (High)"),
    ANIMATION(4, "Framing AI Animation"),
    RIGGING(5, "Skeleton setup"),
    SKINNING(6, "Skinning"),
    IMG_TO_360(7, "Image to 360"),
    TEXTURE(8, "Texture"),
    UV(9, "UV unwrap"),
    MESH_REFINE(10, "Mesh refine"),
    IMG_TO_3D_MID(11, "Image to 3D (Mid)"),
    IMG_TO_POSE(12, "Image to Pose"),
    IMG_TO_3D_LOW(13, "Image to 3D (Low)"),
    SEGMENT_2D(14, "2D Segmentation");

    private final int value;
    private final String description;

    NodeType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static NodeType fromValue(int value) {
        for (NodeType type_ : values()) {
            if (type_.value == value) {
                return type_;
            }
        }
        throw new IllegalArgumentException("Unknown NodeType value: " + value);
    }
}
