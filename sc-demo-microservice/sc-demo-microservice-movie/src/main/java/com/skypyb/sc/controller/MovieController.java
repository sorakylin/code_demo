package com.skypyb.sc.controller;

import com.skypyb.sc.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {

        //获取 user 服务的信息
        List<ServiceInstance> instances =
                discoveryClient.getInstances("sc-demo-microservice-user");

        String userUrl = instances.get(0).getUri().toString();

        logger.info("access getUser method.");
        return restTemplate.getForObject(userUrl + "/user/" + id, User.class);
    }
}
