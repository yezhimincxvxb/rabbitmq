package com.yzm.rabbitmq02.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableScheduling
public class HeaderRabbitConfig {

    public static final String QUEUE_X = "queue-x";
    public static final String QUEUE_Y = "queue-y";

    public static final String HEADER_EXCHANGE = "header.exchange";

    @Bean
    public Queue queueX() {
        return new Queue(QUEUE_X);
    }

    @Bean
    public Queue queueY() {
        return new Queue(QUEUE_Y);
    }

    @Bean
    public HeadersExchange headersExchange() {
        return ExchangeBuilder.headersExchange(HEADER_EXCHANGE).build();
    }

    @Bean
    public Binding bindingX() {
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("name", "yzm");
        // whereAll：表示完全匹配
        return BindingBuilder.bind(queueX()).to(headersExchange()).whereAll(map).match();
    }

    @Bean
    public Binding bindingY() {
        Map<String, Object> map = new HashMap<>();
        map.put("key2", "value2");
        map.put("name", "yzm");
        // whereAny：表示只要有一对键值对能匹配就可以
        return BindingBuilder.bind(queueY()).to(headersExchange()).whereAny(map).match();
    }

}
