package com.visvise.sdk.options;

import com.visvise.sdk.enums.MeshRefineMode;
import com.visvise.sdk.enums.OutputModelFormat;

/**
 * GenMeshRefineOptions defines optional parameters for GenMeshRefine
 */
public class GenMeshRefineOptions {
    private String name = "gen_mesh_refine";
    private String algorithmModel;
    private OutputModelFormat inputModelFormat = OutputModelFormat.FBX;
    private MeshRefineMode mode;
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
