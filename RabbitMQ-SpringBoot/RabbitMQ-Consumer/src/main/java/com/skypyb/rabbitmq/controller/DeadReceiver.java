package com.skypyb.rabbitmq.controller;

import com.rabbitmq.client.Channel;
import com.skypyb.rabbitmq.config.RabbitBindConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@RabbitListener(queues = {RabbitBindConfig.SKYPYB_DEAD_QUEUE})
@Component
public class DeadReceiver {

    private Logger logger = LoggerFactory.getLogger(DeadReceiver.class);

    @RabbitHandler
    public void onUser1Message(@Payload String messsage,
                               @Headers Map<String, Object> headers,
                               Channel channel) throws IOException {

        logger.info("死信队列接收消息: {}", messsage);

        //delivery tag可以从headers中get出来
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        try {
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            boolean redelivered = (boolean) headers.get(AmqpHeaders.REDELIVERED);
            channel.basicNack(deliveryTag, false, !redelivered);
        }


    }
}
