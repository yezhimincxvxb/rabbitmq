package com.yzm.rabbitmq_06.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    // 死信队列
    public static final String DL_QUEUE = "dl_queue2";
    public static final String DL_EXCHANGE = "dl.exchange";
    public static final String DL_KEY = "dl.key2";

    @Bean
    public Queue dlQueue() {
        return QueueBuilder.durable(DL_QUEUE).build();
    }

    @Bean
    public DirectExchange dlExchange() {
        return ExchangeBuilder.directExchange(DL_EXCHANGE).build();
    }

    @Bean
    public Binding dlBinding() {
        return BindingBuilder.bind(dlQueue()).to(dlExchange()).with(DL_KEY);
    }

    // ----------------------------------------------------------------------------------------------------------

    public static final String NORMAL_EXCHANGE = "normal.exchange";
    public static final String NORMAL_QUEUE2 = "normal_queue2";
    public static final String NORMAL_KEY2 = "normal.key2";
    public static final String NORMAL_QUEUE3 = "normal_queue3";
    public static final String NORMAL_KEY3 = "normal.key3";

    @Bean
    public DirectExchange exchange() {
        return ExchangeBuilder.directExchange(NORMAL_EXCHANGE).build();
    }

    // 正常队列2，添加配置
    @Bean
    public Queue queue2() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", DL_EXCHANGE);//声明当前队列绑定的死信交换机
        params.put("x-dead-letter-routing-key", DL_KEY);//声明当前队列的死信路由键
        // 添加消息过期时间，10秒内还没处理完，就转发给死信队列
        params.put("x-message-ttl", 10000);
        return QueueBuilder.durable(NORMAL_QUEUE2).withArguments(params).build();
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(queue2()).to(exchange()).with(NORMAL_KEY2);
    }

    // 正常队列3，添加配置
    @Bean
    public Queue queue3() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", DL_EXCHANGE);//声明当前队列绑定的死信交换机
        params.put("x-dead-letter-routing-key", DL_KEY);//声明当前队列的死信路由键
        // 添加消息过期时间，10秒内还没处理完，就转发给死信队列
        // params.put("x-message-ttl", 10000);
        return QueueBuilder.durable(NORMAL_QUEUE3).withArguments(params).build();
    }

    @Bean
    public Binding binding3() {
        return BindingBuilder.bind(queue3()).to(exchange()).with(NORMAL_KEY3);
    }
}
