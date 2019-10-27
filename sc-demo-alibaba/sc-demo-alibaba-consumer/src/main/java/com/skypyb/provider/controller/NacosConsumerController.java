package com.skypyb.provider.controller;

import com.skypyb.provider.feign.NacosHelloFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RequestMapping("/consumer")
@RestController
public class NacosConsumerController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;


    @Resource
    private NacosHelloFeign nacosHelloFeign;

    @GetMapping(value = "/hello/{msg}")
    public String hello(@PathVariable("msg") String msg) {
        //使用 LoadBalanceClient 和 RestTemplate 结合的方式来访问
        ServiceInstance serviceInstance = loadBalancerClient.choose("sc-demo-alibaba-provider");
        String url = String.format("http://%s:%s/provider/hello/%s",
                serviceInstance.getHost(), serviceInstance.getPort(), msg);
        return restTemplate.getForObject(url, String.class);
    }


    @GetMapping(value = "/hello/feign/{msg}")
    public String helloFeign(@PathVariable("msg") String msg) {
        return nacosHelloFeign.hello(msg);
    }

}
