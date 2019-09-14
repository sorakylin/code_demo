package com.skypyb.provider.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Feign客户端
 * 指定调用 sc-demo-alibaba-provider 的服务
 */
@FeignClient(value = "sc-demo-alibaba-provider")
public interface NacosHelloFeign {

    @RequestMapping(value = "/provider/hello/{msg}")
    String hello(@PathVariable("msg") String msg);
}
