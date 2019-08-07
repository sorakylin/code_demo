package com.skypyb.dubbo.user.service.provider;

import com.alibaba.dubbo.container.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableHystrix
@SpringBootApplication
public class DubboUserServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboUserServiceProviderApplication.class, args);
        Main.main(args);
    }

}
