package com.yzm.fanout.service;

import com.yzm.fanout.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiverService {

    private int countA = 1;
    private int countB = 1;

    @RabbitListener(queues = RabbitConfig.QUEUE_A)
    public void receiveA(String message) {
        try {
            System.out.println(" [ 消费者@A号 ] Received ==> '" + message + "'");
            Thread.sleep(1000);
            System.out.println(" [ 消费者@A号 ] Dealt with：" + countA++);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_B)
    public void receiveB(String message) {
        try {
            System.out.println(" [ 消费者@B号 ] Received ==> '" + message + "'");
            Thread.sleep(2000);
            System.out.println(" [ 消费者@B号 ] Dealt with：" + countB++);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
