package com.skypyb.service;


import com.skypyb.entity.User;
import com.skypyb.event.UserRegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private ApplicationContext context;


    public void register(User user) {
        //repository.save(user)
        logger.info("User register success! user :{}",user);

        context.publishEvent(new UserRegisterEvent(this, user));
    }

}
