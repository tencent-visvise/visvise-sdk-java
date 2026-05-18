package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.ModelFormat;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenVideoMotionOptions;
import com.visvise.sdk.options.WaitOptions;

/**
 * Example: gen_video_motion —— 视频生动画（node_type=4）
 *
 * 从视频中提取动作数据，驱动 3D 模型生成动画。
 */
public class GenVideoMotionExample {

    static final String APP_ID     = System.getenv("VISVISE_APP_ID");
    static final String SECRET_KEY = System.getenv("VISVISE_SECRET_KEY");
    static final String RTX        = System.getenv("VISVISE_RTX");
    static final String ENV        = System.getenv().getOrDefault("VISVISE_ENV", "prod");

    static final String ASSETS     = "src/test/resources/assets";
    static final String MODEL_PATH = ASSETS + "/animation_model.fbx";
    static final String VIDEO_PATH = ASSETS + "/animation_video.mp4";

    public static void main(String[] args) throws WeaverError {
        Environment env = "dev".equals(ENV) ? Environment.DEV : "test".equals(ENV) ? Environment.TEST : Environment.PROD;
        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY,
                ClientOptions.create().setEnv(env));

        System.out.println("[gen_video_motion] 开始视频生动画...");

        String modelId = client.genVideoMotion(MODEL_PATH, VIDEO_PATH,
                GenVideoMotionOptions.create()
                        .setAlgorithmModel("VISVISE-FramingAI-Base-V1.5.0")
                        .setOutputModelFormat(ModelFormat.FBX)
                        .setWithHand(true)
                        .setName("example_gen_video_motion"),
                RTX);
        System.out.println("[gen_video_motion] 任务已创建，model_id=" + modelId);

        ModelInfo model = client.waitModel(modelId,
                WaitOptions.create().setInterval(5).setTimeout(900),
                RTX);
        System.out.println("[gen_video_motion] 生成成功！耗时 " + model.getTimeCost() + "s");
        System.out.println("  output_model : " + model.getOutputModel());
    }
}
