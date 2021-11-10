package com.yzm.rabbitmq03.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

//@Configuration
@EnableScheduling
public class DeadLetterRabbitConfig {

    // 死信队列、死信交换机
    public static final String DL_QUEUE = "dl-queue";
    public static final String DL_EXCHANGE = "dl.exchange";
    public static final String DL_KEY = "dl.key";

    @Bean
    public Queue dlQueue() {
        return QueueBuilder.durable(DL_QUEUE).build();
    }

    @Bean
    public DirectExchange dlExchange() {
        return ExchangeBuilder.directExchange(DL_EXCHANGE).build();
    }

    // 绑定死信队列到死信交换机
    @Bean
    public Binding dlBinding() {
        return BindingBuilder.bind(dlQueue()).to(dlExchange()).with(DL_KEY);
    }

    // ----------------------------------------------------------------------------------------------------------

    public static final String NORMAL_QUEUE = "normal-queue";
    public static final String NORMAL_EXCHANGE = "normal.exchange";
    public static final String NORMAL_KEY = "normal.key";


    // 正常队列，添加配置
    @Bean
    public Queue queue() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", DL_EXCHANGE);//声明当前队列绑定的死信交换机
        params.put("x-dead-letter-routing-key", DL_KEY);//声明当前队列的死信路由键
        return QueueBuilder.durable(NORMAL_QUEUE).withArguments(params).build();
    }

    @Bean
    public DirectExchange exchange() {
        return ExchangeBuilder.directExchange(NORMAL_EXCHANGE).build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(NORMAL_KEY);
    }

    // ----------------------------------------------------------------------------------------------------------

    public static final String NORMAL_QUEUE2 = "normal-queue2";
    public static final String NORMAL_KEY2 = "normal.key2";

    // 延时队列，添加配置
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

}
