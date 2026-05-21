package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.ModelFormat;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenMeshRefineOptions;
import com.visvise.sdk.options.WaitOptions;

/**
 * Example: gen_mesh_refine —— 重布线/布线优化（node_type=10）
 *
 * 对模型网格进行布线重建优化。
 */
public class GenMeshRefineExample {

    static final String APP_ID     = System.getenv("VISVISE_APP_ID");
    static final String SECRET_KEY = System.getenv("VISVISE_SECRET_KEY");
    static final String RTX        = System.getenv("VISVISE_RTX");
    static final String ENV        = System.getenv().getOrDefault("VISVISE_ENV", "prod");

    static final String ASSETS     = "src/test/resources/assets";
    static final String MODEL_PATH = ASSETS + "/high_model.fbx";

    public static void main(String[] args) throws WeaverError {
        Environment env = "dev".equals(ENV) ? Environment.DEV : "test".equals(ENV) ? Environment.TEST : Environment.PROD;
        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY,
                ClientOptions.create().setEnv(env));

        System.out.println("[gen_mesh_refine] 开始重布线...");

        String modelId = client.genMeshRefine(MODEL_PATH,
                GenMeshRefineOptions.create()
                        .setInputModelFormat(ModelFormat.FBX)
                        .setName("example_gen_mesh_refine"),
                RTX);
        System.out.println("[gen_mesh_refine] 任务已创建，model_id=" + modelId);

        ModelInfo model = client.waitModel(modelId,
                WaitOptions.create().setInterval(5).setTimeout(900),
                RTX);
        System.out.println("[gen_mesh_refine] 生成成功！耗时 " + model.getTimeCost() + "s");
        System.out.println("  output_model : " + model.getOutputModel());
    }
}
