package com.skypyb.demo.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(value = {"com.skypyb.demo.sharding.**.dao"})
public class ShardingJDBCApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJDBCApplication.class, args);
    }
}
