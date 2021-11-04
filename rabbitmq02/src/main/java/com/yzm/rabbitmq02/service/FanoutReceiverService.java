package com.yzm.rabbitmq02.service;

import com.yzm.rabbitmq02.config.FanoutRabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutReceiverService {

    private int countA = 1;
    private int countB = 1;
    private int countB_2 = 1;

    @RabbitListener(queues = FanoutRabbitConfig.QUEUE_A)
    public void receiveA(String message) {
        try {
            System.out.println(" [ 消费者@A号 ] Received ==> '" + message + "'");
            Thread.sleep(1000);
            System.out.println(" [ 消费者@A号 ] Dealt with：" + countA++);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = FanoutRabbitConfig.QUEUE_B)
    public void receiveB(String message) {
        try {
            System.out.println(" [ 消费者@B号 ] Received ==> '" + message + "'");
            Thread.sleep(1000);
            System.out.println(" [ 消费者@B号 ] Dealt with：" + countB++);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = FanoutRabbitConfig.QUEUE_B)
    public void receiveB_2(String message) {
        try {
            System.out.println(" [ 消费者@B_2号 ] Received ==> '" + message + "'");
            Thread.sleep(2000);
            System.out.println(" [ 消费者@B_2号 ] Dealt with：" + countB_2++);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
