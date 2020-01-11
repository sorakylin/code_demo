package com.skypyb.test;

import com.skypyb.rabbitmq.Application;
import com.skypyb.rabbitmq.entity.User1;
import com.skypyb.rabbitmq.producer.User1Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RabbitmqTest {

    @Autowired
    private User1Sender userSender;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    Logger logger = LoggerFactory.getLogger(RabbitmqTest.class);

    @Test
    public void testSend1() {

        for (long i = 0; i < 10; i++) {
            User1 user1 = new User1();

            user1.setId(i);
            user1.setName("测试用户" + i);
            user1.setMessageId("user1$" + System.currentTimeMillis() + "$" + UUID.randomUUID().toString());

            userSender.send(user1);
        }
    }


    @Test
    public void testDead() {

        //单位毫秒
        String ttl = "3000";

        MessagePostProcessor msgProcessor = (Message msg) -> {
            msg.getMessageProperties().setExpiration(ttl);
            return msg;
        };


        rabbitTemplate.convertAndSend("skypyb-ordinary-exchange",
                "skypyb.key.ordinary.one", "死信 -- 指定时间:" + ttl, msgProcessor);

        rabbitTemplate.convertAndSend("skypyb-ordinary-exchange",
                "skypyb.key.ordinary.one", "死信 -- 默认值");


        logger.info("-----消息发送完毕-----");
    }

}
