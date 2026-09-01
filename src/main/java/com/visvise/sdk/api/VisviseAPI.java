package com.visvise.sdk.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.visvise.sdk.enums.SegmentViewType;
import com.visvise.sdk.enums.StyleType;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.http.HTTPClient;
import com.visvise.sdk.http.SSEIterator;
import com.visvise.sdk.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * VisviseAPI provides atomic API methods
 */
public class VisviseAPI {
    private static final Gson gson = new Gson();
    private final HTTPClient http;

    public VisviseAPI(HTTPClient httpClient) {
        this.http = httpClient;
    }

    /**
     * Retrieves COS temporary credentials for direct file upload
     */
    public GetCosCredResult getCosCred(boolean isTemp, boolean isPublic, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        if (isTemp) {
            body.put("is_temp", true);
        }
        if (isPublic) {
            body.put("is_public", true);
        }

        Object data = http.post("openapi/weaver/resource/get_cos_cred", body, rtx);
        if (data == null) {
            return null;
        }

        return parseCosCredResult(data);
    }

    private GetCosCredResult parseCosCredResult(Object data) {
        if (!(data instanceof JsonObject)) {
            return null;
        }
        JsonObject m = (JsonObject) data;
        GetCosCredResult result = new GetCosCredResult();

        if (m.has("start_time") && !m.get("start_time").isJsonNull()) {
            result.setStartTime(m.get("start_time").getAsLong());
        }
        if (m.has("expired_time") && !m.get("expired_time").isJsonNull()) {
            result.setExpiredTime(m.get("expired_time").getAsLong());
        }
        if (m.has("bucket") && !m.get("bucket").isJsonNull()) {
            result.setBucket(m.get("bucket").getAsString());
        }
        if (m.has("region") && !m.get("region").isJsonNull()) {
            result.setRegion(m.get("region").getAsString());
        }
        if (m.has("path_prefix") && !m.get("path_prefix").isJsonNull()) {
            result.setPathPrefix(m.get("path_prefix").getAsString());
        }
        if (m.has("cred") && !m.get("cred").isJsonNull()) {
            JsonObject credJson = m.getAsJsonObject("cred");
            CosCred cred = new CosCred();
            if (credJson.has("tmp_secret_id") && !credJson.get("tmp_secret_id").isJsonNull()) {
                cred.setTmpSecretId(credJson.get("tmp_secret_id").getAsString());
            }
            if (credJson.has("tmp_secret_key") && !credJson.get("tmp_secret_key").isJsonNull()) {
                cred.setTmpSecretKey(credJson.get("tmp_secret_key").getAsString());
            }
            if (credJson.has("session_token") && !credJson.get("session_token").isJsonNull()) {
                cred.setSessionToken(credJson.get("session_token").getAsString());
            }
            result.setCred(cred);
        }
        return result;
    }

    /**
     * Retrieves the remaining generation count for the current API key
     */
    public UserQuota getUserQuota(String rtx) throws WeaverError {
        Object data = http.post("openapi/weaver/resource/get_user_quota", new HashMap<>(), rtx);
        if (data == null || !(data instanceof JsonObject)) {
            return null;
        }
        JsonObject m = (JsonObject) data;
        UserQuota quota = new UserQuota();
        if (m.has("model_quota") && !m.get("model_quota").isJsonNull()) {
            quota.setModelQuota(m.get("model_quota").getAsInt());
        }
        if (m.has("animation_quota") && !m.get("animation_quota").isJsonNull()) {
            quota.setAnimationQuota(m.get("animation_quota").getAsInt());
        }
        if (m.has("server_ts") && !m.get("server_ts").isJsonNull()) {
            quota.setServerTs(m.get("server_ts").getAsLong());
        }
        if (m.has("image_processing_quota") && !m.get("image_processing_quota").isJsonNull()) {
            quota.setImageProcessingQuota(m.get("image_processing_quota").getAsInt());
        }
        return quota;
    }

