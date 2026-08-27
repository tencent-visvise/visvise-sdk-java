package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.ModelFormat;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.models.MotionSegment;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenTextMotionOptions;
import com.visvise.sdk.options.WaitOptions;

import java.util.Arrays;
import java.util.List;

/**
 * Example: gen_text_motion —— 文本生动画（node_type=4）
 *
 * 通过提示词描述动作自动生成 3D 动画，一次返回 4 个版本供抽卡选择。
 *
 * 支持两种模式（segments 非空时以多段为准）：
 * 1. 多段提示词 segments（时间轴分段，1~15 段）
 * 2. 单段提示词 prompt（segments 为空时回退使用）
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

        System.out.println("[gen_text_motion] 开始文本生动画（多段 segments）...");

        // 多段提示词：时间轴分段，1~15 段；每段 num_frames / duration 二选一。
        // 非空 segments 优先于 prompt（以多段为准）。
        List<MotionSegment> segments = Arrays.asList(
                new MotionSegment("从站立姿势开始，缓缓抬起右手").setNumFrames(60),
                new MotionSegment("向前走两步").setNumFrames(90).setOverlapFramesWithPrev(10),
                new MotionSegment("转身并挥手告别").setNumFrames(60).setOverlapFramesWithPrev(10)
        );
        List<String> modelIds = client.genTextMotion(MODEL_PATH, null,
                GenTextMotionOptions.create()
                        .setOutputModelFormat(ModelFormat.FBX)
                        .setSegments(segments)
                        .setName("example_gen_text_motion_multi"),
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
