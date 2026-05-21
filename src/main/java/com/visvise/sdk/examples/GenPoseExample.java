package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.ModelFormat;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenPoseOptions;
import com.visvise.sdk.options.WaitOptions;

import java.util.Arrays;
import java.util.List;

/**
 * Example: gen_pose —— 批量图生 Pose（node_type=12）
 *
 * 从参考图片中提取姿态，驱动 3D 模型生成对应 Pose。
 */
public class GenPoseExample {

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

        System.out.println("[gen_pose] 开始图生 Pose...");

        List<String> inputImages = Arrays.asList(ASSETS + "/pose_ref.png");

        List<String> modelIds = client.genPose(MODEL_PATH, inputImages,
                GenPoseOptions.create()
                        .setOutputModelFormat(ModelFormat.FBX)
                        .setName("example_gen_pose"),
                RTX);
        System.out.println("[gen_pose] 任务已创建，model_ids=" + modelIds);

        for (String mid : modelIds) {
            ModelInfo model = client.waitModel(mid,
                    WaitOptions.create().setInterval(3).setTimeout(600),
                    RTX);
            System.out.println("[gen_pose] " + mid + " 生成成功！耗时 " + model.getTimeCost() + "s");
            System.out.println("  output_model : " + model.getOutputModel());
        }
    }
}
