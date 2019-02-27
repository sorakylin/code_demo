package com.skypyb.event;

import com.skypyb.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 * 用户注册事件
 */
public class UserRegisterEvent extends ApplicationEvent {

    private User user;

    /**
     * 重写构造函数
     * @param source
     * @param user
     */
    public UserRegisterEvent(Object source, User user) {
        super(source);
        this.user = user;
    }


    public User getUser() {
        return user;
    }
}
