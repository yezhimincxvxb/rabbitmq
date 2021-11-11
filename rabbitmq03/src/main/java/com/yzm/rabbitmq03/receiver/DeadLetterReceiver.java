package com.yzm.rabbitmq03.receiver;

import com.rabbitmq.client.Channel;
import com.yzm.rabbitmq03.config.DeadLetterRabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;

@Slf4j
//@Component
public class DeadLetterReceiver {

    private int count = 1;

    // 监听 normal-queue 正常队列
    // 不要监听 normal-queue 正常队列
//    @RabbitListener(queues = DeadLetterRabbitConfig.NORMAL_QUEUE)
    public void normal(Message message)  {
        log.info(" [ 消费者@A号 ] 接收到消息 ==> '" + new String(message.getBody()));
        log.info("当前执行次数：{}", count++);
        int i = 1 / 0;
        log.info(" [ 消费者@A号 ] 消费了消息 ==> '" + new String(message.getBody()));
    }

//    @RabbitListener(queues = DeadLetterRabbitConfig.NORMAL_QUEUE)
    public void normal2(Message message, Channel channel) throws IOException {
        log.info(" [ 消费者@A号 ] 接收到消息 ==> '" + new String(message.getBody()));
        log.info("当前执行次数：{}", count++);
        try {
            // 制造异常
            int i = 1 / 0;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(" [ 消费者@A号 ] 消费了消息 ==> '" + new String(message.getBody()));
        } catch (Exception e) {
            log.info("捕获异常，不会启动重试机制，异常消息直接转发到死信队列");
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }
    }

    // 不要监听 normal-queue2 正常队列

    // 监听死信队列
    @RabbitListener(queues = DeadLetterRabbitConfig.DL_QUEUE)
    public void dl(Message message) {
        log.info(" [ 消费者@死信号 ] 接收到消息 ==> '" + new String(message.getBody()));
    }

}
