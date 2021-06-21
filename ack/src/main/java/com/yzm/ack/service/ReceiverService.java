package com.yzm.ack.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class ReceiverService {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "ack-a"),
            exchange = @Exchange(value = "topic.exchange", type = ExchangeTypes.TOPIC),
            key = {"topic.*.ack"}
    ))
    public void receiveA(Message message, Channel channel) throws IOException {
        System.out.println(" [ 消费者@A号 ] 接收到消息 ==> '" + new String(message.getBody()));
//        int i = 1 / 0;
        System.out.println(" [ 消费者@A号 ] 消费了消息 ==> '" + new String(message.getBody()));

        //确认消息
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        //丢弃消息
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
    }


}
