package com.visvise.sdk.options;

import com.visvise.sdk.enums.FaceType;
import com.visvise.sdk.enums.ModelFormat;
import com.visvise.sdk.models.ImageGen360ExtraReference;
import com.visvise.sdk.models.ImageGen360Revision;

import java.util.List;

/**
 * Gen360Options defines optional parameters for Gen360
 */
public class Gen360Options {
    /** optional, task name (auto-generated if empty) */
    private String name = "gen_360";
    /** optional, algorithm model name; auto-selected if empty */
    private String algorithmModel;
    /** optional, output format (default fbx) */
    private ModelFormat modelFormat = ModelFormat.FBX;
    /** optional, face type (default triangle) */
    private FaceType faceType = FaceType.TRIANGLE;
    /** optional, enable A-Pose */
    private Boolean enableAPose;
    /** optional, style type (VISVISE proprietary models only)  灰模/超写实/Q版卡通/像素风格 */
    private String style;
    /** optional, back view to improve quality */
    private Object backView;
    /** optional, left view */
    private Object leftView;
    /** optional, right view */
    private Object rightView;
    /** optional, 文本提示词 */
    private String prompt;
    /** optional, 是否开启脸部增强 */
    private Boolean enableHeadRefinement;
    /** optional, 画面比例，如 1:1 / 16:9 */
    private String aspectRatio;
    /** optional, 是否开启去背景 */
    private Boolean enableBackgroundRemoval;
    /** optional, 随机种子（不传则由算法随机生成） */
    private Long seed;
    /** optional, 是否矫正透视 */
    private Boolean enable15Deg;
    /** optional, 参考图列表，图片在 gen360 中自动上传 */
    private List<ImageGen360ExtraReference> extraReferences;
    /** optional, 续改参数，views 图片在 gen360 中自动上传 */
    private ImageGen360Revision revision;

    public Gen360Options() {
    }

    public String getName() {
        return name;
    }

    public Gen360Options setName(String name) {
        this.name = name;
        return this;
    }

    public String getAlgorithmModel() {
        return algorithmModel;
    }

    public Gen360Options setAlgorithmModel(String algorithmModel) {
        this.algorithmModel = algorithmModel;
        return this;
    }

    public ModelFormat getOutputModelFormat() {
        return modelFormat;
    }

    public Gen360Options setOutputModelFormat(ModelFormat modelFormat) {
        this.modelFormat = modelFormat;
        return this;
    }

    public FaceType getFaceType() {
        return faceType;
    }

    public Gen360Options setFaceType(FaceType faceType) {
        this.faceType = faceType;
        return this;
    }

    public Boolean getEnableAPose() {
        return enableAPose;
    }

    public Gen360Options setEnableAPose(Boolean enableAPose) {
        this.enableAPose = enableAPose;
        return this;
    }

    public String getStyle() {
        return style;
    }

    public Gen360Options setStyle(String style) {
        this.style = style;
        return this;
    }

    public Object getBackView() {
        return backView;
    }

    public Gen360Options setBackView(Object backView) {
        this.backView = backView;
        return this;
    }

    public Object getLeftView() {
        return leftView;
    }

    public Gen360Options setLeftView(Object leftView) {
        this.leftView = leftView;
        return this;
    }

    public Object getRightView() {
        return rightView;
    }

    public Gen360Options setRightView(Object rightView) {
        this.rightView = rightView;
        return this;
    }

    public String getPrompt() {
        return prompt;
    }

    public Gen360Options setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public Boolean getEnableHeadRefinement() {
        return enableHeadRefinement;
    }

    public Gen360Options setEnableHeadRefinement(Boolean enableHeadRefinement) {
        this.enableHeadRefinement = enableHeadRefinement;
        return this;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public Gen360Options setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
        return this;
    }

    public Boolean getEnableBackgroundRemoval() {
        return enableBackgroundRemoval;
    }

    public Gen360Options setEnableBackgroundRemoval(Boolean enableBackgroundRemoval) {
        this.enableBackgroundRemoval = enableBackgroundRemoval;
        return this;
    }

    public Long getSeed() {
        return seed;
    }

    public Gen360Options setSeed(Long seed) {
        this.seed = seed;
        return this;
    }

    public Boolean getEnable15Deg() {
        return enable15Deg;
    }

    public Gen360Options setEnable15Deg(Boolean enable15Deg) {
        this.enable15Deg = enable15Deg;
        return this;
    }

    public List<ImageGen360ExtraReference> getExtraReferences() {
        return extraReferences;
    }

    public Gen360Options setExtraReferences(List<ImageGen360ExtraReference> extraReferences) {
        this.extraReferences = extraReferences;
        return this;
    }

    public ImageGen360Revision getRevision() {
        return revision;
    }

    public Gen360Options setRevision(ImageGen360Revision revision) {
        this.revision = revision;
        return this;
    }

    public static Gen360Options create() {
        return new Gen360Options();
    }
}
