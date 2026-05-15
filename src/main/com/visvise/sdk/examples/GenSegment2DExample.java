package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.SegmentGranularity;
import com.visvise.sdk.enums.SegmentSplitType;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenSegment2DOptions;

/**
 * Example: gen_segment_2d —— 2D 拆分（node_type=14）
 *
 * 对图生 360 输出的多视图进行组件分割，生成的分割资产可作为后续图生中模 / 低模的
 * segment_model_id 输入，用于基于分割结果的精细化生成。
 *
 * 使用 SSE 协议，过程中会推送 thinking 思考态。
 */
public class GenSegment2DExample {

    static final String APP_ID     = System.getenv("VISVISE_APP_ID");
    static final String SECRET_KEY = System.getenv("VISVISE_SECRET_KEY");
    static final String UID        = System.getenv("VISVISE_UID");
    static final String ENV        = System.getenv().getOrDefault("VISVISE_ENV", "prod");

    public static void main(String[] args) throws WeaverError {
        Environment env = "dev".equals(ENV) ? Environment.DEV : "test".equals(ENV) ? Environment.TEST : Environment.PROD;
        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY, UID,
                ClientOptions.create().setEnv(env));

        // 需要先有 gen_360 输出的 model_id
        String mvModelId = System.getenv("MV_360_MODEL_ID");
        if (mvModelId == null || mvModelId.isEmpty()) {
            System.out.println("[gen_segment_2d] 需要先运行 Gen360Example 并设置环境变量 MV_360_MODEL_ID");
            return;
        }

        System.out.println("[gen_segment_2d] 基于 360 模型 " + mvModelId + " 进行 2D 拆分...");

        String segModelId = client.genSegment2D(mvModelId,
                GenSegment2DOptions.create()
                        .setSplitType(SegmentSplitType.FRONT_VIEW)    // 1 正视图（默认） / 2 四视图
                        .setGranularity(SegmentGranularity.MEDIUM)     // 1 粗 / 2 中（默认） / 3 细
                        .setPrompt(null)                                 // 可选：自然语言描述拆分规则
                        .setOnThinking(content -> System.out.println("  [thinking] " + content))
                        .setName("example_gen_segment_2d"));
        System.out.println("[gen_segment_2d] 分割完成，model_id=" + segModelId);
        System.out.println("  → 可作为图生中模/低模的 segment_model_id 参数使用");
    }
}
