package com.skypyb.sc.controller;

import com.skypyb.sc.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        logger.info("access getUser method.");
        return restTemplate.getForObject("http://localhost:8088/user/" + id, User.class);
    }
}
