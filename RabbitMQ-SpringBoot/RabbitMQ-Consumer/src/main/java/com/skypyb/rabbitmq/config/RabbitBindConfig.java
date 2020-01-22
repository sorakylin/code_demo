package com.skypyb.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Rabbitmq的绑定配置，绑定Exchange、MQ、RoutingKey
 * Create by skypyb on 2019-11-16
 */
@Configuration
public class RabbitBindConfig {

    public final static String SKYPYB_ORDINARY_EXCHANGE = "skypyb-ordinary-exchange";
    public final static String SKYPYB_DEAD_EXCHANGE = "skypyb-dead-exchange";
    public final static String SKYPYB_DELAY_EXCHANGE = "skypyb-delay-exchange";

    public final static String SKYPYB_ORDINARY_QUEUE_1 = "skypyb-ordinary-queue";
    public final static String SKYPYB_DEAD_QUEUE = "skypyb-dead-queue";
    public final static String SKYPYB_DELAY_QUEUE = "skypyb-delay-queue";

    public final static String SKYPYB_ORDINARY_KEY = "skypyb.key.ordinary.one";
    public final static String SKYPYB_DEAD_KEY = "skypyb.key.dead";
    public final static String SKYPYB_DELAY_KEY = "skypyb.key.delay";


    @Bean
    public DirectExchange ordinaryExchange() {
        return new DirectExchange(SKYPYB_ORDINARY_EXCHANGE, false, true);
    }

    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(SKYPYB_DEAD_EXCHANGE, false, true);
    }


    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        //自定义交换机
        return new CustomExchange(SKYPYB_DELAY_EXCHANGE, "x-delayed-message", false, true, args);
    }

    @Bean
    public Queue ordinaryQueue() {

        Map<String, Object> arguments = new HashMap<>();
        //TTL 5s
        arguments.put("x-message-ttl", 1000 * 5);
        //绑定死信队列和死信交换机
        arguments.put("x-dead-letter-exchange", SKYPYB_DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", SKYPYB_DEAD_KEY);

        return new Queue(SKYPYB_ORDINARY_QUEUE_1, false, false, true, arguments);
    }

    @Bean
    public Queue deadQueue() {
        return new Queue(SKYPYB_DEAD_QUEUE, false, false, true);
    }

    @Bean
    public Queue delayQueue() {
        return new Queue(SKYPYB_DELAY_QUEUE, false, false, true);
    }


    @Bean
    public Binding bindingOrdinaryExchangeAndQueue() {
        return BindingBuilder.bind(ordinaryQueue()).to(ordinaryExchange()).with(SKYPYB_ORDINARY_KEY);
    }

    @Bean
    public Binding bindingDeadExchangeAndQueue() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(SKYPYB_DEAD_KEY);
    }

    @Bean
    public Binding bindingDelayExchangeAndQueue() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(SKYPYB_DELAY_KEY).noargs();
    }
}
