package com.skypyb.rabbitmq.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQCoreConfig {

    @Autowired
    private ConnectionFactory connectionFactory;


    @Bean(name = "prefetchCount5Container")
    public SimpleRabbitListenerContainerFactory prefetchCount5Container() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //并发数设置: 初始化线程3,最大并发5
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(3);

        ////限流，同一单位时间内只有5条消息消费
        factory.setConnectionFactory(connectionFactory);
        factory.setPrefetchCount(5);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        return factory;
    }
}
