package com.skypyb.entity;

import java.util.List;

/**
 * 权限功能一对多模型
 */
public class AuthorityToFeature extends Authority {


    private List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
    
}
