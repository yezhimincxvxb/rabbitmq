package com.yzm.rabbitmq_01.receiver;

import com.yzm.rabbitmq_01.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * 消息监听
 */
//@Component
@RabbitListener(queues = RabbitConfig.HELLO_QUEUE)
public class HelloReceiver {

    @RabbitHandler
    public void receive(String message) {
        System.out.println(" [ 消费者 ] Received ==> '" + message + "'");
    }

}
