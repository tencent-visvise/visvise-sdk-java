package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.ImageCutoutMode;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.ImageCutoutResult;
import com.visvise.sdk.options.ClientOptions;

import java.util.Collections;

/**
 * Example: image_cutout —— 图片抠图
 *
 * 对输入图片抠图，支持直接抠图（透明底）、纯色底抠图、换底并抠图三种模式。
 * 同步接口，不创建模型资产，直接返回处理后图片的 COS URL。
 */
public class ImageCutoutExample {

    // 从环境变量读取鉴权信息
    static final String APP_ID     = "vsa_6b674d783b0ad99f";
    static final String SECRET_KEY = "vss_2e4c473fb25ba48253ea4b99474d0b339599beac63aa3443";
    static final String RTX        = "hc";
    static final String ENV        =  "test";

    // 素材路径（请替换为实际文件路径）
    static final String ASSETS    = "src/test/resources/assets";
    static final String MAIN_VIEW = ASSETS + "/main_view.png";

    public static void main(String[] args) throws WeaverError {
        Environment env = "dev".equals(ENV) ? Environment.DEV : "test".equals(ENV) ? Environment.TEST : Environment.PROD;
        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY,
                ClientOptions.create().setEnv(env).setTimeout(5*60000));

        System.out.println("[image_cutout] 开始抠图，输入图片：" + MAIN_VIEW);

        ImageCutoutResult result = client.imageCutout(
                Collections.singletonList(MAIN_VIEW),
                ImageCutoutMode.DIRECT,
                "white",
                RTX);

        if (result == null || result.getImageList() == null) {
            System.out.println("[image_cutout] 未返回结果");
            return;
        }
        System.out.println("[image_cutout] 抠图完成，底色=" + result.getBackgroundColor());
        for (String url : result.getImageList()) {
            System.out.println("  " + url);
        }
    }
}
