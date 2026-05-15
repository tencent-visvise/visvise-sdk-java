package com.visvise.sdk.options;

import java.util.List;

/**
 * GenSkinningOptions defines optional parameters for GenSkinning
 */
public class GenSkinningOptions {
    private String name = "gen_skinning";
    private String algorithmModel;
    private List<String> meshNames;
    private List<String> jointNames;

    public GenSkinningOptions(List<String> meshNames, List<String> jointNames) {
        this.meshNames = meshNames;
        this.jointNames = jointNames;
    }

    public String getName() {
        return name;
    }

    public GenSkinningOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenSkinningOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public List<String> getMeshNames() {
        return meshNames;
    }

    public GenSkinningOptions setMeshNames(List<String> meshNames) {
        this.meshNames = meshNames;
        return this;
    }

    public List<String> getJointNames() {
        return jointNames;
    }

    public GenSkinningOptions setJointNames(List<String> jointNames) {
        this.jointNames = jointNames;
        return this;
    }

    public static GenSkinningOptions create(List<String> meshNames, List<String> jointNames) {
        return new GenSkinningOptions(meshNames, jointNames);
    }
}
