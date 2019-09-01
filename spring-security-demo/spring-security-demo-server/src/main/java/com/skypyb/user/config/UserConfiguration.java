package com.skypyb.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.skypyb.user.dao")
public class UserConfiguration {
}
