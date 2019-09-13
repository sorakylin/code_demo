package com.skypyb.provider.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/provider")
public class NacosProviderController {


    @RequestMapping(value = "/hello/{msg}")
    public String hello(@PathVariable("msg") String msg) {
        return "Hello " + msg;
    }
}
