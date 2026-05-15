package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.DetailLevel;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.ModelFormat;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenRetopologyOptions;
import com.visvise.sdk.options.WaitOptions;

/**
 * Example: gen_retopology —— 重拓扑（node_type=1）
 *
 * 对高面数模型进行拓扑优化。
 * 混元模型传 detail_level，VISVISE 自研模型传 face_num，二选一。
 */
public class GenRetopologyExample {

    static final String APP_ID     = System.getenv("VISVISE_APP_ID");
    static final String SECRET_KEY = System.getenv("VISVISE_SECRET_KEY");
    static final String UID        = System.getenv("VISVISE_UID");
    static final String ENV        = System.getenv().getOrDefault("VISVISE_ENV", "prod");

    static final String ASSETS     = "examples/assets";
    static final String MODEL_PATH = ASSETS + "/high_model.fbx";

    public static void main(String[] args) throws WeaverError {
        Environment env = "dev".equals(ENV) ? Environment.DEV : "test".equals(ENV) ? Environment.TEST : Environment.PROD;
        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY, UID,
                ClientOptions.create().setEnv(env));

        System.out.println("[gen_retopology] 开始重拓扑...");

        String modelId = client.genRetopology(MODEL_PATH,
                GenRetopologyOptions.create()
                        .setAlgorithmModel("hunyuan3D-RTP-v1.5")
                        .setOutputModelFormat(ModelFormat.FBX)
                        .setFaceType(FaceType.QUAD)
                        .setDetailLevel(DetailLevel.HIGH)    // 混元模型用 detail_level
                        .setName("example_gen_retopology"));
        System.out.println("[gen_retopology] 任务已创建，model_id=" + modelId);

        ModelInfo model = client.waitModel(modelId,
                WaitOptions.create().setInterval(5).setTimeout(900));
        System.out.println("[gen_retopology] 生成成功！耗时 " + model.getTimeCost() + "s");
        System.out.println("  output_model : " + model.getOutputModel());
    }
}
