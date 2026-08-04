package com.visvise.sdk.models;

import com.visvise.sdk.enums.StyleType;

import java.util.HashMap;
import java.util.Map;

/**
 * StyleParam represents a stylized image result used to create a 2D preprocess asset.
 */
public class StyleParam {
    private StyleType styleType;
    private String resultImage;

    public StyleParam() {
    }

    public StyleParam(StyleType styleType, String resultImage) {
        this.styleType = styleType;
        this.resultImage = resultImage;
    }

    public StyleType getStyleType() {
        return styleType;
    }

    public void setStyleType(StyleType styleType) {
        this.styleType = styleType;
    }

    public String getResultImage() {
        return resultImage;
    }

    public void setResultImage(String resultImage) {
        this.resultImage = resultImage;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (styleType != null) {
            map.put("style_type", styleType.getValue());
        }
        map.put("result_image", resultImage);
        return map;
    }
}
