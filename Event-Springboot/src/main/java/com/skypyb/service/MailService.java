package com.skypyb.service;

import com.skypyb.event.UserRegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 假设这是个发邮件的服务
 */
@Service
public class MailService {
    private static Logger logger = LoggerFactory.getLogger(MailService.class);

    /*
    public void send(Mail mail) {
        ...
    }*/


    /**
     * 监听用户注册，发送注册成功邮件。
     *
     * @param event
     */
    @EventListener
    @Async
    public void userRegister(UserRegisterEvent event) {
        //send(xxx);

        logger.info("Send a registration success email to the user! user name : {}",
                event.getUser().getUserName());
    }


}
