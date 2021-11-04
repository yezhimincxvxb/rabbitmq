package com.yzm.rabbitmq02.service;

import com.yzm.rabbitmq02.config.TopicRabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicReceiverService {

    private int countE = 1;
    private int countF = 1;
    private int countG = 1;

    @RabbitListener(queues = TopicRabbitConfig.QUEUE_E)
    public void receiveE(String message) {
        try {
            System.out.println(" [ 消费者@E号 ] Received ==> '" + message + "'");
            Thread.sleep(1000);
            System.out.println(" [ 消费者@E号 ] Dealt with：" + countE++);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = TopicRabbitConfig.QUEUE_F)
    public void receiveF(String message) {
        try {
            System.out.println(" [ 消费者@F号 ] Received ==> '" + message + "'");
            Thread.sleep(1000);
            System.out.println(" [ 消费者@F号 ] Dealt with：" + countF++);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = TopicRabbitConfig.QUEUE_G)
    public void receiveG(String message) {
        try {
            System.out.println(" [ 消费者@G号 ] Received ==> '" + message + "'");
            Thread.sleep(1000);
            System.out.println(" [ 消费者@G号 ] Dealt with：" + countG++);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
