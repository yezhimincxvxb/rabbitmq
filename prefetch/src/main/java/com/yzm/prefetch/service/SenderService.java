package com.yzm.prefetch.service;

import com.yzm.prefetch.config.RabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SenderService {

    @Autowired
    private AmqpTemplate template;

    private int count = 1;

    @Scheduled(fixedDelay = 500, initialDelay = 10000)
    public void send() {
        if (count <= 30) {
            String message = "Hello.........." + count++;
            template.convertAndSend(RabbitConfig.WORK_QUEUE, message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

}
