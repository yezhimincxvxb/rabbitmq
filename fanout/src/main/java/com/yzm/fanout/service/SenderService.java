package com.yzm.fanout.service;

import com.yzm.fanout.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SenderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private int count = 1;

    @Scheduled(fixedDelay = 500, initialDelay = 10000)
    public void send() {
        if (count <= 10) {
            String message = "Hello.........." + count++;
            rabbitTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE, "", message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

}
