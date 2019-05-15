package com.skypyb.sc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //表示这个服务是一个 Eureka 服务注册中心
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(EurekaServerApplication.class);
        application.run(args);
    }

}
