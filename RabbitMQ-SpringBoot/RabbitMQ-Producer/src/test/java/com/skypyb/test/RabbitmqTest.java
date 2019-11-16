package com.skypyb.test;

import com.skypyb.rabbitmq.Application;
import com.skypyb.rabbitmq.entity.User1;
import com.skypyb.rabbitmq.producer.User1Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
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
        rabbitTemplate.convertAndSend("skypyb-ordinary-exchange",
                "skypyb.key.ordinary.one", "测试私信~~~");
        System.out.println("消息发送完毕~~~");
    }

}
