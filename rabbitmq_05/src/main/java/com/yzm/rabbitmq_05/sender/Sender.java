package com.yzm.rabbitmq_05.sender;

import com.yzm.rabbitmq_05.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Sender {

    private final RabbitTemplate rabbitTemplate;

    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/dl")
    public void dl() {
        String message = "Hello World!";
        System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        rabbitTemplate.convertAndSend(RabbitConfig.NORMAL_EXCHANGE, RabbitConfig.NORMAL_KEY, message);
    }

}
