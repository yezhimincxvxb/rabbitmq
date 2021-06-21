package com.yzm.base.service;

import com.yzm.base.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SenderService {

    @Autowired
    private AmqpTemplate template;

    private int count = 1;

    @Scheduled(fixedDelay = 500, initialDelay = 5000)
    public void send() {
        if (count <= 10) {
            String message = "Hello.........." + count++;
            template.convertAndSend(RabbitConfig.HELLO_WORLD, message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

}
