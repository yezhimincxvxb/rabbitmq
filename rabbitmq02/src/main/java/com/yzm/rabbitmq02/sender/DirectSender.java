package com.yzm.rabbitmq02.sender;

import com.yzm.rabbitmq02.config.DirectRabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;

//@Component
public class DirectSender {

    private final RabbitTemplate rabbitTemplate;

    public DirectSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void send_1() {
        for (int i = 1; i <= 30; i++) {
            String message = "Hello World ..." + i;
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
            if (i % 3 == 0) {
                rabbitTemplate.convertAndSend(DirectRabbitConfig.DIRECT_EXCHANGE, DirectRabbitConfig.DIRECT_C, message);
            } else if (i % 3 == 1) {
                rabbitTemplate.convertAndSend(DirectRabbitConfig.DIRECT_EXCHANGE, DirectRabbitConfig.DIRECT_D, message);
            } else {
                rabbitTemplate.convertAndSend(DirectRabbitConfig.DIRECT_EXCHANGE, DirectRabbitConfig.DIRECT_D2, message);
            }
        }
    }

}
