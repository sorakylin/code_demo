package com.skypyb.dubbo.user.service.provider.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.skypyb.dubbo.service.user.api.UserService;

@Service(version = "1.0.0")
public class UserServiceImpl implements UserService{

    @Override
    public String hello() {
        return null;
    }

}
