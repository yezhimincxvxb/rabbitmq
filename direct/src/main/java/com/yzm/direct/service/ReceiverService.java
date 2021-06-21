package com.yzm.direct.service;

import com.yzm.direct.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiverService {

    @RabbitListener(queues = RabbitConfig.QUEUE_C)
    public void receiveC(String message) {
        try {
            System.out.println(" [ 消费者@C号 ] Received ==> '" + message + "'");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_D)
    public void receiveD(String message) {
        try {
            System.out.println(" [ 消费者@D号 ] Received ==> '" + message + "'");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
