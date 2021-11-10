package com.yzm.rabbitmq03.sender;

import com.yzm.rabbitmq03.config.AlternateRabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AlternateSender {

    private final RabbitTemplate rabbitTemplate;

    public AlternateSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void send() {
        String[] routs = {"ordinary.yzm", "alternate.yzm","other.yzm"};
        for (int i = 0; i < 10; i++) {
            String routing = routs[i % 3];
            String message = routing + " ! @yzm_" + i;
            rabbitTemplate.convertAndSend(AlternateRabbitConfig.ORDINARY_EXCHANGE, routing, message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

}
