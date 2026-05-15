package com.visvise.sdk;

import com.google.gson.JsonObject;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;
import com.visvise.sdk.api.VisviseAPI;
import com.visvise.sdk.http.HTTPClient;
import com.visvise.sdk.enums.Environment;
import com.visvise.sdk.enums.ModelStatus;
import com.visvise.sdk.enums.AnimationSubType;
import com.visvise.sdk.enums.NodeType;
import com.visvise.sdk.exceptions.ErrorFactory;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.http.SSEIterator;
import com.visvise.sdk.models.*;
import com.visvise.sdk.models.ModelInfo;
import com.visvise.sdk.options.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * VisviseClient is the main entry point for the VISVISE Weaver SDK
 */
public class VisviseClient {
    private static final Logger logger = LoggerFactory.getLogger(VisviseClient.class);

    private final HTTPClient http;
    private final VisviseAPI api;

    /**
     * Create a new VisviseClient with custom options
     */
    public VisviseClient(String appId, String secretKey, String uid, ClientOptions opts) {
        if (opts == null) {
            opts = ClientOptions.create();
        }
        String env = opts.getEnv() != null ? opts.getEnv().getValue() : Environment.PROD.getValue();
        int timeout = opts.getTimeout() > 0 ? opts.getTimeout() : 30;
        this.http = new HTTPClient(appId, secretKey, uid, env, timeout);
        this.api = new VisviseAPI(this.http);
        if (opts.isDebug()) {
            setDebug(true);
        }
    }

    /**
     * Create a new VisviseClient with default options (Env.PROD, Timeout=30)
     */
    public VisviseClient(String appId, String secretKey, String uid) {
        this(appId, secretKey, uid, ClientOptions.create());
    }

    /**
     * Returns the underlying API instance
     */
    public VisviseAPI getAPI() {
        return api;
    }

    /**
     * Enable or disable debug logging for this client.
     * When enabled, logs HTTP request path, request body, response status, and response body.
     */
    public VisviseClient setDebug(boolean enabled) {
        this.http.setDebug(enabled);
        return this;
    }

    /**
     * Resolves a file input to a COS URL (without filename parameter)
     */
    private String resolveFile(Object source, boolean isTemp) throws WeaverError {
        if (source == null) {
            throw new WeaverError(-1, "File input cannot be null");
        }

        if (source instanceof String) {
            String s = (String) source;
            if (isLocalFile(s)) {
                String filename = Paths.get(s).getFileName().toString();
                return uploadFile(s, filename, false);
            }
            if (isCosUrl(s)) {
                return s;
            }
            throw new WeaverError(-1, String.format(
                    "Local file not found: %s. If passing a COS URL, it must be a VISVISE platform COS URL (format: https://{bucket}.cos.{region}.myqcloud.com/...)", s));
        }

        if (source instanceof byte[]) {
            byte[] data = (byte[]) source;
            String filename = genRandomFilename(sniffExtension(data, ".bin"));
            return uploadBytes(data, filename, isTemp);
        }

        if (source instanceof File) {
            File file = (File) source;
            try {
                String filename = file.getName();
                return uploadBytes(Files.readAllBytes(file.toPath()), filename, isTemp);
            } catch (IOException e) {
                throw new WeaverError(-1, String.format("Failed to read file: %s", e.getMessage()));
            }
        }

        if (source instanceof InputStream) {
            try {
                byte[] data = toByteArray((InputStream) source);
                String filename = genRandomFilename(sniffExtension(data, ".bin"));
                return uploadBytes(data, filename, isTemp);
            } catch (IOException e) {
                throw new WeaverError(-1, String.format("Failed to read data: %s", e.getMessage()));
            }
        }

        throw new WeaverError(-1, "Unsupported file input type");
    }

    private String resolveModelFile(Object source, boolean isTemp) throws WeaverError {
        if (source == null) {
            throw new WeaverError(-1, "Model file input cannot be null");
        }

        if (source instanceof String) {
            String s = (String) source;
            if (isLocalFile(s)) {
                String lower = s.toLowerCase();
                if (lower.endsWith(".fbx") || lower.endsWith(".obj") || lower.endsWith(".glb") || lower.endsWith(".gltf")) {
                    try {
                        byte[] data = Files.readAllBytes(Paths.get(s));
                        String srcFilename = Paths.get(s).getFileName().toString();
                        String stem = srcFilename.substring(0, srcFilename.lastIndexOf('.'));
                        String zipFilename = stem + ".zip";
                        return uploadZip(data, srcFilename, zipFilename, isTemp);
                    } catch (IOException e) {
                        throw new WeaverError(-1, String.format("Failed to read file: %s", e.getMessage()));
                    }
                }
                String filename = Paths.get(s).getFileName().toString();
                return uploadFile(s, filename, isTemp);
            }
            if (isCosUrl(s)) {
                return s;
            }
            throw new WeaverError(-1, String.format("Local file not found: %s", s));
        }

        byte[] data;
        if (source instanceof byte[]) {
            data = (byte[]) source;
        } else if (source instanceof File) {
            try {
                data = Files.readAllBytes(((File) source).toPath());
            } catch (IOException e) {
                throw new WeaverError(-1, String.format("Failed to read file: %s", e.getMessage()));
            }
        } else if (source instanceof InputStream) {
            try {
                data = toByteArray((InputStream) source);
            } catch (IOException e) {
                throw new WeaverError(-1, String.format("Failed to read data: %s", e.getMessage()));
            }
        } else {
            throw new WeaverError(-1, "Unsupported model file input type");
        }

        if (isZip(data)) {
            String filename = genRandomFilename(".zip");
            return uploadBytes(data, filename, isTemp);
        }

        String filename = genRandomFilename(sniffExtension(data, ".fbx"));
        String stem = filename.substring(0, filename.lastIndexOf('.'));
        String zipFilename = stem + ".zip";
        return uploadZip(data, filename, zipFilename, isTemp);
    }

