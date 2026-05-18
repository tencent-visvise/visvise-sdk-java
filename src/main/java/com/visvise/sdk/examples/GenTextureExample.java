package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.models.View;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenTextureOptions;
import com.visvise.sdk.options.WaitOptions;

/**
 * Example: gen_texture —— 贴图纹理生成（node_type=8）
 *
 * input_view 中的本地图片路径会被 SDK 自动上传到 COS。
 * input_view.main_view 和 prompt 必须传其中一个。
 */
public class GenTextureExample {

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

        System.out.println("[gen_texture] 开始贴图纹理生成...");

        // 本地图片路径，SDK 内部自动上传到 COS
        View inputView = new View();
        inputView.setMainView(ASSETS + "/tex_ref_front.jpg");
        inputView.setBackView(ASSETS + "/tex_ref_back.jpg");
        inputView.setLeftView(ASSETS + "/tex_ref_left.jpg");

        String modelId = client.genTexture(MODEL_PATH,
                GenTextureOptions.create()
                        .setAlgorithmModel("hunyuan3D-TEX-v2.0")
                        .setInputView(inputView)
                        .setResolution(2048)
                        .setUnwarpUV(false)
                        .setName("example_gen_texture"),
                RTX);
        System.out.println("[gen_texture] 任务已创建，model_id=" + modelId);

        ModelInfo model = client.waitModel(modelId,
                WaitOptions.create().setInterval(5).setTimeout(900),
                RTX);
        System.out.println("[gen_texture] 生成成功！耗时 " + model.getTimeCost() + "s");
        System.out.println("  output_model : " + model.getOutputModel());
    }
}