    /**
     * Creates a 3D generation task (async)
     */
    public List<String> gen3DModel(
            String name,
            int nodeType,
            Map<String, Object> params,
            View inputView,
            String inputModel,
            String inputModelFormat,
            String inputVideo,
            String rtx) throws WeaverError {

        Map<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("node_type", nodeType);
        body.put("params", params);

        if (inputView != null) {
            body.put("input_view", inputView.toMap());
        }
        if (inputModel != null && !inputModel.isEmpty()) {
            body.put("input_model", inputModel);
        }
        if (inputModelFormat != null && !inputModelFormat.isEmpty()) {
            body.put("input_model_format", inputModelFormat);
        }
        if (inputVideo != null && !inputVideo.isEmpty()) {
            body.put("input_video", inputVideo);
        }

        Object data = http.post("openapi/weaver/resource/gen_3d_model", body, rtx);
        if (data == null || !(data instanceof JsonObject)) {
            return null;
        }

        JsonObject m = (JsonObject) data;
        if (m.has("model_ids") && !m.get("model_ids").isJsonNull()) {
            JsonArray modelIds = m.getAsJsonArray("model_ids");
            List<String> result = new ArrayList<>();
            for (JsonElement id : modelIds) {
                result.add(id.getAsString());
            }
            return result;
        }
        return null;
    }

    /**
     * Generates multi-view images from a single image (async)
     */
    public String genMultiViews(String name, View inputView, Map<String, Object> params, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("input_view", inputView.toMap());
        body.put("params", params);

        Object data = http.post("openapi/weaver/resource/gen_multi_views", body, rtx);
        if (data == null || !(data instanceof JsonObject)) {
            return null;
        }

        JsonObject m = (JsonObject) data;
        if (m.has("model_id") && !m.get("model_id").isJsonNull()) {
            return m.get("model_id").getAsString();
        }
        return null;
    }

    /**
     * Retrieves the model asset list
     */
    public ModelListResult getModelList(
            List<String> modelIdList,
            List<Integer> nodeTypeList,
            List<Integer> statusList,
            String keyword,
            int limit,
            int page,
            Long lastTs,
            List<Integer> modelTypeList,
            Sorter sorter,
            String rtx) throws WeaverError {

        Map<String, Object> body = new HashMap<>();
        body.put("limit", limit);
        body.put("page", page);

        if (modelIdList != null && !modelIdList.isEmpty()) {
            body.put("model_id_list", modelIdList);
        }
        if (nodeTypeList != null && !nodeTypeList.isEmpty()) {
            body.put("node_type_list", nodeTypeList);
        }
        if (statusList != null && !statusList.isEmpty()) {
            body.put("status_list", statusList);
        }
        if (keyword != null && !keyword.isEmpty()) {
            body.put("keyword", keyword);
        }
        if (modelTypeList != null && !modelTypeList.isEmpty()) {
            body.put("model_type_list", modelTypeList);
        }
        if (lastTs != null && lastTs > 0) {
            body.put("last_ts", lastTs);
        }
        if (sorter != null && sorter.getName() != null && !sorter.getName().isEmpty()) {
            body.put("sorter", sorter);
        }

        Object data = http.post("openapi/weaver/resource/get_model_list", body, rtx);
        if (data == null || !(data instanceof JsonObject)) {
            return new ModelListResult(new ArrayList<>(), 0);
        }

        JsonObject m = (JsonObject) data;
        List<ModelInfo> models = new ArrayList<>();
        int totalCount = 0;

        if (m.has("model_list") && !m.get("model_list").isJsonNull()) {
            JsonArray modelList = m.getAsJsonArray("model_list");
            for (JsonElement element : modelList) {
                models.add(gson.fromJson(element, ModelInfo.class));
            }
        }
        if (m.has("total_count") && !m.get("total_count").isJsonNull()) {
            totalCount = m.get("total_count").getAsInt();
        }

        return new ModelListResult(models, totalCount);
    }

