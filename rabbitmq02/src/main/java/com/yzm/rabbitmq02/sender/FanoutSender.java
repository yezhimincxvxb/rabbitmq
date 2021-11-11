package com.yzm.rabbitmq02.sender;

import com.yzm.rabbitmq02.config.FanoutRabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;

//@Component
public class FanoutSender {

    private final RabbitTemplate rabbitTemplate;

    public FanoutSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void send() {
        for (int i = 1; i <= 10; i++) {
            String message = "Hello World ..." + i;
            rabbitTemplate.convertAndSend(FanoutRabbitConfig.FANOUT_EXCHANGE, "", message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

}
