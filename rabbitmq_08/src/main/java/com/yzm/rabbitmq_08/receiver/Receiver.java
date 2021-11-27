package com.yzm.rabbitmq_08.receiver;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "callback_queue"),
            exchange = @Exchange(value = "callback.exchange"),
            key = {"callback.a.yzm", "callback.b.admin"}
    ))
    public void callbackA(Message message) {
        System.out.println(" [ 消费者@A号 ] Received ==> '" + new String(message.getBody()) + "'");
    }

}
