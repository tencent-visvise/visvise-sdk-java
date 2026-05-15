package com.visvise.sdk.options;

import com.visvise.sdk.enums.MeshRefineMode;
import com.visvise.sdk.enums.OutputModelFormat;

/**
 * GenMeshRefineOptions defines optional parameters for GenMeshRefine
 */
public class GenMeshRefineOptions {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_mesh_refine";
    /** optional, algorithm model name */
    private String algorithmModel;
    /** optional, input model format (default fbx) */
    private OutputModelFormat inputModelFormat = OutputModelFormat.FBX;
    /** optional, MeshRefineMode.OPTIMIZE(1) or MeshRefineMode.DENSIFY(2) */
    private MeshRefineMode mode;
    /** optional, color model for texture preservation */
    private Object colorModel;

    public GenMeshRefineOptions() {
    }

    public String getName() {
        return name;
    }

    public GenMeshRefineOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenMeshRefineOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public OutputModelFormat getInputModelFormat() {
        return inputModelFormat;
    }

    public GenMeshRefineOptions setInputModelFormat(OutputModelFormat inputModelFormat) {
        this.inputModelFormat = inputModelFormat;
        return this;
    }

    public MeshRefineMode getMode() {
        return mode;
    }

    public GenMeshRefineOptions setMode(MeshRefineMode mode) {
        this.mode = mode;
        return this;
    }

    public Object getColorModel() {
        return colorModel;
    }

    public GenMeshRefineOptions setColorModel(Object colorModel) {
        this.colorModel = colorModel;
        return this;
    }

    public static GenMeshRefineOptions create() {
        return new GenMeshRefineOptions();
    }
}
