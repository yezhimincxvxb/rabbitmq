package com.yzm.rabbitmq03.receiver;

import com.yzm.rabbitmq03.config.AlternateRabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AlternateReceiver {

    @RabbitListener(queues = AlternateRabbitConfig.ORDINARY_QUEUE)
    public void ordinary(Message message) {
        System.out.println(" [ 消费者@普通号 ] 接收到消息 ==> '" + new String(message.getBody()));
    }

    @RabbitListener(queues = AlternateRabbitConfig.ALTERNATE_QUEUE)
    public void alternate(Message message) {
        System.out.println(" [ 消费者@备用号 ] 接收到消息 ==> '" + new String(message.getBody()));
    }

}
