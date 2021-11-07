package com.yzm.rabbitmq02.service;

import com.yzm.rabbitmq02.config.DirectRabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectReceiverService {

    private int countC = 1;
    private int countD = 1;
    private int countD_2 = 1;

    @RabbitListener(queues = DirectRabbitConfig.QUEUE_C)
    public void receiveC(String message) throws InterruptedException {
        System.out.println(" [ 消费者@C号 ] Received ==> '" + message + "'");
        Thread.sleep(1000);
        System.out.println(" [ 消费者@C号 ] Dealt with：" + countC++);
    }

    @RabbitListener(queues = DirectRabbitConfig.QUEUE_D)
    public void receiveD(String message) throws InterruptedException {
        System.out.println(" [ 消费者@D号 ] Received ==> '" + message + "'");
        Thread.sleep(1000);
        System.out.println(" [ 消费者@D号 ] Dealt with：" + countD++);
    }

    @RabbitListener(queues = DirectRabbitConfig.QUEUE_D)
    public void receiveD_2(String message) throws InterruptedException {
        System.out.println(" [ 消费者@D_2号 ] Received ==> '" + message + "'");
        Thread.sleep(2000);
        System.out.println(" [ 消费者@D_2号 ] Dealt with：" + countD_2++);
    }
}
