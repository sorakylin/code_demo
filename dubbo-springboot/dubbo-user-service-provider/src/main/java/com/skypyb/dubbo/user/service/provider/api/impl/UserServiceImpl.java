package com.skypyb.dubbo.user.service.provider.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.skypyb.dubbo.service.user.api.UserService;

@Service(version = "${user.service.version}")
public class UserServiceImpl implements UserService {

    @Override
    public String hello() {
        return "hello dubbo";
    }

}
