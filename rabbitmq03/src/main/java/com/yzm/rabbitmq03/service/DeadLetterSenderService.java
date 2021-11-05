package com.yzm.rabbitmq03.service;

import com.yzm.rabbitmq03.config.DeadLetterRabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class DeadLetterSenderService {

    private final RabbitTemplate rabbitTemplate;
    private int count = 1;

    public DeadLetterSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

//    @Scheduled(fixedDelay = 1000, initialDelay = 10000)
    public void send() {
        if (count <= 1) {
            String message = "Hello.........." + count++;
            log.info(" [ 生产者 ] Sent ==> '" + message + "'");
            rabbitTemplate.convertAndSend(DeadLetterRabbitConfig.NORMAL_EXCHANGE, DeadLetterRabbitConfig.NORMAL_KEY, message);
        }
    }

    //    @Scheduled(fixedDelay = 1000, initialDelay = 10000)
    public void send2() {
        if (count <= 1) {
            String message = "Hello.........." + count++;
            log.info(" [ 生产者 ] Sent ==> '" + message + "'");
            rabbitTemplate.convertAndSend(DeadLetterRabbitConfig.NORMAL_EXCHANGE, DeadLetterRabbitConfig.NORMAL_KEY2, message);
        }
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 10000)
    public void send3() {
        if (count <= 1) {
            String s = "Hello.........." + count++;
            log.info(" [ 生产者 ] Sent ==> '" + s + "'");

            //设置过期时间
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setExpiration("12000");
            Message message = new Message(s.getBytes(StandardCharsets.UTF_8), messageProperties);
            rabbitTemplate.convertAndSend(DeadLetterRabbitConfig.NORMAL_EXCHANGE, DeadLetterRabbitConfig.NORMAL_KEY, message);
        }
    }

}
