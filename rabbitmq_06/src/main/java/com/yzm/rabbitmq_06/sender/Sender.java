package com.yzm.rabbitmq_06.sender;

import com.yzm.rabbitmq_06.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
public class Sender {

    private final RabbitTemplate rabbitTemplate;

    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/dl")
    public void dl() {
        String message = "Hello World!";
        log.info(" [ 生产者 ] Sent ==> '" + message + "'");
        rabbitTemplate.convertAndSend(RabbitConfig.NORMAL_EXCHANGE, RabbitConfig.NORMAL_KEY2, message);
    }

    @GetMapping("/dl3")
    public void dl3() {
        String s = "Hello World!";
        log.info(" [ 生产者 ] Sent ==> '" + s + "'");
        //设置过期时间
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setExpiration("12000");
        Message message = new Message(s.getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.convertAndSend(RabbitConfig.NORMAL_EXCHANGE, RabbitConfig.NORMAL_KEY3, message);
    }

}
