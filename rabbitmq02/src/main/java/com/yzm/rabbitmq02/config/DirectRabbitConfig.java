package com.yzm.rabbitmq02.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

//@Configuration
@EnableScheduling
public class DirectRabbitConfig {

    //队列
    public static final String QUEUE_C = "queue-c";
    public static final String QUEUE_D = "queue-d";

    //路由键
    public static final String DIRECT_C = "direct.yzm";
    public static final String DIRECT_D = "direct.admin";
    public static final String DIRECT_D2 = "direct.root";

    public static final String DIRECT_EXCHANGE = "direct.exchange";

    @Bean
    public Queue queueC() {
        return new Queue(QUEUE_C);
    }

    @Bean
    public Queue queueD() {
        return new Queue(QUEUE_D);
    }


    /**
     * 广播式交换机：fanout交换器中没有路由键的概念，他会把消息发送到所有绑定在此交换器上面的队列中。
     * 路由式交换机：direct交换器相对来说比较简单，匹配规则为：路由键完全匹配，消息就被投送到相关的队列
     * 主题式交换机：topic交换器采用模糊匹配路由键的原则进行转发消息到队列中
     */
    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(DIRECT_EXCHANGE).build();
    }

    @Bean
    public Binding bindingC() {
        return BindingBuilder.bind(queueC()).to(directExchange()).with(DIRECT_C);
    }

    // 队列D有两个路由键
    @Bean
    public Binding bindingD() {
        return BindingBuilder.bind(queueD()).to(directExchange()).with(DIRECT_D);
    }

    @Bean
    public Binding bindingD2() {
        return BindingBuilder.bind(queueD()).to(directExchange()).with(DIRECT_D2);
    }

}