    /**
     * Retrieves the list of algorithm models for a given node type
     */
    public List<String> listAlgorithmModel(int nodeType, Integer subType, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("node_type", nodeType);
        if (subType != null) {
            body.put("type", subType);
        }

        Object data = http.post("openapi/weaver/resource/list_algorithm_model", body, rtx);
        if (data == null || !(data instanceof JsonObject)) {
            return null;
        }

        JsonObject m = (JsonObject) data;
        if (m.has("model_list") && !m.get("model_list").isJsonNull()) {
            JsonArray modelList = m.getAsJsonArray("model_list");
            List<String> result = new ArrayList<>();
            for (JsonElement model : modelList) {
                result.add(model.getAsString());
            }
            return result;
        }
        return null;
    }

    /**
     * Generates a signed download URL for a model asset
     */
    public String downloadModel(String modelId, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("model_id", modelId);

        Object data = http.post("openapi/weaver/resource/download_model", body, rtx);
        if (data != null && !(data instanceof JsonNull)) {
            return data.toString();
        }
        return null;
    }

    /**
     * Deletes a single model asset
     */
    public void deleteModel(String modelId, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("model_id", modelId);
        http.post("openapi/weaver/resource/delete_model", body, rtx);
    }

    /**
     * Batch deletes model assets
     */
    public void batchDeleteModel(List<String> modelIds, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("model_ids", modelIds);
        http.post("openapi/weaver/resource/batch_delete_model", body, rtx);
    }

    /**
     * Removes the background from an image
     */
    public String removeBackground(String imageUrl, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("image_url", imageUrl);

        Object data = http.post("openapi/weaver/resource/remove_background", body, rtx);
        if (data == null || !(data instanceof JsonObject)) {
            return null;
        }

        JsonObject m = (JsonObject) data;
        if (m.has("image_url") && !m.get("image_url").isJsonNull()) {
            return m.get("image_url").getAsString();
        }
        return null;
    }

    /**
     * Stylizes an input image and returns a 24-hour signed COS download URL.
     * Preserve every query parameter when passing the result to {@link #genPreprocess}.
     */
    public String styleTransfer(String inputView, StyleType styleType, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("input_view", inputView);
        body.put("style_type", styleType.getValue());

        Object data = http.post("openapi/weaver/resource/style_transfer", body, rtx);
        if (data == null || !(data instanceof JsonObject)) {
            return null;
        }

        JsonObject m = (JsonObject) data;
        if (m.has("result_image") && !m.get("result_image").isJsonNull()) {
            return m.get("result_image").getAsString();
        }
        return null;
    }

    /**
     * Automatically removes surface patterns and returns a 24-hour signed COS download URL.
     * Preserve every query parameter when passing the result to {@link #genPreprocess}.
     */
    public String patterAutoRemove(String inputView, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("input_view", inputView);

        Object data = http.post("openapi/weaver/resource/patter_auto_remove", body, rtx);
        if (data == null || !(data instanceof JsonObject)) {
            return null;
        }

        JsonObject m = (JsonObject) data;
        if (m.has("result_image") && !m.get("result_image").isJsonNull()) {
            return m.get("result_image").getAsString();
        }
        return null;
    }



    /**
     * Saves a processed image as a 2D preprocess model asset.
     */
    public String genPreprocess(
            String name,
            String inputView,
            Map<String, Object> params,
            String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("input_view", inputView);
        if (params != null) {
            body.putAll(params);
        }

        Object data = http.post("openapi/weaver/resource/gen_preprocess", body, rtx);
        if (data == null || !(data instanceof JsonObject)) {
            return null;
        }
        JsonObject m = (JsonObject) data;
        if (m.has("model_id") && !m.get("model_id").isJsonNull()) {
            return m.get("model_id").getAsString();
        }
        return null;
    }



