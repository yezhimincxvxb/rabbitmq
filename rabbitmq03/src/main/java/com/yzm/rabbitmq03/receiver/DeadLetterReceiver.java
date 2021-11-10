package com.yzm.rabbitmq03.receiver;

import com.yzm.rabbitmq03.config.DeadLetterRabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Slf4j
//@Component
public class DeadLetterReceiver {

    private int count = 1;

    // 监听 normal-queue 正常队列
    // 不要监听 normal-queue 正常队列
    //@RabbitListener(queues = DeadLetterRabbitConfig.NORMAL_QUEUE)
    public void normal(Message message)  {
        log.info(" [ 消费者@A号 ] 接收到消息 ==> '" + new String(message.getBody()));
        log.info("当前执行次数：{}", count++);
        int i = 1 / 0;
        log.info(" [ 消费者@A号 ] 消费了消息 ==> '" + new String(message.getBody()));
    }

    // 不要监听 normal-queue2 正常队列

    // 监听死信队列
    @RabbitListener(queues = DeadLetterRabbitConfig.DL_QUEUE)
    public void dl(Message message) {
        log.info(" [ 消费者@死信号 ] 接收到消息 ==> '" + new String(message.getBody()));
    }

}
