package com.skypyb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SecurityServerApplication.class);
        application.run(args);

    }
}
