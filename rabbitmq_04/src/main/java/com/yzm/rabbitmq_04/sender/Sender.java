package com.yzm.rabbitmq_04.sender;

import com.yzm.rabbitmq_04.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Sender {

    private final RabbitTemplate rabbitTemplate;

    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/retry")
    public void retry() {
        String message = "Hello World !";
        rabbitTemplate.convertAndSend(RabbitConfig.RETRY_EXCHANGE, RabbitConfig.RETRY_KEY, message);
        System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
    }

}
