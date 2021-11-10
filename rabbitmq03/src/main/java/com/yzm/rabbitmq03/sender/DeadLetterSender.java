package com.yzm.rabbitmq03.sender;

import com.yzm.rabbitmq03.config.DeadLetterRabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.nio.charset.StandardCharsets;

@Slf4j
//@Component
public class DeadLetterSender {

    private final RabbitTemplate rabbitTemplate;

    public DeadLetterSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void send() {
        String message = "Hello World!";
        log.info(" [ 生产者 ] Sent ==> '" + message + "'");
        rabbitTemplate.convertAndSend(DeadLetterRabbitConfig.NORMAL_EXCHANGE, DeadLetterRabbitConfig.NORMAL_KEY, message);
    }

    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void send2() {
        String message = "Hello World!";
        log.info(" [ 生产者 ] Sent ==> '" + message + "'");
        rabbitTemplate.convertAndSend(DeadLetterRabbitConfig.NORMAL_EXCHANGE, DeadLetterRabbitConfig.NORMAL_KEY2, message);
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void send3() {
        String s = "Hello World!";
        log.info(" [ 生产者 ] Sent ==> '" + s + "'");

        //设置过期时间
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("12000");
        Message message = new Message(s.getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.convertAndSend(DeadLetterRabbitConfig.NORMAL_EXCHANGE, DeadLetterRabbitConfig.NORMAL_KEY, message);
    }

}
