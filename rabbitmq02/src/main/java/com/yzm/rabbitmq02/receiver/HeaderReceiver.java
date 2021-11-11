package com.yzm.rabbitmq02.receiver;

import com.yzm.rabbitmq02.config.HeaderRabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HeaderReceiver {

    @RabbitListener(queues = HeaderRabbitConfig.QUEUE_X)
    public void receiveA(String message) {
        System.out.println(" [ 消费者@X号 ] Received ==> '" + message + "'");
    }

    @RabbitListener(queues = HeaderRabbitConfig.QUEUE_Y)
    public void receiveB(String message) {
        System.out.println(" [ 消费者@Y号 ] Received ==> '" + message + "'");
    }

}
