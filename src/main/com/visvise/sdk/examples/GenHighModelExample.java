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
import com.visvise.sdk.options.GenHighModelOptions;
import com.visvise.sdk.options.WaitOptions;

/**
 * Example: gen_high_model —— 图生高模（node_type=3）
 *
 * 高模用第二个算法模型 Tripo-v3.1-ultra（支持单图输入，无需四视图）。
 * 也可传入四视图以提升质量，优先从 MV_360_MODEL_ID 获取。
 */
public class GenHighModelExample {

    static final String APP_ID     = System.getenv("VISVISE_APP_ID");
    static final String SECRET_KEY = System.getenv("VISVISE_SECRET_KEY");
    static final String UID        = System.getenv("VISVISE_UID");
    static final String ENV        = System.getenv().getOrDefault("VISVISE_ENV", "prod");

    static final String ASSETS = "examples/assets";

    private static String stripSign(String url) {
        return url != null ? url.split("\\?")[0] : null;
    }

    public static void main(String[] args) throws WeaverError {
        Environment env = "dev".equals(ENV) ? Environment.DEV : "test".equals(ENV) ? Environment.TEST : Environment.PROD;
        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY, UID,
                ClientOptions.create().setEnv(env));

        String mvModelId = System.getenv("MV_360_MODEL_ID");
        String mainView, backView, leftView, rightView;

        if (mvModelId != null && !mvModelId.isEmpty()) {
            System.out.println("[gen_high_model] 从 gen_360 输出提取四视图 (model_id=" + mvModelId + ")");
            VisviseAPI.ModelListResult result = client.getAPI().getModelList(
                    java.util.Collections.singletonList(mvModelId), null, null, "", 10, 1);
            View out = result.getModels().get(0).getImageGen360Output().getOutputView();
            mainView  = stripSign(out.getMainView());
            backView  = stripSign(out.getBackView());
            leftView  = stripSign(out.getLeftView());
            rightView = stripSign(out.getRightView());
        } else {
            // 使用本地主视图（Tripo-v3.1-ultra 支持单图）
            mainView  = ASSETS + "/main_view.png";
            backView  = null;
            leftView  = null;
            rightView = null;
        }

        // 高模用第 2 个算法模型（不依赖多图）
        String algorithmModel = "Tripo-v3.1-ultra";

        System.out.println("[gen_high_model] algorithm_model=" + algorithmModel);
        System.out.println("[gen_high_model] 开始生成高模...");
        String modelId = client.genHighModel(mainView,
                GenHighModelOptions.create()
                        .setBackView(backView)
                        .setLeftView(leftView)
                        .setRightView(rightView)
                        .setAlgorithmModel(algorithmModel)
                        .setOutputModelFormat(ModelFormat.FBX)
                        .setFaceType(FaceType.TRIANGLE)
                        .setName("example_gen_high_model"));
        System.out.println("[gen_high_model] 任务已创建，model_id=" + modelId);

        ModelInfo model = client.waitModel(modelId,
                WaitOptions.create().setInterval(10).setTimeout(1200));
        System.out.println("[gen_high_model] 生成成功！耗时 " + model.getTimeCost() + "s");
        System.out.println("  output_model : " + model.getOutputModel());
    }
}
