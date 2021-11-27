package com.yzm.rabbitmq_07.sender;

import com.yzm.rabbitmq_07.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Sender {

    private final RabbitTemplate rabbitTemplate;

    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/alternate")
    public void alternate() {
        String[] routs = {"ordinary.yzm", "alternate.yzm","other.yzm"};
        for (int i = 0; i < 10; i++) {
            String routing = routs[i % 3];
            String message = routing + " ! @yzm_" + i;
            rabbitTemplate.convertAndSend(RabbitConfig.ORDINARY_EXCHANGE, routing, message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

}
