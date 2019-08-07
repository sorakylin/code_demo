package com.skypyb.dubbo.user.service.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableHystrix
@SpringBootApplication
public class DubboUserServiceConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboUserServiceConsumerApplication.class, args);
	}

}
