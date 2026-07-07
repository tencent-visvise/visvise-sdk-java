package com.visvise.sdk.options;

import com.visvise.sdk.enums.MeshCategory;
import com.visvise.sdk.enums.RiggingAlgoScenario;

import java.util.List;

/**
 * GenRiggingOptions defines optional parameters for GenRigging
 */
public class GenRiggingOptions {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_rigging";
    /** optional, algorithm model name */
    private String algorithmModel;
    /** optional, mesh category: MeshCategory.HUMANOID (default) or MeshCategory.TETRAPOD */
    private MeshCategory meshCategory = MeshCategory.HUMANOID;
    /** optional, template skeleton (local path, COS URL, File, bytes, or InputStream) */
    private Object templateSkeleton;
    /** optional, mesh names */
    private List<String> meshNames;
    /** optional, algo scenario: RiggingAlgoScenario.AUTO_GEN / TEMPLATE_SKELETON / ADDITIONAL_BONES */
    private RiggingAlgoScenario algoScenario;
    /** optional, generate root skeleton */
    private boolean generateRoot;
    /** optional, temperature (default -1 means not set) */
    private double temperature = -1;
    /** optional, number of beams (default -1 means not set) */
    private int numBeams = -1;

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

    public MeshCategory getMeshCategory() {
        return meshCategory;
    }

    public GenRiggingOptions setMeshCategory(MeshCategory meshCategory) {
        this.meshCategory = meshCategory;
        return this;
    }

    // 兼容之前使用的方式，只传字符串
    public GenRiggingOptions setMeshCategory(String meshCategory) {
        this.meshCategory = MeshCategory.fromValue(meshCategory);
        return this;
    }

    public Object getTemplateSkeleton() {
        return templateSkeleton;
    }

    public GenRiggingOptions setTemplateSkeleton(Object templateSkeleton) {
        this.templateSkeleton = templateSkeleton;
        return this;
    }

    public List<String> getMeshNames() {
        return meshNames;
    }

    public GenRiggingOptions setMeshNames(List<String> meshNames) {
        this.meshNames = meshNames;
        return this;
    }

    public RiggingAlgoScenario getAlgoScenario() {
        return algoScenario;
    }

    public GenRiggingOptions setAlgoScenario(RiggingAlgoScenario algoScenario) {
        this.algoScenario = algoScenario;
        return this;
    }

    public boolean getGenerateRoot() {
        return generateRoot;
    }

    public GenRiggingOptions setGenerateRoot(boolean generateRoot) {
        this.generateRoot = generateRoot;
        return this;
    }

    public double getTemperature() {
        return temperature;
    }

    public GenRiggingOptions setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public int getNumBeams() {
        return numBeams;
    }

    public GenRiggingOptions setNumBeams(int numBeams) {
        this.numBeams = numBeams;
        return this;
    }

    public static GenRiggingOptions create() {
        return new GenRiggingOptions();
    }
}
