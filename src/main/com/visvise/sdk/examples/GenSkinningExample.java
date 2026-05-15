package com.visvise.sdk.examples;

import com.visvise.sdk.VisviseClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.options.ClientOptions;
import com.visvise.sdk.options.GenSkinningOptions;
import com.visvise.sdk.options.WaitOptions;

import java.util.Arrays;
import java.util.List;

/**
 * Example: gen_skinning —— 蒙皮生成（node_type=6）
 *
 * 为带骨骼的 3D 模型自动生成蒙皮权重。
 * SDK 内部自动构建 JSON 参数文件（含 mesh_names / joint_names）并打包成 zip 上传。
 */
public class GenSkinningExample {

    static final String APP_ID     = System.getenv("VISVISE_APP_ID");
    static final String SECRET_KEY = System.getenv("VISVISE_SECRET_KEY");
    static final String UID        = System.getenv("VISVISE_UID");
    static final String ENV        = System.getenv().getOrDefault("VISVISE_ENV", "prod");

    static final String ASSETS     = "examples/assets";
    static final String MODEL_PATH = ASSETS + "/skinning_model.fbx";

    // 来自 skinning_model.json
    static final List<String> MESH_NAMES = Arrays.asList(
            "CH_M_ZB_Human_01_Body",
            "CH_M_ZB_Human_01_Hair",
            "CH_M_ZB_Human_01_Weapon"
    );

    static final List<String> JOINT_NAMES = Arrays.asList(
            "Bip002", "Bip002 Pelvis", "Bip002 Spine", "Bip002 Spine1", "Bip002 Spine2",
            "Bip002 Neck", "Bip002 L Clavicle", "Bip002 L UpperArm", "Bip002 L Forearm",
            "Bip002 L Hand", "Bip002 L Finger0", "Bip002 L Finger01", "Bip002 L Finger02",
            "Bip002 L Finger1", "Bip002 L Finger11", "Bip002 L Finger12",
            "Bip002 L Finger2", "Bip002 L Finger21", "Bip002 L Finger22",
            "Bip002 L Finger3", "Bip002 L Finger31", "Bip002 L Finger32",
            "Bip002 L Finger4", "Bip002 L Finger41", "Bip002 L Finger42",
            "Bone001(mirrored)", "Bone002(mirrored)", "Bone003(mirrored)",
            "Bip002 R Clavicle", "Bip002 R UpperArm", "Bip002 R Forearm",
            "Bip002 R Hand", "Bip002 R Finger0", "Bip002 R Finger01", "Bip002 R Finger02",
            "Bip002 R Finger1", "Bip002 R Finger11", "Bip002 R Finger12",
            "Bip002 R Finger2", "Bip002 R Finger21", "Bip002 R Finger22",
            "Bip002 R Finger3", "Bip002 R Finger31", "Bip002 R Finger32",
            "Bip002 R Finger4", "Bip002 R Finger41", "Bip002 R Finger42",
            "Bone028", "Bone001", "Bone002", "Bone003",
            "Bip002 Head", "Bone031", "Bone031(mirrored)",
            "Bip002 L Thigh", "Bip002 L Calf", "Bip002 L Foot", "Bip002 L Toe0",
            "Bip002 R Thigh", "Bip002 R Calf", "Bip002 R Foot", "Bip002 R Toe0",
            "Bone004", "Bone005", "Bone006", "Bone007"
    );

    public static void main(String[] args) throws WeaverError {
        Environment env = "dev".equals(ENV) ? Environment.DEV : "test".equals(ENV) ? Environment.TEST : Environment.PROD;
        VisviseClient client = new VisviseClient(APP_ID, SECRET_KEY, UID,
                ClientOptions.create().setEnv(env));

        System.out.println("[gen_skinning] 开始蒙皮生成...");

        String modelId = client.genSkinning(MODEL_PATH,
                GenSkinningOptions.create(MESH_NAMES, MESH_NAMES)
                        .setAlgorithmModel("VISVISE-GoSkinning-V1.0.0")
                        .setName("example_gen_skinning"));
        System.out.println("[gen_skinning] 任务已创建，model_id=" + modelId);

        ModelInfo model = client.waitModel(modelId,
                WaitOptions.create().setInterval(5).setTimeout(600));
        System.out.println("[gen_skinning] 生成成功！耗时 " + model.getTimeCost() + "s");
        System.out.println("  output_model : " + model.getOutputModel());
    }
}
