package com.yzm.rabbitmq02.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

//@Configuration
@EnableScheduling
public class TopicRabbitConfig {

    //队列
    public static final String QUEUE_E = "queue-e";
    public static final String QUEUE_F = "queue-f";
    public static final String QUEUE_G = "queue-g";

    public static final String TOPIC_EXCHANGE = "topic.exchange";

    //两种特殊字符*与#，用于做模糊匹配，其中*用于匹配一个单词，#用于匹配多个单词（可以是零个）
    public static final String TOPIC_E = "topic.yzm.*";
    public static final String TOPIC_F = "topic.#";
    public static final String TOPIC_G = "topic.*.yzm";

    @Bean
    public Queue queueE() {
        return new Queue(QUEUE_E);
    }

    @Bean
    public Queue queueF() {
        return new Queue(QUEUE_F);
    }

    @Bean
    public Queue queueG() {
        return new Queue(QUEUE_G);
    }

    /**
     * 广播式交换机：fanout交换器中没有路由键的概念，他会把消息发送到所有绑定在此交换器上面的队列中。
     * 路由式交换机：direct交换器相对来说比较简单，匹配规则为：路由键匹配，消息就被投送到相关的队列
     * 主题式交换机：topic交换器采用模糊匹配路由键的原则进行转发消息到队列中
     */
    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE).build();
    }

    @Bean
    public Binding bindingE() {
        return BindingBuilder.bind(queueE()).to(topicExchange()).with(TOPIC_E);
    }

    @Bean
    public Binding bindingF() {
        return BindingBuilder.bind(queueF()).to(topicExchange()).with(TOPIC_F);
    }

    @Bean
    public Binding bindingG() {
        return BindingBuilder.bind(queueG()).to(topicExchange()).with(TOPIC_G);
    }
}
