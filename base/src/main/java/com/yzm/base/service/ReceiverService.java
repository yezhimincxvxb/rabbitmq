package com.yzm.base.service;

import com.yzm.base.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitConfig.HELLO_WORLD)
public class ReceiverService {

    @RabbitHandler
    public void receive(String message) {
        System.out.println(" [ 消费者 ] Received ==> '" + message + "'");
    }

}
