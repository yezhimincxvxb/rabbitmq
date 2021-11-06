package com.yzm.rabbitmq01.service;

import com.yzm.rabbitmq01.config.RabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 */
@Component
public class WorkSenderService {

    private final AmqpTemplate template;
    private int count = 1;

    public WorkSenderService(AmqpTemplate template) {
        this.template = template;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 10000)
    public void workSend() {
        while (count <= 20) {
            String message = "Hello.........." + count++;
            template.convertAndSend(RabbitConfig.WORK_QUEUE, message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

}
