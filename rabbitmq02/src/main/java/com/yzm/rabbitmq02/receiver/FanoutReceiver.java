package com.yzm.rabbitmq02.receiver;

import com.yzm.rabbitmq02.config.FanoutRabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutReceiver {

    private int countA = 1;
    private int countB = 1;
    private int countB2 = 1;

    @RabbitListener(queues = FanoutRabbitConfig.QUEUE_A)
    public void receiveA(String message) throws InterruptedException {
        System.out.println(" [ 消费者@A号 ] Received ==> '" + message + "'");
        Thread.sleep(500);
        System.out.println(" [ 消费者@A号 ] Dealt with：" + countA++);
    }

    @RabbitListener(queues = FanoutRabbitConfig.QUEUE_B)
    public void receiveB(String message) throws InterruptedException {
        System.out.println(" [ 消费者@B号 ] Received ==> '" + message + "'");
        Thread.sleep(500);
        System.out.println(" [ 消费者@B号 ] Dealt with：" + countB++);
    }

    @RabbitListener(queues = FanoutRabbitConfig.QUEUE_B)
    public void receiveB2(String message) throws InterruptedException {
        System.out.println(" [ 消费者@B2号 ] Received ==> '" + message + "'");
        Thread.sleep(1000);
        System.out.println(" [ 消费者@B2号 ] Dealt with：" + countB2++);
    }

}
