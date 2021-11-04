package com.yzm.rabbitmq01.service;

import com.yzm.rabbitmq01.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 消息生产者
 */
//@Component
public class WorkSenderService {

    private final AmqpTemplate template;
    private int count = 1;

    public WorkSenderService(AmqpTemplate template) {
        this.template = template;
    }

    @Scheduled(fixedDelay = 500, initialDelay = 10000)
    public void workSend() {
        if (count <= 30) {
            String message = "Hello.........." + count++;
            template.convertAndSend(RabbitConfig.WORK_QUEUE, message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

}
