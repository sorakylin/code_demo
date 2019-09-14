package com.skypyb.provider.fallback;

import com.skypyb.provider.feign.NacosHelloFeign;
import org.springframework.stereotype.Component;

/**
 * 熔断类
 * 要是 Feign 的接口调用失败(或者被快速失败)就会走这个类的方法进行处理
 */
@Component
public class HelloServiceFallback implements NacosHelloFeign {

    @Override
    public String hello(String msg) {
        return "触发熔断机制~";
    }
}
