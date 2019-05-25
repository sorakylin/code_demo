package com.skypyb.sc.config;

import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignConfiguration {


    @Bean
    public Logger.Level feignLog() {
        return Logger.Level.FULL;
    }

    /**
     * 使用指定的用户名和密码验证所有请求
     * @return
     */
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
        return new BasicAuthRequestInterceptor("user","614");
    }

}
