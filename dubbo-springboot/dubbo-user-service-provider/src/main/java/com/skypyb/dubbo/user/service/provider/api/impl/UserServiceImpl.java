package com.skypyb.dubbo.user.service.provider.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.skypyb.dubbo.service.user.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@Service(version = "${user.service.version}")
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${dubbo.protocol.port}")
    private String port;

    @Override
    public String hello() {
        logger.info("access hello, service port:{}", this.port);
        return "hello dubbo";
    }

}