    private String uploadFile(String path, String filename, boolean isTemp) throws WeaverError {
        GetCosCredResult cred = api.getCosCred(isTemp);
        if (cred == null) {
            throw new WeaverError(-1, "Failed to get COS credentials");
        }

        try {
            byte[] data = Files.readAllBytes(Paths.get(path));
            return uploadWithCred(cred, data, filename, isTemp);
        } catch (IOException e) {
            throw new WeaverError(-1, String.format("Failed to read file: %s", e.getMessage()));
        }
    }

    private String uploadBytes(byte[] data, String filename, boolean isTemp) throws WeaverError {
        GetCosCredResult cred = api.getCosCred(isTemp);
        if (cred == null) {
            throw new WeaverError(-1, "Failed to get COS credentials");
        }
        return uploadWithCred(cred, data, filename, isTemp);
    }

    private String uploadWithCred(GetCosCredResult cred, byte[] data, String filename, boolean isTemp) throws WeaverError {
        if (filename == null || filename.isEmpty()) {
            filename = String.format("upload_%d.bin", System.currentTimeMillis() % 1000000);
        }

        String cosKey = cred.getPathPrefix().replaceAll("/$", "") + "/" + filename;

        COSCredentials credentials = new BasicCOSCredentials(
                cred.getCred().getTmpSecretId(),
                cred.getCred().getTmpSecretKey());
        Region region = new Region(cred.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(credentials, clientConfig);

        try {
            String bucketName = cred.getBucket();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(data.length);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            com.qcloud.cos.model.PutObjectRequest putRequest = new com.qcloud.cos.model.PutObjectRequest(bucketName, cosKey, inputStream, metadata);
            cosClient.putObject(putRequest);
        } finally {
            cosClient.shutdown();
        }

        return String.format("https://%s.cos.%s.myqcloud.com/%s", cred.getBucket(), cred.getRegion(), cosKey);
    }

    private String uploadZip(byte[] data, String innerFilename, String zipFilename, boolean isTemp) throws WeaverError {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            ZipEntry entry = new ZipEntry(innerFilename);
            zos.putNextEntry(entry);
            zos.write(data);
            zos.closeEntry();
        } catch (IOException e) {
            throw new WeaverError(-1, String.format("Failed to create zip: %s", e.getMessage()));
        }

        return uploadBytes(baos.toByteArray(), zipFilename, isTemp);
    }

    private String resolveAlgorithmModel(String algorithmModel, NodeType nodeType, AnimationSubType subType) throws WeaverError {
        if (algorithmModel != null && !algorithmModel.isEmpty()) {
            return algorithmModel;
        }

        Integer subTypeValue = subType != null ? subType.getValue() : null;
        List<String> models = api.listAlgorithmModel(nodeType.getValue(), subTypeValue);
        if (models == null || models.isEmpty()) {
            throw new WeaverError(-1, String.format(
                    "No available algorithm model for node_type=%d. Please apply through the platform or specify algorithm_model manually", nodeType.getValue()));
        }

        String selected = models.get(0);
        logger.info("algorithm_model not specified, auto-selecting first available model: {}", selected);
        return selected;
    }

