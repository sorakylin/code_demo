package com.skypyb.rabbitmq.producer;

import com.skypyb.rabbitmq.entity.User1;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class User1Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;//操作rabbitmq的模板


    /**
     * 回调函数,confirm 确认
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {

        /**
         *
         * @param correlationData   消息唯一ID
         * @param ack           确认消息是否被MQBroker接收,true是已被接收,false为没被接收/投递失败/异常/...
         * @param cause
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.out.println("CorrelationData:" + correlationData);

            if (ack) {
                System.out.println("消息已被消费~~~");
            } else {
                System.err.println("消息未被消费!!!");
            }
        }
    };

    public void send(User1 user1) {

        //这里是设置回调
        rabbitTemplate.setConfirmCallback(confirmCallback);

        //下面是发消息
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(user1.getMessageId());
        rabbitTemplate.convertAndSend(
                "user1-exchange",//exchange
                "user1.key1",//routingKey
                user1,//消息体内容
                correlationData//消息唯一ID
        );
    }


}
