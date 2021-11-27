package com.yzm.rabbitmq_07.receiver;

import com.yzm.rabbitmq_07.config.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @RabbitListener(queues = RabbitConfig.ORDINARY_QUEUE)
    public void ordinary(Message message) {
        System.out.println(" [ 消费者@普通号 ] 接收到消息 ==> '" + new String(message.getBody()));
    }

    @RabbitListener(queues = RabbitConfig.ALTERNATE_QUEUE)
    public void alternate(Message message) {
        System.out.println(" [ 消费者@备用号 ] 接收到消息 ==> '" + new String(message.getBody()));
    }

}
