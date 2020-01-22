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

@RabbitListener(queues = {RabbitBindConfig.SKYPYB_DELAY_QUEUE})
@Component
public class DelayReceiver {

    private Logger logger = LoggerFactory.getLogger(DelayReceiver.class);

    @RabbitHandler
    public void onDelayMessage(@Payload String message,
                              @Headers Map<String, Object> headers,
                              Channel channel) throws IOException {

        logger.info("监听延时交换机, 收到消息: {}", message);

        //delivery tag可以从headers中get出来
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        try {
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            boolean redelivered = (boolean) headers.get(AmqpHeaders.REDELIVERED);
            channel.basicNack(deliveryTag, false, !redelivered);
        }


    }
}