    /**
     * Batch generates poses from images (async)
     */
    public List<String> batchGenPose(
            String name,
            String inputModel,
            List<String> inputImages,
            Map<String, Object> params,
            String rtx) throws WeaverError {

        Map<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("input_model", inputModel);
        body.put("input_images", inputImages);
        body.put("params", params);

        Object data = http.post("openapi/weaver/resource/batch_gen_pose", body, rtx);
        if (data == null || !(data instanceof JsonObject)) {
            return null;
        }

        JsonObject m = (JsonObject) data;
        if (m.has("model_ids") && !m.get("model_ids").isJsonNull()) {
            JsonArray modelIds = m.getAsJsonArray("model_ids");
            List<String> result = new ArrayList<>();
            for (JsonElement id : modelIds) {
                result.add(id.getAsString());
            }
            return result;
        }
        return null;
    }

    /**
     * Retrieves the text-to-motion prompt demo list
     */
    public List<String> getText2MotionPromptList(String language, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("language", language);

        Object data = http.post("openapi/weaver/demo/get_text2motion_prompt_list", body, rtx);
        if (data == null || !(data instanceof JsonObject)) {
            return null;
        }

        JsonObject m = (JsonObject) data;
        if (m.has("prompt_list") && !m.get("prompt_list").isJsonNull()) {
            JsonArray promptList = m.getAsJsonArray("prompt_list");
            List<String> result = new ArrayList<>();
            for (JsonElement p : promptList) {
                result.add(p.getAsString());
            }
            return result;
        }
        return null;
    }

    /**
     * Initializes 2D segmentation (SSE streaming interface)
     */
    public SSEIterator initSegment(
            String name,
            String algorithmModel,
            String modelId,
            View inputView,
            Integer splitType,
            Integer granularity,
            String prompt,
            int readTimeout,
            String rtx) throws WeaverError {

        Map<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("algorithm_model", algorithmModel);

        if (modelId != null && !modelId.isEmpty()) {
            body.put("model_id", modelId);
        }
        if (inputView != null) {
            body.put("input_view", inputView.toMap());
        }
        if (splitType != null) {
            body.put("split_type", splitType);
        }
        if (granularity != null) {
            body.put("granularity", granularity);
        }
        if (prompt != null && !prompt.isEmpty()) {
            body.put("prompt", prompt);
        }

        return http.postSSE("openapi/weaver/component/init_segment", body, readTimeout, rtx);
    }

    /**
     * Enters the segment state and specifies the component to split.
     */
    public OperatorResult beginSegment(String clientId, int componentLabel, SegmentViewType viewType, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("view_type", viewType.getValue());
        body.put("component_label", componentLabel);

        Object data = http.post("openapi/weaver/component/begin_segment", body, rtx);
        if (data == null) {
            return null;
        }
        return gson.fromJson((JsonElement) data, OperatorResult.class);
    }

    /**
     * Marks the region to split out in the segment state (repeatable).
     * add_pixels / remove_pixels / rects jointly define the split region.
     */
    public OperatorResult segmentComponent(String clientId, SegmentViewType viewType, List<Pixel> addPixels, List<Pixel> removePixels, List<Rect> rects, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("view_type", viewType.getValue());
        if (addPixels != null && !addPixels.isEmpty()) {
            body.put("add_pixels", addPixels);
        }
        if (removePixels != null && !removePixels.isEmpty()) {
            body.put("remove_pixels", removePixels);
        }
        if (rects != null && !rects.isEmpty()) {
            body.put("rects", rects);
        }

        Object data = http.post("openapi/weaver/component/segment", body, rtx);
        if (data == null) {
            return null;
        }
        return gson.fromJson((JsonElement) data, OperatorResult.class);
    }

    /**
     * Finalizes the current segmentation result.
     */
    public OperatorResult confirmSegment(String clientId, SegmentViewType viewType, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("view_type", viewType.getValue());

        Object data = http.post("openapi/weaver/component/confirm_segment", body, rtx);
        if (data == null) {
            return null;
        }
        return gson.fromJson((JsonElement) data, OperatorResult.class);
    }

    /**
     * Cancels the current segmentation and reverts to the pre-segment state.
     */
    public OperatorResult cancelSegment(String clientId, SegmentViewType viewType, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("view_type", viewType.getValue());

        Object data = http.post("openapi/weaver/component/cancel_segment", body, rtx);
        if (data == null) {
            return null;
        }
        return gson.fromJson((JsonElement) data, OperatorResult.class);
    }

