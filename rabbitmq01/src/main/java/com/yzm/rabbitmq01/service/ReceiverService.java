package com.yzm.rabbitmq01.service;

import com.yzm.rabbitmq01.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 */
@Component
@RabbitListener(queues = RabbitConfig.HELLO_WORLD)
public class ReceiverService {

    @RabbitHandler
    public void receive(String message) {
        System.out.println(" [ 消费者 ] Received ==> '" + message + "'");
    }

}