    /**
     * Polls and waits for model generation to complete
     */
    public ModelInfo waitModel(String modelId, WaitOptions opts) throws WeaverError {
        if (opts == null) {
            opts = WaitOptions.defaults();
        }

        long start = System.currentTimeMillis();
        long interval = opts.getIntervalMillis();
        long timeoutMs = (long) opts.getTimeout() * 1000;

        while (true) {
            long elapsed = System.currentTimeMillis() - start;
            if (elapsed >= timeoutMs) {
                throw ErrorFactory.newPollingTimeoutError(modelId, opts.getTimeout());
            }

            VisviseAPI.ModelListResult result = api.getModelList(
                    Collections.singletonList(modelId), null, null, "", 10, 1);

            if (result.getModels().isEmpty()) {
                logger.info("Model {} not found, continuing to wait...", modelId);
                sleep(interval);
                continue;
            }

            ModelInfo model = result.getModels().get(0);

            if (model.getStatus() == ModelStatus.SUCCESS.getValue()) {
                logger.info("Model {} generated successfully (time: {}s) output_model={}",
                        modelId, model.getTimeCost(), model.getOutputModel());
                return model;
            }

            if (model.getStatus() == ModelStatus.FAILED.getValue()) {
                String reason = "";
                int code = -1;
                if (model.getFailedReason() != null) {
                    reason = model.getFailedReason().getReason();
                    code = model.getFailedReason().getCode();
                }
                throw ErrorFactory.newModelGenerationError(
                        String.format("Model generation failed: %s", reason),
                        code, modelId, "");
            }

            String statusName = model.getStatus() == ModelStatus.RUNNING.getValue() ? "generating" : "pending";
            logger.info("Model {} {}, remaining time: {}s (waited: {}s)",
                    modelId, statusName, model.getRemainingTime(), elapsed / 1000);
            sleep(interval);
        }
    }

    // ════════════════════════════════════════════════════════════════════
    // High-level methods
    // ════════════════════════════════════════════════════════════════════

    /**
     * Generates multi-view images from an image
     */
    public String gen360(Object mainView, Gen360Options opts) throws WeaverError {
        if (opts == null) {
            opts = Gen360Options.create();
        }

        String mainUrl = resolveFile(mainView, false);
        View view = new View(mainUrl);

        if (opts.getBackView() != null) {
            String backUrl = resolveFile(opts.getBackView(), false);
            view.setBackView(backUrl);
        }
        if (opts.getLeftView() != null) {
            String leftUrl = resolveFile(opts.getLeftView(), false);
            view.setLeftView(leftUrl);
        }
        if (opts.getRightView() != null) {
            String rightUrl = resolveFile(opts.getRightView(), false);
            view.setRightView(rightUrl);
        }

        String resolvedModel = resolveAlgorithmModel(opts.getAlgorithmModel(), NodeType.IMG_TO_360, null);

        Map<String, Object> img360 = new HashMap<>();
        img360.put("algorithm_model", resolvedModel);
        img360.put("output_model_format", opts.getOutputModelFormat().getValue());
        img360.put("face_type", opts.getFaceType().getValue());
        if (opts.getEnableAPose() != null) {
            img360.put("enable_a_pose", opts.getEnableAPose());
        }
        if (opts.getStyle() != null && !opts.getStyle().isEmpty()) {
            img360.put("style", opts.getStyle());
        }

        Map<String, Object> params = new HashMap<>();
        params.put("image_gen_360_params", img360);
        return api.genMultiViews(opts.getName(), view, params);
    }

    /**
     * Generates a high-detail 3D model from images
     */
    public String genHighModel(Object mainView, GenHighModelOptions opts) throws WeaverError {
        if (opts == null) {
            opts = GenHighModelOptions.create();
        }

        View view = new View();
        String mainUrl = resolveFile(mainView, false);
        view.setMainView(mainUrl);

        if (opts.getBackView() != null) {
            view.setBackView(resolveFile(opts.getBackView(), false));
        }
        if (opts.getLeftView() != null) {
            view.setLeftView(resolveFile(opts.getLeftView(), false));
        }
        if (opts.getRightView() != null) {
            view.setRightView(resolveFile(opts.getRightView(), false));
        }

        String resolvedModel = resolveAlgorithmModel(opts.getAlgorithmModel(), NodeType.IMG_TO_3D_HIGH, null);

        Map<String, Object> imgParams = new HashMap<>();
        imgParams.put("algorithm_model", resolvedModel);
        imgParams.put("output_model_format", opts.getOutputModelFormat().getValue());
        imgParams.put("face_type", opts.getFaceType().getValue());
        if (opts.getFaceNum() != null) {
            imgParams.put("face_num", opts.getFaceNum());
        }

        Map<String, Object> genParams = new HashMap<>();
        genParams.put("image_gen_model_params", imgParams);
        List<String> modelIds = api.gen3DModel(opts.getName(), NodeType.IMG_TO_3D_HIGH.getValue(),
                genParams, view, "", "", "");
        return modelIds != null && !modelIds.isEmpty() ? modelIds.get(0) : null;
    }

