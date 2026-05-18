package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.models.ImageGen360Output;
import com.visvise.sdk.models.View;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.Gen360Options;
import com.visvise.sdk.options.WaitOptions;

/**
 * Example: gen_360 —— 图生360（生成多视图）
 *
 * 从单张图片生成 360° 多视图，输出结果可作为图生高模/中模/低模的输入。
 */
public class Gen360Example {

    // 从环境变量读取鉴权信息
    static final String APP_ID     = System.getenv("VISVISE_APP_ID");
    static final String SECRET_KEY = System.getenv("VISVISE_SECRET_KEY");
    static final String RTX        = System.getenv("VISVISE_RTX");
    static final String ENV        = System.getenv().getOrDefault("VISVISE_ENV", "prod");

    // 素材路径（请替换为实际文件路径）
    static final String ASSETS    = "examples/assets";
    static final String MAIN_VIEW = ASSETS + "/main_view.png";

    public static void main(String[] args) throws WeaverError {
        Environment env = "dev".equals(ENV) ? Environment.DEV : "test".equals(ENV) ? Environment.TEST : Environment.PROD;
        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY,
                ClientOptions.create().setEnv(env));

        System.out.println("[gen_360] 开始生成多视图，输入图片：" + MAIN_VIEW);

        String modelId = client.gen360(MAIN_VIEW,
                Gen360Options.create()
                        .setAlgorithmModel("VISVISE-MultiView-V1.0.0")
                        .setName("example_gen_360"),
                RTX);
        System.out.println("[gen_360] 任务已创建，model_id=" + modelId);

        System.out.println("[gen_360] 等待完成...");
        ModelInfo model = client.waitModel(modelId,
                WaitOptions.create().setInterval(3).setTimeout(300),
                RTX);

        ImageGen360Output output = model.getImageGen360Output();
        System.out.println("[gen_360] 生成成功！耗时 " + model.getTimeCost() + "s");
        if (output != null && output.getOutputView() != null) {
            View v = output.getOutputView();
            System.out.println("  main_view  : " + v.getMainView());
            System.out.println("  back_view  : " + v.getBackView());
            System.out.println("  left_view  : " + v.getLeftView());
            System.out.println("  right_view : " + v.getRightView());
        }
        if (output != null && output.getHorizontalViewVideo() != null) {
            System.out.println("  旋转视频   : " + output.getHorizontalViewVideo());
        }
    }
}
