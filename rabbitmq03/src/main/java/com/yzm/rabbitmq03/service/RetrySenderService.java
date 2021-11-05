package com.yzm.rabbitmq03.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;

//@Component
public class RetrySenderService {

    private final RabbitTemplate rabbitTemplate;
    private int count = 1;

    public RetrySenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 10000)
    public void send() {
        if (count <= 1) {
            String message = "Hello.........." + count++;
            rabbitTemplate.convertAndSend("retry.exchange", "topic.yzm.retry", message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

}