    /**
     * Generates a mid-detail 3D model from images
     */
    public String genMidModel(Object mainView, Object backView, Object leftView, Object rightView, GenMidModelOptions opts) throws WeaverError {
        if (opts == null) {
            opts = GenMidModelOptions.create();
        }

        View view = new View();
        view.setMainView(resolveFile(mainView, false));
        view.setBackView(resolveFile(backView, false));
        view.setLeftView(resolveFile(leftView, false));
        view.setRightView(resolveFile(rightView, false));

        String resolvedModel = resolveAlgorithmModel(opts.getAlgorithmModel(), NodeType.IMG_TO_3D_MID, null);

        Map<String, Object> imgParams = new HashMap<>();
        imgParams.put("algorithm_model", resolvedModel);
        imgParams.put("output_model_format", opts.getOutputModelFormat().getValue());
        imgParams.put("face_type", opts.getFaceType().getValue());
        if (opts.getSegmentModelId() != null && !opts.getSegmentModelId().isEmpty()) {
            imgParams.put("segment_model_id", opts.getSegmentModelId());
        }

        Map<String, Object> genParams = new HashMap<>();
        genParams.put("image_gen_model_params", imgParams);
        List<String> modelIds = api.gen3DModel(opts.getName(), NodeType.IMG_TO_3D_MID.getValue(),
                genParams, view, "", "", "");
        return modelIds != null && !modelIds.isEmpty() ? modelIds.get(0) : null;
    }

    /**
     * Generates a low-detail 3D model from images
     */
    public String genLowModel(Object mainView, GenLowModelOptions opts) throws WeaverError {
        if (opts == null) {
            opts = GenLowModelOptions.create();
        }

        View view = new View();
        view.setMainView(resolveFile(mainView, false));
        if (opts.getBackView() != null) {
            view.setBackView(resolveFile(opts.getBackView(), false));
        }
        if (opts.getLeftView() != null) {
            view.setLeftView(resolveFile(opts.getLeftView(), false));
        }
        if (opts.getRightView() != null) {
            view.setRightView(resolveFile(opts.getRightView(), false));
        }

        String resolvedModel = resolveAlgorithmModel(opts.getAlgorithmModel(), NodeType.IMG_TO_3D_LOW, null);

        Map<String, Object> imgParams = new HashMap<>();
        imgParams.put("algorithm_model", resolvedModel);
        imgParams.put("output_model_format", opts.getOutputModelFormat().getValue());
        imgParams.put("face_type", opts.getFaceType().getValue());

        Map<String, Object> genParams = new HashMap<>();
        genParams.put("image_gen_model_params", imgParams);
        List<String> modelIds = api.gen3DModel(opts.getName(), NodeType.IMG_TO_3D_LOW.getValue(),
                genParams, view, "", "", "");
        return modelIds != null && !modelIds.isEmpty() ? modelIds.get(0) : null;
    }

    /**
     * Performs mesh refinement/optimization
     */
    public String genMeshRefine(Object modelPath, GenMeshRefineOptions opts) throws WeaverError {
        if (opts == null) {
            opts = GenMeshRefineOptions.create();
        }

        String cosUrl = resolveModelFile(modelPath, false);
        String resolvedModel = resolveAlgorithmModel(opts.getAlgorithmModel(), NodeType.MESH_REFINE, null);

        Map<String, Object> params = new HashMap<>();
        params.put("algorithm_model", resolvedModel);
        params.put("input_model_format", opts.getInputModelFormat().getValue());
        if (opts.getMode() != null) {
            params.put("mode", opts.getMode().getValue());
        }
        if (opts.getColorModel() != null) {
            String colorUrl = resolveModelFile(opts.getColorModel(), false);
            params.put("color_model", colorUrl);
        }

        Map<String, Object> genParams = new HashMap<>();
        genParams.put("mesh_refine_params", params);
        List<String> modelIds = api.gen3DModel(opts.getName(), NodeType.MESH_REFINE.getValue(),
                genParams, null, cosUrl, "", "");
        return modelIds != null && !modelIds.isEmpty() ? modelIds.get(0) : null;
    }

    /**
     * Performs re-topology on a model
     */
    public String genRetopology(Object modelPath, GenRetopologyOptions opts) throws WeaverError {
        if (opts == null) {
            opts = GenRetopologyOptions.create();
        }

        String cosUrl = resolveModelFile(modelPath, false);
        String resolvedModel = resolveAlgorithmModel(opts.getAlgorithmModel(), NodeType.RE_TOPOLOGY, null);

        Map<String, Object> params = new HashMap<>();
        params.put("algorithm_model", resolvedModel);
        params.put("output_model_format", opts.getOutputModelFormat().getValue());
        params.put("face_type", opts.getFaceType().getValue());
        if (opts.getDetailLevel() != null) {
            params.put("detail_level", opts.getDetailLevel().getValue());
        }
        if (opts.getFaceNum() != null) {
            params.put("face_num", opts.getFaceNum());
        }

        Map<String, Object> genParams = new HashMap<>();
        genParams.put("re_topology_params", params);
        List<String> modelIds = api.gen3DModel(opts.getName(), NodeType.RE_TOPOLOGY.getValue(),
                genParams, null, cosUrl, "", "");
        return modelIds != null && !modelIds.isEmpty() ? modelIds.get(0) : null;
    }

