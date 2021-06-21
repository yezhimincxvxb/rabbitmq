package com.yzm.direct.service;

import com.yzm.direct.config.RabbitConfig;
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
    public void send_1() {
        if (count <= 10) {
            String message = "Hello.........." + count++;
            rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE, RabbitConfig.DIRECT_C, message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

    @Scheduled(fixedDelay = 500, initialDelay = 30000)
    public void send_2() {
        if (count <= 20) {
            String message = "Hello.........." + count++;
            rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE, RabbitConfig.DIRECT_D, message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

}
