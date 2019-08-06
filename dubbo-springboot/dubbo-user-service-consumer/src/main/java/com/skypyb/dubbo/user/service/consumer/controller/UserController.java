package com.skypyb.dubbo.user.service.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.skypyb.dubbo.service.user.api.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Reference(version = "${user.service.version}")
    private UserService userService;

    @GetMapping(value = "hello")
    public String hello() {
        return userService.hello();
    }
}
