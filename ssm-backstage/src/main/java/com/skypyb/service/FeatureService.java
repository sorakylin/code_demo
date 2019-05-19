package com.skypyb.service;

import com.skypyb.dao.AuthorityDao;
import com.skypyb.dao.FeatureDao;
import com.skypyb.entity.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureService {

    @Autowired
    private FeatureDao featureDao;
    @Autowired
    private AuthorityDao authorityDao;

    public List<Feature> getByAuthorityId(Long authorityId) {
        return featureDao.getByAuthorityId(authorityId);
    }


    public List<Feature> findAll() {
        return featureDao.findAll();
    }

    public void delete(Long featureId) {
        featureDao.delete(featureId);
        authorityDao.deleteAtfByFid(featureId);
    }

    public void add(String featureName) {
        featureDao.add(featureName);
    }
}
