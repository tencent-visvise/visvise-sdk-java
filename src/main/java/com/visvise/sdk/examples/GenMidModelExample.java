package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.api.VisviseAPI;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.ModelFormat;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.models.View;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenMidModelOptions;
import com.visvise.sdk.options.WaitOptions;

/**
 * Example: gen_mid_model —— 图生中模（node_type=3）
 *
 * 中模要求四视图全部必传。
 * 优先从环境变量 MV_360_MODEL_ID 读取 gen_360 的输出，自动提取四视图。
 */
public class GenMidModelExample {

    static final String APP_ID     = System.getenv("VISVISE_APP_ID");
    static final String SECRET_KEY = System.getenv("VISVISE_SECRET_KEY");
    static final String RTX        = System.getenv("VISVISE_RTX");
    static final String ENV        = System.getenv().getOrDefault("VISVISE_ENV", "prod");

    static final String ASSETS = "src/test/resources/assets";

    private static String stripSign(String url) {
        return url != null ? url.split("\\?")[0] : null;
    }

    public static void main(String[] args) throws WeaverError {
        Environment env = "dev".equals(ENV) ? Environment.DEV : "test".equals(ENV) ? Environment.TEST : Environment.PROD;
        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY,
                ClientOptions.create().setEnv(env));

        String mvModelId = System.getenv("MV_360_MODEL_ID");
        String mainView, backView, leftView, rightView;

        if (mvModelId != null && !mvModelId.isEmpty()) {
            System.out.println("[gen_mid_model] 从 gen_360 输出提取四视图 (model_id=" + mvModelId + ")");
            VisviseAPI.ModelListResult result = client.getAPI().getModelList(
                    java.util.Collections.singletonList(mvModelId), null, null, "", 10, 1, RTX);
            View out = result.getModels().get(0).getImageGen360Output().getOutputView();
            mainView  = stripSign(out.getMainView());
            backView  = stripSign(out.getBackView());
            leftView  = stripSign(out.getLeftView());
            rightView = stripSign(out.getRightView());
        } else {
            mainView  = System.getenv().getOrDefault("MV_MAIN",  ASSETS + "/main_view.png");
            backView  = System.getenv().getOrDefault("MV_BACK",  ASSETS + "/back_view.png");
            leftView  = System.getenv().getOrDefault("MV_LEFT",  ASSETS + "/left_view.png");
            rightView = System.getenv().getOrDefault("MV_RIGHT", ASSETS + "/right_view.png");
        }

        System.out.println("[gen_mid_model] 开始生成中模...");
        String modelId = client.genMidModel(mainView, backView, leftView, rightView,
                GenMidModelOptions.create()
                        .setAlgorithmModel("VISVISE-MeshGen-V1.0.0")
                        .setOutputModelFormat(ModelFormat.FBX)
                        .setFaceType(FaceType.TRIANGLE)
                        .setName("example_gen_mid_model"),
                RTX);
        System.out.println("[gen_mid_model] 任务已创建，model_id=" + modelId);

        ModelInfo model = client.waitModel(modelId,
                WaitOptions.create().setInterval(5).setTimeout(900),
                RTX);
        System.out.println("[gen_mid_model] 生成成功！耗时 " + model.getTimeCost() + "s");
        System.out.println("  output_model : " + model.getOutputModel());
    }
}
