package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.ModelFormat;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.LODFile;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.models.ReduceFace;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenLODOptions;
import com.visvise.sdk.options.WaitOptions;

import java.util.Arrays;
import java.util.List;

/**
 * Example: gen_lod —— LOD 减面（node_type=2）
 *
 * gen_times=1 表示不抽卡，生成单个版本。
 */
public class GenLODExample {

    static final String APP_ID     = System.getenv("VISVISE_APP_ID");
    static final String SECRET_KEY = System.getenv("VISVISE_SECRET_KEY");
    static final String RTX        = System.getenv("VISVISE_RTX");
    static final String ENV        = System.getenv().getOrDefault("VISVISE_ENV", "prod");

    static final String ASSETS     = "src/test/resources/assets";
    static final String MODEL_PATH = ASSETS + "/tex_model.obj";

    public static void main(String[] args) throws WeaverError {
        Environment env = "dev".equals(ENV) ? Environment.DEV : "test".equals(ENV) ? Environment.TEST : Environment.PROD;
        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY,
                ClientOptions.create().setEnv(env));

        System.out.println("[gen_lod] 开始 LOD 减面...");

        List<ReduceFace> reduceFaces = Arrays.asList(
                new ReduceFace(1, 50, FaceType.QUAD),
                new ReduceFace(2, 25, FaceType.QUAD),
                new ReduceFace(3, 13, FaceType.QUAD)
        );

        List<String> modelIds = client.genLOD(MODEL_PATH, reduceFaces,
                GenLODOptions.create()
                        .setAlgorithmModel("VISVISE-LOD-V1.0.0")
                        .setOutputModelFormat(ModelFormat.FBX)
                        .setGenTimes(1)
                        .setName("example_gen_lod"),
                RTX);
        System.out.println("[gen_lod] 任务已创建，model_ids=" + modelIds);

        for (String mid : modelIds) {
            ModelInfo model = client.waitModel(mid,
                    WaitOptions.create().setInterval(5).setTimeout(600),
                    RTX);
            System.out.println("[gen_lod] " + mid + " 生成成功！耗时 " + model.getTimeCost() + "s");
            if (model.getLodOutput() != null) {
                for (LODFile lf : model.getLodOutput().getLodFiles()) {
                    System.out.println("  LOD" + lf.getReduceLevel() + ": " + lf.getDownloadUrl());
                }
            } else {
                System.out.println("  output_model: " + model.getOutputModel());
            }
        }
    }
}
