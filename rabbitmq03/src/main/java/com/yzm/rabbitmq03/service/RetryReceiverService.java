package com.yzm.rabbitmq03.service;

import com.yzm.rabbitmq03.config.RetryRabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RetryReceiverService {

    private int count = 1;

    /**
     * 可以看到重试次数是5次（包含自身消费的一次），重试时间依次是2s，4s，8s，10s（上一次间隔时间*间隔时间乘子），
     * 最后一次重试时间理论上是16s，但是由于设置了最大间隔时间是10s，因此最后一次间隔时间只能是10s，和配置相符合。
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "retry-a"),
            exchange = @Exchange(value = "retry.exchange", type = ExchangeTypes.TOPIC),
            key = {"topic.*.retry"}
    ))
    public void retry(Message message) {
        log.info(" [ 消费者@A号 ] 接收到消息 ==> '" + new String(message.getBody()));
        log.info("当前执行次数：{}", count++);
        // 制造异常
        int i = 1 / 0;
        log.info(" [ 消费者@A号 ] 消费了消息 ==> '" + new String(message.getBody()));
    }

    // 异常队列
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RetryRabbitConfig.RETRY_QUEUE),
            exchange = @Exchange(value = RetryRabbitConfig.RETRY_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {RetryRabbitConfig.RETRY_KEY}
    ))
    public void error(Message message) {
        log.info(" [ 消费者@异常号 ] 接收到消息 ==> '" + new String(message.getBody()));
    }

}
