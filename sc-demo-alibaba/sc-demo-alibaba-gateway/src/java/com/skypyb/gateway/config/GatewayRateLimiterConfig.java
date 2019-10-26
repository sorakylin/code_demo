package com.skypyb.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayRateLimiterConfig {

    /**
     * Gateway通过内置的RequestRateLimiter过滤器实现限流，用的是令牌桶算法，借助Redis保存中间数据
     * 这里自定义一个KeyResolver
     * 作用是对来源ip进行限流
     */
    @Bean(value = "ipKeyResolver")
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
