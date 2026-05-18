package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.ModelFormat;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenLowModelOptions;
import com.visvise.sdk.options.WaitOptions;

/**
 * Example: gen_low_model —— 图生低模（node_type=13）
 *
 * 仅需主视图，其余视图可选。
 */
public class GenLowModelExample {

    static final String APP_ID     = System.getenv("VISVISE_APP_ID");
    static final String SECRET_KEY = System.getenv("VISVISE_SECRET_KEY");
    static final String RTX        = System.getenv("VISVISE_RTX");
    static final String ENV        = System.getenv().getOrDefault("VISVISE_ENV", "prod");

    static final String ASSETS     = "src/test/resources/assets";
    static final String MAIN_VIEW  = ASSETS + "/main_view.png";

    public static void main(String[] args) throws WeaverError {
        Environment env = "dev".equals(ENV) ? Environment.DEV : "test".equals(ENV) ? Environment.TEST : Environment.PROD;
        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY,
                ClientOptions.create().setEnv(env));

        System.out.println("[gen_low_model] 开始生成低模...");

        String modelId = client.genLowModel(MAIN_VIEW,
                GenLowModelOptions.create()
                        .setAlgorithmModel("Tripo-v1.0-快速生成")
                        .setOutputModelFormat(ModelFormat.FBX)
                        .setFaceType(FaceType.TRIANGLE)
                        .setName("example_gen_low_model"),
                RTX);
        System.out.println("[gen_low_model] 任务已创建，model_id=" + modelId);

        ModelInfo model = client.waitModel(modelId,
                WaitOptions.create().setInterval(3).setTimeout(600),
                RTX);
        System.out.println("[gen_low_model] 生成成功！耗时 " + model.getTimeCost() + "s");
        System.out.println("  output_model : " + model.getOutputModel());
    }
}
