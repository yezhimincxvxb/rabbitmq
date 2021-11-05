package com.yzm.rabbitmq03.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.ImmediateRequeueMessageRecoverer;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class RetryRabbitConfig {

    /**
     * 重试5次之后，返回队列，然后再重试5次，周而复始直到不抛出异常为止，这样还是会影响后续的消息消费。
     */
//    @Bean
    public MessageRecoverer messageRecoverer() {
        return new ImmediateRequeueMessageRecoverer();
    }


    public static final String RETRY_QUEUE = "retry-error";
    public static final String RETRY_EXCHANGE = "retry.exchange";
    public static final String RETRY_KEY = "retry-key";

    /**
     * 消息重试5次以后直接以新的routingKey发送到了配置的交换机中，
     * 此时再查看监控页面，可以看原始队列中已经没有消息了，但是配置的异常队列中存在一条消息
     */
//    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate, RETRY_EXCHANGE, RETRY_KEY);
    }

}