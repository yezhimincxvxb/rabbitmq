//package com.yzm.ack.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitConfig {
//
//    public static final String QUEUE_A = "ack-a";
//
//    public static final String TOPIC_EXCHANGE = "topic.exchange";
//
//    @Bean
//    public Queue queueA() {
//        return new Queue(QUEUE_A);
//    }
//
//    @Bean
//    public TopicExchange topicExchange() {
//        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE).build();
//    }
//
//    @Bean
//    public Binding bindingA() {
//        return BindingBuilder.bind(queueA()).to(topicExchange()).with("topic.*.a");
//    }
//
//    @Bean
//    public Binding bindingA_2() {
//        return BindingBuilder.bind(queueA()).to(topicExchange()).with("topic.a.#*");
//    }
//
//}
