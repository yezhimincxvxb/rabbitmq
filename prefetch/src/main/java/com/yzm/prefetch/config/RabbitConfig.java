package com.yzm.prefetch.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String WORK_QUEUE = "work-queue";
    public static final String PREFETCH_ONE = "prefetchOne";

    @Bean
    public Queue queue() {
        return new Queue(WORK_QUEUE);
    }

    @Bean(name = PREFETCH_ONE)
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer> prefetchOneRabbitListenerContainerFactory(ConnectionFactory rabbitConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(rabbitConnectionFactory);
        factory.setPrefetchCount(1);
        return factory;
    }

}
