package com.skypyb.sc.controller;

import com.google.common.collect.Maps;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.skypyb.sc.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * 请求聚合
 * 客户端只需要发送一个请求到 zuul server ,由 zuul 帮忙将多个请求的数据合并
 */
@RestController
@RequestMapping("/aggregate")
public class AggregationController {

    private Logger logger = LoggerFactory.getLogger(AggregationController.class);

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fullbackUserAndMovieUser")
    @GetMapping("/user/user_movie/{id}")
    public Map<String, User> userAndMovieUser(@PathVariable Long id) {
        User user = restTemplate.getForObject("http://sc-demo-microservice-user/user/{id}", User.class, id);
        User movieUser = restTemplate.getForObject("http://sc-demo-microservice-movie/movie/user/{id}", User.class, id);

        Map map = Maps.newHashMap();
        map.put("user", user);
        map.put("movieUser", movieUser);
        return map;
    }

    //异常回退
    public Map<String, User> fullbackUserAndMovieUser(Long id) {
        return Collections.EMPTY_MAP;
    }
}
