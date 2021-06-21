package com.yzm.retry.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.ImmediateRequeueMessageRecoverer;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_ERR = "retry-err";
    public static final String ERR_EXCHANGE = "err.exchange";
    public static final String ERR_KEY = "retry-err-key";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_ERR);
    }

    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(ERR_EXCHANGE).build();
    }

    @Bean
    public Binding bindingA() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(ERR_KEY);
    }

    /**
     * 消息重试5次以后直接以新的routingKey发送到了配置的交换机中，
     * 此时再查看监控页面，可以看原始队列中已经没有消息了，但是配置的异常队列中存在一条消息
     */
//    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate, ERR_EXCHANGE, ERR_KEY);
    }

    //==========================================================================================

    /**
     * 重试5次之后，返回队列，然后再重试5次，周而复始直到不抛出异常为止，这样还是会影响后续的消息消费。
     */
//    @Bean
    public MessageRecoverer messageRecoverer() {
        return new ImmediateRequeueMessageRecoverer();
    }

    //==========================================================================================

    /**
     * 死信队列
     * 创建业务队列时绑定死信队列
     * 当业务队列消费发送异常时，直接将消息转移到死信队列中，不会触发重试机制
     */
    public static final String DLX_QUEUE = "dlx-queue";
    public static final String DLX_EXCHANGE = "dlx.exchange";
    public static final String DLX_KEY = "dlx.key";

    @Bean
    public Queue queueDlX() {
        return new Queue(DLX_QUEUE);
    }

    @Bean
    public DirectExchange exchangeDLX() {
        return ExchangeBuilder.directExchange(DLX_EXCHANGE).build();
    }

    @Bean
    public Binding bindingDLX() {
        return BindingBuilder.bind(queueDlX()).to(exchangeDLK()).with(DLX_KEY);
    }

    //业务队列
    public static final String NORMAL_QUEUE = "dlk-queue";
    public static final String NORMAL_EXCHANGE = "dlk.exchange";
    public static final String NORMAL_KEY = "dlk.key";

    @Bean
    public Queue queueDlK() {
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", DLX_EXCHANGE);//声明当前队列绑定的死信交换机
        params.put("x-dead-letter-routing-key", DLX_KEY);//声明当前队列的死信路由键
        return QueueBuilder.durable(NORMAL_QUEUE).withArguments(params).build();
    }

    @Bean
    public DirectExchange exchangeDLK() {
        return ExchangeBuilder.directExchange(NORMAL_EXCHANGE).build();
    }

    @Bean
    public Binding bindingDLK() {
        return BindingBuilder.bind(queueDlX()).to(exchangeDLK()).with(NORMAL_KEY);
    }

}
