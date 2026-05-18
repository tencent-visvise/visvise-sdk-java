package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.ModelFormat;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenTextMotionOptions;
import com.visvise.sdk.options.WaitOptions;

import java.util.List;

/**
 * Example: gen_text_motion —— 文本生动画（node_type=4）
 *
 * 通过提示词描述动作自动生成 3D 动画，一次返回 4 个版本供抽卡选择。
 */
public class GenTextMotionExample {

    static final String APP_ID     = System.getenv("VISVISE_APP_ID");
    static final String SECRET_KEY = System.getenv("VISVISE_SECRET_KEY");
    static final String RTX        = System.getenv("VISVISE_RTX");
    static final String ENV        = System.getenv().getOrDefault("VISVISE_ENV", "prod");

    static final String ASSETS     = "src/test/resources/assets";
    static final String MODEL_PATH = ASSETS + "/animation_model.fbx";

    public static void main(String[] args) throws WeaverError {
        Environment env = "dev".equals(ENV) ? Environment.DEV : "test".equals(ENV) ? Environment.TEST : Environment.PROD;
        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY,
                ClientOptions.create().setEnv(env));

        System.out.println("[gen_text_motion] 开始文本生动画...");

        // 一次生成 4 个版本供抽卡
        List<String> modelIds = client.genTextMotion(MODEL_PATH, "一个人在跳街舞",
                GenTextMotionOptions.create()
                        .setAlgorithmModel("VISVISE-TextMotion-V1.1.0")
                        .setOutputModelFormat(ModelFormat.FBX)
                        .setName("example_gen_text_motion"),
                RTX);
        System.out.println("[gen_text_motion] 任务已创建，共 " + modelIds.size() + " 个版本：" + modelIds);

        System.out.println("[gen_text_motion] 等待第一个版本完成（可按需等待全部）...");
        ModelInfo model = client.waitModel(modelIds.get(0),
                WaitOptions.create().setInterval(5).setTimeout(900),
                RTX);
        System.out.println("[gen_text_motion] model_ids[0] 生成成功！耗时 " + model.getTimeCost() + "s");
        System.out.println("  output_model : " + model.getOutputModel());
    }
}
