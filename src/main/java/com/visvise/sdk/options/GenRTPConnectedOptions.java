package com.visvise.sdk.options;

/**
 * GenRTPConnectedOptions defines optional parameters for GenRTPConnected（重拓扑-连通体, node_type=19）
 */
public class GenRTPConnectedOptions {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_rtp_connected";
    /** optional, algorithm model name; auto-selected if empty */
    private String algorithmModel;

    public GenRTPConnectedOptions() {
    }

    public String getName() {
        return name;
    }

    public GenRTPConnectedOptions setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public GenRTPConnectedOptions setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public static GenRTPConnectedOptions create() {
        return new GenRTPConnectedOptions();
    }
}
