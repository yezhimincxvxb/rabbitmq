package com.yzm.rabbitmq01.service;

import com.yzm.rabbitmq01.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 */
@Component
public class WorkReceiverService {

    private int count = 1;
    private int count2 = 1;

    @RabbitListener(queues = RabbitConfig.WORK_QUEUE, containerFactory = RabbitConfig.PREFETCH_ONE)
    public void workReceive(String message) {
        try {
            System.out.println(" [ 消费者@1号 ] Received ==> '" + message + "'");
            Thread.sleep(1000);
            System.out.println(" [ 消费者@1号 ] Dealt with：" + count++);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitConfig.WORK_QUEUE, containerFactory = RabbitConfig.PREFETCH_ONE)
    public void workReceive2(String message) {
        try {
            System.out.println(" [ 消费者@2号 ] Received ==> '" + message + "'");
            Thread.sleep(2000);
            System.out.println(" [ 消费者@2号 ] Dealt with：" + count2++);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
