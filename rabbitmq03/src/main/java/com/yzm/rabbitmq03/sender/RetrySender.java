package com.yzm.rabbitmq03.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;

//@Component
public class RetrySender {

    private final RabbitTemplate rabbitTemplate;

    public RetrySender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void send() {
        String message = "Hello World !";
        rabbitTemplate.convertAndSend("retry.exchange", "retry.yzm", message);
        System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
    }

}
