package com.skypyb.sc.controller;

import com.skypyb.sc.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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
    private LoadBalancerClient loadBalancerClient;


    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {

        //获取 user 服务的信息,使用到了 Ribbon 实现负载均衡
        ServiceInstance choose = loadBalancerClient.choose("sc-demo-microservice-user");

        String userUrl = choose.getUri().toString();

        logger.info("access getUser method , service info: {},{}", choose.getServiceId(), userUrl);
        return restTemplate.getForObject(userUrl + "/user/" + id, User.class);
    }
}
