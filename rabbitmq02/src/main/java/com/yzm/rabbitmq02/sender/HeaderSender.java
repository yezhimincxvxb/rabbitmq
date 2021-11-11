package com.yzm.rabbitmq02.sender;

import com.yzm.rabbitmq02.config.HeaderRabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class HeaderSender {

    private final RabbitTemplate rabbitTemplate;

    public HeaderSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void send() {
        String s = "Hello World";
        System.out.println(" [ 生产者 ] Sent ==> '" + s + "'");
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("key3", "value3");
        messageProperties.setHeader("name", "yzm");
        Message message = new Message(s.getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.convertAndSend(HeaderRabbitConfig.HEADER_EXCHANGE, "", message);
    }

}
