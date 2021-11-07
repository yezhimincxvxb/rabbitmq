package com.yzm.rabbitmq02.service;

import com.yzm.rabbitmq02.config.DirectRabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class DirectSenderService {

    private final RabbitTemplate rabbitTemplate;
    private int count = 1;

    public DirectSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 10000)
    public void send_1() {
        while (count <= 30) {
            String message = "Hello.........." + count++;
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
            if (count % 3 == 0) {
                rabbitTemplate.convertAndSend(DirectRabbitConfig.DIRECT_EXCHANGE, DirectRabbitConfig.DIRECT_C, message);
            } else if (count % 3 == 1) {
                rabbitTemplate.convertAndSend(DirectRabbitConfig.DIRECT_EXCHANGE, DirectRabbitConfig.DIRECT_D, message);
            } else {
                rabbitTemplate.convertAndSend(DirectRabbitConfig.DIRECT_EXCHANGE, DirectRabbitConfig.DIRECT_D2, message);
            }
        }
    }

}
