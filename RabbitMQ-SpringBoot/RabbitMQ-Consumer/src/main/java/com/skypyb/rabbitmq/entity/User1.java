package com.skypyb.rabbitmq.entity;

import java.io.Serializable;

public class User1 implements Serializable{

    private Long id;
    private String name;
    private String messageId;//储存消息发送的唯一标识


    public User1() {
    }

    public User1(Long id, String name, String messageId) {
        this.id = id;
        this.name = name;
        this.messageId = messageId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