    /**
     * Generates LOD (Level of Detail) models
     */
    public List<String> genLOD(Object modelPath, List<ReduceFace> reduceFaces, GenLODOptions opts) throws WeaverError {
        if (opts == null) {
            opts = GenLODOptions.create();
        }

        String cosUrl = resolveModelFile(modelPath, false);
        String resolvedModel = resolveAlgorithmModel(opts.getAlgorithmModel(), NodeType.LOD, null);

        List<Map<String, Object>> reduceFacesData = new ArrayList<>();
        for (ReduceFace rf : reduceFaces) {
            reduceFacesData.add(rf.toMap());
        }

        Map<String, Object> lodParams = new HashMap<>();
        lodParams.put("algorithm_model", resolvedModel);
        lodParams.put("output_model_format", opts.getOutputModelFormat().getValue());
        lodParams.put("reduce_faces", reduceFacesData);
        lodParams.put("gen_times", opts.getGenTimes());

        Map<String, Object> genParams = new HashMap<>();
        genParams.put("lod_params", lodParams);
        return api.gen3DModel(opts.getName(), NodeType.LOD.getValue(),
                genParams, null, cosUrl, "", "");
    }

    /**
     * Performs UV unwrapping on a model
     */
    public String genUV(Object modelPath, GenUVOptions opts) throws WeaverError {
        if (opts == null) {
            opts = GenUVOptions.create();
        }

        String cosUrl = resolveModelFile(modelPath, false);
        String resolvedModel = resolveAlgorithmModel(opts.getAlgorithmModel(), NodeType.UV, null);

        Map<String, Object> uvParams = new HashMap<>();
        uvParams.put("algorithm_model", resolvedModel);
        if (opts.getEnableAutoSmoothing() != null) {
            uvParams.put("enable_auto_smoothing", opts.getEnableAutoSmoothing());
        }

        Map<String, Object> genParams = new HashMap<>();
        genParams.put("uv_params", uvParams);
        List<String> modelIds = api.gen3DModel(opts.getName(), NodeType.UV.getValue(),
                genParams, null, cosUrl, "", "");
        return modelIds != null && !modelIds.isEmpty() ? modelIds.get(0) : null;
    }

    /**
     * Generates textures for a model
     */
    public String genTexture(Object modelPath, GenTextureOptions opts) throws WeaverError {
        if (opts == null) {
            opts = GenTextureOptions.create();
        }

        View inputView = opts.getInputView();
        if ((inputView == null || inputView.getMainView() == null || inputView.getMainView().isEmpty())
                && (opts.getPrompt() == null || opts.getPrompt().isEmpty())) {
            throw new WeaverError(-1, "gen_texture requires either input_view.main_view or prompt");
        }

        String cosUrl = resolveModelFile(modelPath, false);
        String resolvedModel = resolveAlgorithmModel(opts.getAlgorithmModel(), NodeType.TEXTURE, null);

        Map<String, Object> texParams = new HashMap<>();
        texParams.put("algorithm_model", resolvedModel);
        if (opts.getResolution() != null) {
            texParams.put("resolution", opts.getResolution());
        }
        if (opts.getUnwarpUV() != null) {
            texParams.put("unwarp_uv", opts.getUnwarpUV());
        }
        if (opts.getPrompt() != null && !opts.getPrompt().isEmpty()) {
            texParams.put("prompt", opts.getPrompt());
        }

        View resolvedView = null;
        if (inputView != null) {
            resolvedView = new View();
            if (inputView.getMainView() != null && !inputView.getMainView().isEmpty()) {
                resolvedView.setMainView(resolveFile(inputView.getMainView(), false));
            }
            if (inputView.getBackView() != null && !inputView.getBackView().isEmpty()) {
                resolvedView.setBackView(resolveFile(inputView.getBackView(), false));
            }
            if (inputView.getLeftView() != null && !inputView.getLeftView().isEmpty()) {
                resolvedView.setLeftView(resolveFile(inputView.getLeftView(), false));
            }
            if (inputView.getRightView() != null && !inputView.getRightView().isEmpty()) {
                resolvedView.setRightView(resolveFile(inputView.getRightView(), false));
            }
        }

        Map<String, Object> genParams = new HashMap<>();
        genParams.put("tex_params", texParams);
        List<String> modelIds = api.gen3DModel(opts.getName(), NodeType.TEXTURE.getValue(),
                genParams, resolvedView, cosUrl, "", "");
        return modelIds != null && !modelIds.isEmpty() ? modelIds.get(0) : null;
    }

