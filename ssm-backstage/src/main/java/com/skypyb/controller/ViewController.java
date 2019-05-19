package com.skypyb.controller;

import com.skypyb.service.AuthorityService;
import com.skypyb.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class ViewController {

    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private FeatureService featureService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/layout", method = RequestMethod.GET)
    public String layout() {
        return "layout";
    }

    @RequestMapping(value = "/userManager", method = RequestMethod.GET)
    public String userManager() {
        return "userManager";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/authorityManager", method = RequestMethod.GET)
    public String authorityManager(Map map) {

        map.put("authorityList", authorityService.findAll());
        map.put("featureList", featureService.findAll());

        return "authorityManager";
    }

}

