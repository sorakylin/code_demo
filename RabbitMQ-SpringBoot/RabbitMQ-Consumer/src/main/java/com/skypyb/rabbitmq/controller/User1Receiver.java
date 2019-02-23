package com.skypyb.rabbitmq.controller;

import com.rabbitmq.client.Channel;
import com.skypyb.rabbitmq.entity.User1;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class User1Receiver {

    /**
     * @param user1   消息体,使用 @Payload 注解
     * @param headers 消息头,使用 @Headers 注解
     * @param channel
     */
    /*@RabbitListener表示监听的具体队列.
        bindings属性代表绑定。里边有几个值填写,填写好绑定的队列名字和交换机名字
        指定好routingKey。若指定的这些参数不存在的话。则会自行给你创建好
        durable代表是否持久化
    */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "user1-queue", durable = "true"),
            exchange = @Exchange(name = "user1-exchange", durable = "true", type = "topic"),
            key = "user1.#"
    )
    )
    @RabbitHandler//标识这个方法用于消费消息
    public void onUser1Message(@Payload User1 user1,
                               @Headers Map<String, Object> headers,
                               Channel channel) throws IOException {
        //消费者操作
        System.out.println("-------收到消息辣！-----");
        System.out.println("发过来的用户名为:" + user1.getName());

        //delivery tag可以从消息头里边get出来
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        try{

            //basicAck()表示确认已经消费消息。通知一下mq,需要先得到 delivery tag
            channel.basicAck(deliveryTag, false);
        }catch (Exception e){

            boolean  redelivered = (boolean) headers.get(AmqpHeaders.REDELIVERED);
            //requeue参数:如果被拒绝的消息应该被重新排队而不是丢弃/死信，则为true,这个参数代表是否重新分发
            channel.basicNack(deliveryTag,false,!redelivered);
        }



    }

}