    /**
     * Merges multiple components into one connected component.
     */
    public MultiViewSegmentResult mergeComponent(String clientId, List<Integer> componentLabels, SegmentViewType viewType, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("component_labels", componentLabels);
        body.put("view_type", viewType.getValue());

        Object data = http.post("openapi/weaver/component/merge", body, rtx);
        if (data == null) {
            return null;
        }
        return gson.fromJson((JsonElement) data, MultiViewSegmentResult.class);
    }

    /**
     * Automatically merges all adjacent connected components.
     */
    public MultiViewSegmentResult autoMergeComponent(String clientId, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("client_id", clientId);

        Object data = http.post("openapi/weaver/component/auto_merge", body, rtx);
        if (data == null) {
            return null;
        }
        return gson.fromJson((JsonElement) data, MultiViewSegmentResult.class);
    }

    /**
     * Adjusts a component boundary via a painted mask region.
     */
    public OperatorResult boundaryAdjust(String clientId, SegmentViewType viewType, int componentLabel, String paintMask, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("view_type", viewType.getValue());
        body.put("paint_mask", paintMask);
        body.put("component_label", componentLabel);

        Object data = http.post("openapi/weaver/component/boundary_adjust", body, rtx);
        if (data == null) {
            return null;
        }
        return gson.fromJson((JsonElement) data, OperatorResult.class);
    }

    /**
     * Renames a component, syncing across all views in the four-view stage.
     */
    public MultiViewSegmentResult renameComponent(String clientId, SegmentViewType viewType, int componentLabel, String newName, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("view_type", viewType.getValue());
        body.put("component_label", componentLabel);
        body.put("new_name", newName);

        Object data = http.post("openapi/weaver/component/part_rename", body, rtx);
        if (data == null) {
            return null;
        }
        return gson.fromJson((JsonElement) data, MultiViewSegmentResult.class);
    }

    /**
     * Persists the current segmentation result as a standalone 2D split asset (node_type=14).
     */
    public ModelInfo saveSegment(String clientId, String name, String algorithmModel, String openedModelId, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("name", name);
        body.put("algorithm_model", algorithmModel);
        if (openedModelId != null && !openedModelId.isEmpty()) {
            body.put("opened_model_id", openedModelId);
        }

        Object data = http.post("openapi/weaver/component/save_segment", body, rtx);
        if (data == null) {
            return null;
        }
        return gson.fromJson((JsonElement) data, ModelInfo.class);
    }

    /**
     * Opens a saved 2D split asset for re-editing, returning a new client_id.
     */
    public MultiViewSegmentResult openSegment(String modelId, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("model_id", modelId);

        Object data = http.post("openapi/weaver/component/open_segment", body, rtx);
        if (data == null) {
            return null;
        }
        return gson.fromJson((JsonElement) data, MultiViewSegmentResult.class);
    }

    /**
     * Regenerates a model asset in place.
     * Only 2UV assets (node_type=15, AUTO_LUV) are supported; other node types
     * will be rejected by the server. Regeneration does not return a new model_id —
     * it overwrites in place and increments redo_count.
     */
    public void regenerateModel(String modelId, Map<String, Object> params, String rtx) throws WeaverError {
        Map<String, Object> body = new HashMap<>();
        body.put("model_id", modelId);
        if (params != null) {
            body.put("params", params);
        }

        http.post("openapi/weaver/resource/regenerate_model", body, rtx);
    }

    /**
     * Result wrapper for model list queries
     */
    public static class ModelListResult {
        private final List<ModelInfo> models;
        private final int totalCount;

        public ModelListResult(List<ModelInfo> models, int totalCount) {
            this.models = models;
            this.totalCount = totalCount;
        }

        public List<ModelInfo> getModels() {
            return models;
        }

        public int getTotalCount() {
            return totalCount;
        }
    }
}
