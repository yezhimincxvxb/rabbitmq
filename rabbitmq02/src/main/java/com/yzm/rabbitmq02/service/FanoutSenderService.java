package com.yzm.rabbitmq02.service;

import com.yzm.rabbitmq02.config.FanoutRabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;

//@Component
public class FanoutSenderService {

    private final RabbitTemplate rabbitTemplate;
    private int count = 1;

    public FanoutSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 500, initialDelay = 10000)
    public void send() {
        if (count <= 10) {
            String message = "Hello.........." + count++;
            rabbitTemplate.convertAndSend(FanoutRabbitConfig.FANOUT_EXCHANGE, "", message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

}