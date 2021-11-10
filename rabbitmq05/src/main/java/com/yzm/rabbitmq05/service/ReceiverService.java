package com.yzm.rabbitmq05.service;

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
            value = @Queue(value = "callback-a"),
            exchange = @Exchange(value = "callback.exchange", type = ExchangeTypes.TOPIC),
            key = {"callback.a.*", "callback.*.a"}
    ))
    public void receiveA(Message message) {
        System.out.println(" [ 消费者@A号 ] Received ==> '" + new String(message.getBody()) + "'");
    }

    //----------------------------------------------------------------------------------------------------------------

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "tx-a"),
            exchange = @Exchange(value = "tx.exchange", type = ExchangeTypes.DIRECT),
            key = {"tx.yzm"}
    ))
    public void receiveC(Message message) {
        System.out.println(" [ 消费者@C号 ] Received ==> '" + new String(message.getBody()) + "'");
    }

    //----------------------------------------------------------------------------------------------------------------

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "confirm-a"),
            exchange = @Exchange(value = "confirm.exchange", type = ExchangeTypes.FANOUT)
    ))
    public void receiveD(Message message) {
        System.out.println(" [ 消费者@D号 ] Received ==> '" + new String(message.getBody()) + "'");
    }

    //----------------------------------------------------------------------------------------------------------------

}
