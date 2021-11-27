package com.yzm.rabbitmq_06.receiver;

import com.yzm.rabbitmq_06.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Receiver {

    // 监听死信队列
    @RabbitListener(queues = RabbitConfig.DL_QUEUE)
    public void dl(Message message) {
        log.info(" [ 消费者@死信号 ] 接收到消息 ==> '" + new String(message.getBody()));
    }

}
