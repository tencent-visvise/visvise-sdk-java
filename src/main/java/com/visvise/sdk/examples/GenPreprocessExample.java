package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.StyleType;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenPatterAutoRemoveOptions;
import com.visvise.sdk.options.GenStyleTransferOptions;

/**
 * Example: genStyleTransfer / genPatterAutoRemove —— 2D 预处理。
 *
 * 同步处理输入图片并保存为 2D 预处理资产（node_type=16），直接返回 model_id。
 */
public class GenPreprocessExample {
    private static final String APP_ID = System.getenv("VISVISE_APP_ID");
    private static final String SECRET_KEY = System.getenv("VISVISE_SECRET_KEY");
    private static final String RTX = System.getenv("VISVISE_RTX");
    private static final String ENV = System.getenv().getOrDefault("VISVISE_ENV", "prod");

    private static final String ASSETS = "src/test/resources/assets";

    public static void main(String[] args) throws WeaverError {
        Environment env = "dev".equals(ENV) ? Environment.DEV : "test".equals(ENV) ? Environment.TEST : Environment.PROD;
        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY,
                ClientOptions.create().setEnv(env));

        // 原画风格化
        System.out.println("[gen_preprocess] 开始原画风格化...");
        String styledId = client.genStyleTransfer(
                ASSETS + "/preprocess.png",
                StyleType.GRAYSCALE,
                GenStyleTransferOptions.create()
                        .setName("example_gen_style_transfer"),
                RTX);
        System.out.println("[gen_preprocess] 原画风格化完成，model_id=" + styledId);

        // 智能去花纹
        System.out.println("[gen_preprocess] 开始智能去花纹...");
        String patternedId = client.genPatterAutoRemove(
                ASSETS + "/preprocess.png",
                GenPatterAutoRemoveOptions.create()
                        .setName("example_gen_patter_auto_remove"),
                RTX);
        System.out.println("[gen_preprocess] 智能去花纹完成，model_id=" + patternedId);
    }
}
