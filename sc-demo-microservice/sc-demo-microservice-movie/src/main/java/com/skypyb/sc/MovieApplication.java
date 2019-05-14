package com.skypyb.sc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MovieApplication.class);
        application.run(args);
    }
}
