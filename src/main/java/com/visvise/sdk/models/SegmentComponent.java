package com.visvise.sdk.models;

/**
 * SegmentComponent represents a single segmented component (2D split).
 */
public class SegmentComponent {
    private int label;
    private String color;
    private String name;

    public SegmentComponent() {
    }

    public SegmentComponent(int label, String color, String name) {
        this.label = label;
        this.color = color;
        this.name = name;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
