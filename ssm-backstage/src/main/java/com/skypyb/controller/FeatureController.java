package com.skypyb.controller;

import com.skypyb.entity.Feature;
import com.skypyb.service.FeatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feature")
public class FeatureController {

    private Logger logger = LoggerFactory.getLogger(FeatureController.class);


    @Autowired
    private FeatureService featureService;

    @GetMapping("/authorityId/{authorityId}")
    public List<Feature> getFeature(@PathVariable("authorityId") Long authorityId) {
        return featureService.getByAuthorityId(authorityId);
    }

    @DeleteMapping("/{id}")
    public String deleteFeature(@PathVariable("id") Long id) {

        logger.info("Access AuthorityController.deleteFeature() id={}", id);
        featureService.delete(id);
        return "success";
    }

    @PutMapping("/{name}")
    public String addFeature(@PathVariable("name") String name) {

        logger.info("Access AuthorityController.addFeature() name={}", name);


        if (name != null && !name.trim().equals(""))
            featureService.add(name);

        return "success";
    }

}
