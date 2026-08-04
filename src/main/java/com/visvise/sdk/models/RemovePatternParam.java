package com.visvise.sdk.models;

import java.util.HashMap;
import java.util.Map;

/**
 * RemovePatternParam represents a pattern-removed image result used to create a 2D preprocess asset.
 */
public class RemovePatternParam {
    private String resultImage;

    public RemovePatternParam() {
    }

    public RemovePatternParam(String resultImage) {
        this.resultImage = resultImage;
    }

    public String getResultImage() {
        return resultImage;
    }

    public void setResultImage(String resultImage) {
        this.resultImage = resultImage;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("result_image", resultImage);
        return map;
    }
}
