package com.yzm.rabbitmq_07.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    // 普通交换机
    public static final String ORDINARY_EXCHANGE = "ordinary-exchange";
    public static final String ORDINARY_QUEUE = "ordinary-queue";
    // 备用交换机
    public static final String ALTERNATE_EXCHANGE = "alternate-exchange";
    public static final String ALTERNATE_QUEUE = "alternate-queue";

    @Bean
    public Queue alQueue() {
        return QueueBuilder.durable(ALTERNATE_QUEUE).build();
    }

    // 备用交换器一般都是设置FANOUT模式
    @Bean
    public FanoutExchange alExchange() {
        return ExchangeBuilder.fanoutExchange(ALTERNATE_EXCHANGE).build();
    }

    @Bean
    public Binding alBinding() {
        return BindingBuilder.bind(alQueue()).to(alExchange());
    }

    //-----------------------------------------------------------------------------------------

    @Bean
    public Queue orQueue() {
        return QueueBuilder.durable(ORDINARY_QUEUE).build();
    }

    // 普通交换机绑定备用交换机
    @Bean
    public DirectExchange orExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("alternate-exchange", ALTERNATE_EXCHANGE);
        return ExchangeBuilder.directExchange(ORDINARY_EXCHANGE).withArguments(args).build();
    }

    @Bean
    public Binding orBinding() {
        return BindingBuilder.bind(orQueue()).to(orExchange()).with("ordinary.yzm");
    }

}
