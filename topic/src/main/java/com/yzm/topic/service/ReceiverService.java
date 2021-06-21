package com.yzm.topic.service;

import com.yzm.topic.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiverService {

    @RabbitListener(queues = RabbitConfig.QUEUE_E)
    public void receiveE(String message) {
        try {
            System.out.println(" [ 消费者@E号 ] Received ==> '" + message + "'");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_F)
    public void receiveF(String message) {
        try {
            System.out.println(" [ 消费者@F号 ] Received ==> '" + message + "'");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_G)
    public void receiveG(String message) {
        try {
            System.out.println(" [ 消费者@G号 ] Received ==> '" + message + "'");
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
