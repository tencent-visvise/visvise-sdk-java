package com.visvise.sdk.enums;

/**
 * RiggingAlgoScenario represents the algorithm scenario for rigging
 */
public enum RiggingAlgoScenario {
    AUTO_GEN(1, "Auto generate"),
    TEMPLATE_SKELETON(2, "Template skeleton"),
    ADDITIONAL_BONES(3, "Additional bones");

    private final int value;
    private final String description;

    RiggingAlgoScenario(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static RiggingAlgoScenario fromValue(int value) {
        for (RiggingAlgoScenario scenario : values()) {
            if (scenario.value == value) {
                return scenario;
            }
        }
        throw new IllegalArgumentException("Unknown RiggingAlgoScenario value: " + value);
    }
}