    /**
     * Performs skeleton rigging on a model
     */
    public String genRigging(Object modelPath, GenRiggingOptions opts) throws WeaverError {
        if (opts == null) {
            opts = GenRiggingOptions.create();
        }

        String resolvedModel = resolveAlgorithmModel(opts.getAlgorithmModel(), NodeType.RIGGING, null);

        Map<String, Object> jsonData = new HashMap<>();
        Map<String, Object> config = new HashMap<>();
        config.put("mesh_category", opts.getMeshCategory());
        config.put("algo_name", resolvedModel);
        jsonData.put("config", config);

        byte[] zipBytes = buildModelZip(modelPath, jsonData);
        String cosUrl = uploadBytes(zipBytes, "", false);

        Map<String, Object> riggingParams = new HashMap<>();
        riggingParams.put("algorithm_model", resolvedModel);
        if (opts.getTemplateSkeleton() != null) {
            String skeletonUrl = resolveModelFile(opts.getTemplateSkeleton(), false);
            riggingParams.put("template_skeleton", skeletonUrl);
        }

        Map<String, Object> genParams = new HashMap<>();
        genParams.put("go_rigging_params", riggingParams);
        List<String> modelIds = api.gen3DModel(opts.getName(), NodeType.RIGGING.getValue(),
                genParams, null, cosUrl, "", "");
        return modelIds != null && !modelIds.isEmpty() ? modelIds.get(0) : null;
    }

    /**
     * Performs skinning on a rigged model
     */
    public String genSkinning(Object modelPath, GenSkinningOptions opts) throws WeaverError {
        if (opts == null) {
            throw new WeaverError(-1, "gen_skinning requires opts with mesh_names and joint_names");
        }
        if (opts.getMeshNames() == null || opts.getMeshNames().isEmpty()
                || opts.getJointNames() == null || opts.getJointNames().isEmpty()) {
            throw new WeaverError(-1, "gen_skinning requires mesh_names and joint_names");
        }

        String resolvedModel = resolveAlgorithmModel(opts.getAlgorithmModel(), NodeType.SKINNING, null);

        Map<String, Object> jsonData = new HashMap<>();
        Map<String, Object> config = new HashMap<>();
        config.put("algo_name", resolvedModel);
        jsonData.put("config", config);
        Map<String, Object> selection = new HashMap<>();
        selection.put("mesh_names", opts.getMeshNames());
        selection.put("joint_names", opts.getJointNames());
        jsonData.put("selection", selection);

        byte[] zipBytes = buildModelZip(modelPath, jsonData);
        String cosUrl = uploadBytes(zipBytes, "", false);

        List<String> modelIds = api.gen3DModel(opts.getName(), NodeType.SKINNING.getValue(),
                new HashMap<>(), null, cosUrl, "", "");
        return modelIds != null && !modelIds.isEmpty() ? modelIds.get(0) : null;
    }

    /**
     * Generates animation from video
     */
    public String genVideoMotion(Object modelPath, Object videoPath, GenVideoMotionOptions opts) throws WeaverError {
        if (opts == null) {
            opts = GenVideoMotionOptions.create();
        }

        String modelUrl = resolveModelFile(modelPath, false);
        String videoUrl = resolveFile(videoPath, false);

        AnimationSubType subType = AnimationSubType.VIDEO;
        String resolvedModel = resolveAlgorithmModel(opts.getAlgorithmModel(), NodeType.ANIMATION, subType);

        Map<String, Object> framing = new HashMap<>();
        framing.put("algorithm_model", resolvedModel);
        framing.put("output_model_format", opts.getOutputModelFormat().getValue());
        if (opts.getWithHand() != null) {
            framing.put("with_hand", opts.getWithHand());
        }
        if (opts.getMultipleTrack() != null) {
            framing.put("multiple_track", opts.getMultipleTrack());
        }
        if (opts.getRotateAxisAngle() != null && opts.getRotateAxisAngle().length == 3) {
            framing.put("rotate_axis_angle", Arrays.stream(opts.getRotateAxisAngle()).boxed().collect(Collectors.toList()));
        }

        Map<String, Object> genParams = new HashMap<>();
        genParams.put("framing_ai_params", framing);
        List<String> modelIds = api.gen3DModel(opts.getName(), NodeType.ANIMATION.getValue(),
                genParams, null, modelUrl, "", videoUrl);
        return modelIds != null && !modelIds.isEmpty() ? modelIds.get(0) : null;
    }

    /**
     * Generates animation from text prompts
     */
    public List<String> genTextMotion(Object modelPath, String prompt, GenTextMotionOptions opts) throws WeaverError {
        if (opts == null) {
            opts = GenTextMotionOptions.create();
        }

        String modelUrl = resolveModelFile(modelPath, false);

        AnimationSubType subType = AnimationSubType.TEXT;
        String resolvedModel = resolveAlgorithmModel(opts.getAlgorithmModel(), NodeType.ANIMATION, subType);

        Map<String, Object> framingParams = new HashMap<>();
        framingParams.put("algorithm_model", resolvedModel);
        framingParams.put("output_model_format", opts.getOutputModelFormat().getValue());
        framingParams.put("prompt", prompt);

        Map<String, Object> genParams = new HashMap<>();
        genParams.put("framing_ai_params", framingParams);
        return api.gen3DModel(opts.getName(), NodeType.ANIMATION.getValue(),
                genParams, null, modelUrl, "", "");
    }

