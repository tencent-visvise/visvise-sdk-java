package com.visvise.sdk.options;

/**
 * GenRiggingOptions defines optional parameters for GenRigging
 */
public class GenRiggingOptions {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_rigging";
    /** optional, algorithm model name */
    private String algorithmModel;
    /** optional, mesh category: "humanoid" (default) or "tetrapod" */
    private String meshCategory = "humanoid";
    /** optional, template skeleton */
    private Object templateSkeleton;

    public GenRiggingOptions() {
    }

    public String getName() {
        return name;
    }

    public GenRiggingOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenRiggingOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public String getMeshCategory() {
        return meshCategory;
    }

    public GenRiggingOptions setMeshCategory(String meshCategory) {
        this.meshCategory = meshCategory;
        return this;
    }

    public Object getTemplateSkeleton() {
        return templateSkeleton;
    }

    public GenRiggingOptions setTemplateSkeleton(Object templateSkeleton) {
        this.templateSkeleton = templateSkeleton;
        return this;
    }

    public static GenRiggingOptions create() {
        return new GenRiggingOptions();
    }
}
