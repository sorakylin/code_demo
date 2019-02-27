package com.skypyb.listener;

import com.skypyb.entity.User;
import com.skypyb.event.UserRegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 用户注册完了,要初始化一些数据嘛
 * 这是监听用户注册的初始化方法
 */
@Component
public class InitializationListener implements ApplicationListener<UserRegisterEvent> {

    private static Logger logger = LoggerFactory.getLogger(InitializationListener.class);

    @Override
    @Async
    public void onApplicationEvent(UserRegisterEvent event) {
        User user = event.getUser();
        //init(user)
        logger.info("User initialization completed.");
    }


}