    /**
     * Generates poses from reference images
     */
    public List<String> genPose(Object modelPath, List<?> inputImages, GenPoseOptions opts) throws WeaverError {
        if (opts == null) {
            opts = GenPoseOptions.create();
        }

        String modelUrl = resolveModelFile(modelPath, false);

        List<String> uploadedImages = new ArrayList<>();
        for (Object img : inputImages) {
            uploadedImages.add(resolveFile(img, false));
        }

        String resolvedModel = resolveAlgorithmModel(opts.getAlgorithmModel(), NodeType.IMG_TO_POSE, null);

        Map<String, Object> params = new HashMap<>();
        params.put("algorithm_model", resolvedModel);
        params.put("output_model_format", opts.getOutputModelFormat().getValue());

        return api.batchGenPose(opts.getName(), modelUrl, uploadedImages, params);
    }

    /**
     * Performs 2D segmentation (SSE streaming interface)
     */
    public String genSegment2D(String modelId360, GenSegment2DOptions opts) throws WeaverError {
        if (opts == null) {
            opts = GenSegment2DOptions.create();
        }

        if ((modelId360 == null || modelId360.isEmpty()) && opts.getInputView() == null) {
            throw new WeaverError(-1, "gen_segment_2d requires either model_id_360 or input_view");
        }

        View resolvedView = null;
        if (opts.getInputView() != null) {
            resolvedView = new View();
            if (opts.getInputView().getMainView() != null && !opts.getInputView().getMainView().isEmpty()) {
                resolvedView.setMainView(resolveFile(opts.getInputView().getMainView(), false));
            }
            if (opts.getInputView().getBackView() != null && !opts.getInputView().getBackView().isEmpty()) {
                resolvedView.setBackView(resolveFile(opts.getInputView().getBackView(), false));
            }
            if (opts.getInputView().getLeftView() != null && !opts.getInputView().getLeftView().isEmpty()) {
                resolvedView.setLeftView(resolveFile(opts.getInputView().getLeftView(), false));
            }
            if (opts.getInputView().getRightView() != null && !opts.getInputView().getRightView().isEmpty()) {
                resolvedView.setRightView(resolveFile(opts.getInputView().getRightView(), false));
            }
        }

        String resolvedModel = resolveAlgorithmModel(opts.getAlgorithmModel(), NodeType.SEGMENT_2D, null);

        Integer splitType = opts.getSplitType() != null ? opts.getSplitType().getValue() : null;
        Integer granularity = opts.getGranularity() != null ? opts.getGranularity().getValue() : null;

        SSEIterator iter = api.initSegment(opts.getName(), resolvedModel, modelId360,
                resolvedView, splitType, granularity,
                opts.getPrompt(), opts.getReadTimeout());

        String newModelId = null;

        try {
            while (true) {
                SSEResult event = iter.next();
                if (event == null) {
                    break;
                }

                switch (event.getEvent()) {
                    case "pre_create":
                        if (event.getData() instanceof JsonObject) {
                            JsonObject data = (JsonObject) event.getData();
                            if (data.has("model_id") && !data.get("model_id").isJsonNull()) {
                                newModelId = data.get("model_id").getAsString();
                                logger.info("gen_segment_2d: pre_create model_id={}", newModelId);
                            }
                        }
                        break;
                    case "thinking":
                        if (event.getData() != null) {
                            String thinking = event.getData().toString();
                            logger.info("gen_segment_2d thinking: {}", thinking);
                            if (opts.getOnThinking() != null) {
                                opts.getOnThinking().onThinking(thinking);
                            }
                        }
                        break;
                    case "reply":
                        logger.info("gen_segment_2d: reply received, complete");
                        if (newModelId == null) {
                            throw ErrorFactory.newModelGenerationError(
                                    "2D segmentation did not return model_id", -1, "", "");
                        }
                        return newModelId;
                    case "error":
                        String msg = "";
                        int code = -1;
                        if (event.getData() instanceof JsonObject) {
                            JsonObject data = (JsonObject) event.getData();
                            if (data.has("msg") && !data.get("msg").isJsonNull()) {
                                msg = data.get("msg").getAsString();
                            }
                            if (data.has("code") && !data.get("code").isJsonNull()) {
                                code = data.get("code").getAsInt();
                            }
                        } else {
                            msg = event.getData().toString();
                        }
                        throw ErrorFactory.newModelGenerationError(
                                String.format("2D segmentation failed: %s", msg),
                                code, newModelId != null ? newModelId : "", "");
                }
            }
        } finally {
            try {
                iter.close();
            } catch (IOException e) {
                logger.warn("Failed to close SSE iterator", e);
            }
        }

        if (newModelId == null) {
            throw ErrorFactory.newModelGenerationError("2D segmentation did not return model_id", -1, "", "");
        }
        return newModelId;
    }

    /**
     * Uploads a local file to COS and returns the COS URL
     */
    public String upload(String path, String filename, boolean isTemp) throws WeaverError {
        return uploadFile(path, filename, isTemp);
    }

    // ════════════════════════════════════════════════════════════════════
    // Helper methods
    // ════════════════════════════════════════════════════════════════════

