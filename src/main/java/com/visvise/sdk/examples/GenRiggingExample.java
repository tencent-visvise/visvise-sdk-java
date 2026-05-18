package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenRiggingOptions;
import com.visvise.sdk.options.WaitOptions;

/**
 * Example: gen_rigging —— 骨骼架设（node_type=5）
 *
 * 为输入的 3D 模型自动生成骨骼结构。
 * SDK 内部自动构建 JSON 参数文件并打包成 zip 上传。
 */
public class GenRiggingExample {

    static final String APP_ID     = System.getenv("VISVISE_APP_ID");
    static final String SECRET_KEY = System.getenv("VISVISE_SECRET_KEY");
    static final String RTX        = System.getenv("VISVISE_RTX");
    static final String ENV        = System.getenv().getOrDefault("VISVISE_ENV", "prod");

    static final String ASSETS     = "examples/assets";
    static final String MODEL_PATH = ASSETS + "/rigging_model.fbx";

    public static void main(String[] args) throws WeaverError {
        Environment env = "dev".equals(ENV) ? Environment.DEV : "test".equals(ENV) ? Environment.TEST : Environment.PROD;
        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY,
                ClientOptions.create().setEnv(env));

        System.out.println("[gen_rigging] 开始骨骼架设...");

        // SDK 自动将 rigging_model.fbx + 参数 JSON 打包成 zip 上传
        // 无需手动准备 zip 包
        String modelId = client.genRigging(MODEL_PATH,
                GenRiggingOptions.create()
                        .setAlgorithmModel("VISVISE-GoRigging-V1.0.0")
                        .setMeshCategory("humanoid")    // humanoid（人形）或 tetrapod（四足）
                        .setName("example_gen_rigging"),
                RTX);
        System.out.println("[gen_rigging] 任务已创建，model_id=" + modelId);

        ModelInfo model = client.waitModel(modelId,
                WaitOptions.create().setInterval(5).setTimeout(600),
                RTX);
        System.out.println("[gen_rigging] 生成成功！耗时 " + model.getTimeCost() + "s");
        System.out.println("  output_model : " + model.getOutputModel());
    }
}
