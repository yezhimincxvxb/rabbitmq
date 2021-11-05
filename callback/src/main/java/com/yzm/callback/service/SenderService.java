package com.yzm.callback.service;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
public class SenderService {

    @Resource(name = "rabbit")
    private RabbitTemplate rabbitTemplate;

    private int count = 1;

    @Scheduled(fixedDelay = 1000, initialDelay = 10000)
    public void send() {
        if (count <= 10) {
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            String message = "Hello.........." + count++;
            rabbitTemplate.convertAndSend("topic.exchange", "不存在的路由键", message, correlationData);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

}
