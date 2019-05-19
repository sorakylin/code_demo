package com.skypyb.controller;

import com.skypyb.entity.Authority;
import com.skypyb.entity.AuthorityToFeature;
import com.skypyb.entity.Feature;
import com.skypyb.service.AuthorityService;
import com.skypyb.service.FeatureService;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authority")
public class AuthorityController {

    private Logger logger = LoggerFactory.getLogger(AuthorityController.class);

    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private FeatureService featureService;


    @GetMapping("/atf/{id}")
    public AuthorityToFeature getAtf(@PathVariable("id") Long id) {

        logger.info("Access AuthorityController.getAtf() id={}", id);
        return authorityService.findAtfById(id);
    }

    @GetMapping("/{id}")
    public Authority getAuthority(@PathVariable("id") Long id) {

        logger.info("Access AuthorityController.getAuthority() id={}", id);
        return authorityService.getById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteAuthority(@PathVariable("id") Long id) {

        logger.info("Access AuthorityController.deleteAuthority() id={}", id);
        authorityService.delete(id);

        return "success";
    }

    @PutMapping("/{name}")
    public String addAuthority(@PathVariable("name") String name) {

        logger.info("Access AuthorityController.addAuthority() name={}", name);


        if (name != null && !name.trim().equals(""))
            authorityService.add(name);

        return "success";
    }


    /**
     * 查所有的权限，用于展示
     *
     * @return
     */
    @GetMapping("/all")
    public List<Authority> getAllAuthority() {

        logger.info("Access AuthorityController.getAllAuthority()");
        return authorityService.findAll();
    }

    /**
     * 修改权限对应的功能
     *
     * @param atfMap k:featureName  v: isChecked
     * @param map
     */
    @PostMapping("/atf")
    public void updateAtf(@RequestParam Map<String, Object> atfMap, Map map) {


        Long authorityId = Long.parseLong(atfMap.get("authorityId").toString());
        logger.info("Access AuthorityController.updateAtf() authorityId:{}", authorityId);

        atfMap.remove("authorityId");

        authorityService.deleteAtfByAid(authorityId);

        List<Feature> features = featureService.findAll();

        atfMap.entrySet().stream()
                .filter(en -> Boolean.valueOf(String.valueOf(en.getValue())))//只留下选中的
                .map(en -> en.getKey())
                .forEach(fName -> {
                    for (Feature feature : features) {
                        if (fName.equals(feature.getFeatureName())) {
                            authorityService.insertAtf(authorityId, feature.getFeatureId());
                        }
                    }
                });

    }


}
