package com.skypyb.sc.controller;

import com.skypyb.sc.entity.User;
import com.skypyb.sc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        logger.info("access getUser method.");
        return userService.getUser(id);
    }
}
