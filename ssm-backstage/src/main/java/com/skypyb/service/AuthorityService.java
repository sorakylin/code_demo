package com.skypyb.service;

import com.skypyb.dao.AuthorityDao;
import com.skypyb.entity.Authority;
import com.skypyb.entity.AuthorityToFeature;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityDao authorityDao;

    public Authority getById(Long id) {
        return authorityDao.getById(id);
    }

    public Authority getByName(String name) {
        return authorityDao.getByName(name);
    }

    public List<Authority> findAll() {
        return authorityDao.findAll();
    }


    public AuthorityToFeature findAtfById(Long authorityId) {
        return authorityDao.findAtfById(authorityId);
    }

    public List<AuthorityToFeature> findAtfAll() {
        return authorityDao.findAtfAll();
    }


    public void delete(Long authorityId) {
        authorityDao.delete(authorityId);
        deleteAtfByAid(authorityId);
    }
    public void add(String authorityName) {
        authorityDao.add(authorityName);
    }




    public void deleteAtfByAid(Long authorityId) {
        authorityDao.deleteAtfByAid(authorityId);
    }


    public void insertAtf(Long authorityId, Long featureId) {
        authorityDao.insertAtf(authorityId, featureId);
    }
}
