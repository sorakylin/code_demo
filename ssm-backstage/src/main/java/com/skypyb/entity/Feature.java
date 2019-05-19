package com.skypyb.entity;

import java.io.Serializable;

/**
 * 功能表
 */
public class Feature implements Serializable {

    private Long featureId;
    private String featureName;

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Feature{");
        sb.append("featureId=").append(featureId);
        sb.append(", featureName='").append(featureName).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
