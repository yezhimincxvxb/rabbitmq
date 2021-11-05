package com.yzm.callback.service;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiverService {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ack-a"),
            exchange = @Exchange(value = "topic.exchange", type = ExchangeTypes.TOPIC),
            key = {"topic.*.a", "topic.a.#"}
    ))
    public void receiveA(Message message) {
        System.out.println(" [ 消费者@A号 ] Received ==> '" + new String(message.getBody()) + "'");
    }
}
