package com.skypyb.test;

import com.skypyb.rabbitmq.Application;
import com.skypyb.rabbitmq.config.RabbitBindConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RabbitmqTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private Logger logger = LoggerFactory.getLogger(RabbitmqTest.class);

    @Test
    public void testDead() {

        rabbitTemplate.convertAndSend(RabbitBindConfig.SKYPYB_ORDINARY_EXCHANGE,
                RabbitBindConfig.SKYPYB_ORDINARY_KEY, "消息体");

        rabbitTemplate.convertAndSend(
                RabbitBindConfig.SKYPYB_ORDINARY_EXCHANGE,
                RabbitBindConfig.SKYPYB_ORDINARY_KEY,
                "消息体",
                (msg) -> {
                    msg.getMessageProperties().setExpiration("3000");
                    return msg;
                });

        logger.info("-----消息发送完毕-----");
    }

}