    private byte[] buildModelZip(Object source, Map<String, Object> jsonData) throws WeaverError {
        byte[] data;
        String srcFilename;

        if (source instanceof String) {
            String s = (String) source;
            if (!isLocalFile(s)) {
                throw new WeaverError(-1, String.format("model_path only accepts local file path or binary content, not COS URL: %s", s));
            }
            try {
                data = Files.readAllBytes(Paths.get(s));
                srcFilename = Paths.get(s).getFileName().toString();
            } catch (IOException e) {
                throw new WeaverError(-1, String.format("Failed to read file: %s", e.getMessage()));
            }
        } else if (source instanceof byte[]) {
            data = (byte[]) source;
            srcFilename = genRandomFilename(sniffExtension(data, ".fbx"));
        } else if (source instanceof File) {
            try {
                data = Files.readAllBytes(((File) source).toPath());
                srcFilename = ((File) source).getName();
            } catch (IOException e) {
                throw new WeaverError(-1, String.format("Failed to read file: %s", e.getMessage()));
            }
        } else if (source instanceof InputStream) {
            try {
                data = toByteArray((InputStream) source);
                srcFilename = genRandomFilename(sniffExtension(data, ".fbx"));
            } catch (IOException e) {
                throw new WeaverError(-1, String.format("Failed to read data: %s", e.getMessage()));
            }
        } else {
            throw new WeaverError(-1, "Unsupported model source type");
        }

        String jsonStr = new com.google.gson.Gson().toJson(jsonData);
        byte[] jsonBytes = jsonStr.getBytes();

        String stem = srcFilename.substring(0, srcFilename.lastIndexOf('.'));
        String jsonEntry = stem + ".json";

        Map<String, byte[]> newFiles = new HashMap<>();
        newFiles.put(srcFilename, data);
        newFiles.put(jsonEntry, jsonBytes);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (Map.Entry<String, byte[]> entry : newFiles.entrySet()) {
                ZipEntry zipEntry = new ZipEntry(entry.getKey());
                zos.putNextEntry(zipEntry);
                zos.write(entry.getValue());
                zos.closeEntry();
            }
        } catch (IOException e) {
            throw new WeaverError(-1, String.format("Failed to create zip: %s", e.getMessage()));
        }

        return baos.toByteArray();
    }

    private boolean isLocalFile(String s) {
        Path path = Paths.get(s);
        return Files.exists(path) && !Files.isDirectory(path);
    }

    private boolean isCosUrl(String s) {
        return s.startsWith("https://") && s.contains(".myqcloud.com") && s.contains(".cos.");
    }

    private boolean isZip(byte[] data) {
        return data.length >= 4 && data[0] == 0x50 && data[1] == 0x4B && data[2] == 0x03 && data[3] == 0x04;
    }

    private String genRandomFilename(String suffix) {
        if (!suffix.startsWith(".")) {
            suffix = "." + suffix;
        }
        return UUID.randomUUID().toString() + suffix;
    }

    private String sniffExtension(byte[] data, String defaultExt) {
        if (data.length == 0) {
            return defaultExt.startsWith(".") ? defaultExt : "." + defaultExt;
        }

        // PNG
        if (data.length >= 8 && data[0] == (byte) 0x89 && data[1] == 0x50 && data[2] == 0x4E && data[3] == 0x47) {
            return ".png";
        }
        // JPEG
        if (data.length >= 3 && data[0] == (byte) 0xFF && data[1] == (byte) 0xD8 && data[2] == (byte) 0xFF) {
            return ".jpg";
        }
        // GIF
        if (data.length >= 6 && (matchBytes(data, 0, "GIF87a") || matchBytes(data, 0, "GIF89a"))) {
            return ".gif";
        }
        // BMP
        if (data.length >= 2 && data[0] == 'B' && data[1] == 'M') {
            return ".bmp";
        }
        // WebP
        if (data.length >= 12 && data[0] == 'R' && data[1] == 'I' && data[2] == 'F' && data[3] == 'F'
                && data[8] == 'W' && data[9] == 'E' && data[10] == 'B' && data[11] == 'P') {
            return ".webp";
        }
        // FBX binary
        if (data.length >= 16 && matchBytes(data, 0, "Kaydara FBX Bina")) {
            return ".fbx";
        }
        // GLB
        if (data.length >= 4 && data[0] == 'g' && data[1] == 'l' && data[2] == 'T' && data[3] == 'F') {
            return ".glb";
        }
        // MP4/MOV
        if (data.length >= 12 && data[4] == 'f' && data[5] == 't' && data[6] == 'y' && data[7] == 'p') {
            return ".mp4";
        }

        return defaultExt.startsWith(".") ? defaultExt : "." + defaultExt;
    }

    private boolean matchBytes(byte[] data, int offset, String str) {
        if (data.length < offset + str.length()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (data[offset + i] != (byte) str.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[8192];
        int n;
        while ((n = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, n);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
