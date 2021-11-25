package com.yzm.rabbitmq_03.sender;

import com.yzm.rabbitmq_03.config.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class Sender {

    private final RabbitTemplate rabbitTemplate;

    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/fanout")
    public void fanout() {
        for (int i = 1; i <= 10; i++) {
            String message = "Hello World ..." + i;
            rabbitTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE, "", message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

    @GetMapping("/direct")
    public void direct() {
        for (int i = 1; i <= 30; i++) {
            String message = "Hello World ..." + i;
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
            if (i % 3 == 0) {
                rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE, RabbitConfig.DIRECT_C, message);
            } else if (i % 3 == 1) {
                rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE, RabbitConfig.DIRECT_D, message);
            } else {
                rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE, RabbitConfig.DIRECT_D2, message);
            }
        }
    }

    @GetMapping("/topic")
    public void topic() {
        for (int i = 1; i <= 30; i++) {
            String message = "Hello World ..." + i;
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
            if (i % 3 == 0) {
                // topic.yzm.key，可以匹配 topic.yzm.* 和 topic.#
                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, "topic.yzm.key", message);
            } else if (i % 3 == 1) {
                // topic.yzm.yzm，可以匹配 topic.yzm.* 、 topic.# 和 topic.*.yzm
                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, "topic.yzm.yzm", message);
            } else {
                // topic.key.yzm，可以匹配 topic.# 和 topic.*.yzm
                rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, "topic.key.yzm", message);
            }
        }
    }

    @GetMapping("/headers")
    public void headers() {
        String s = "Hello World";
        System.out.println(" [ 生产者 ] Sent ==> '" + s + "'");
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("key1", "value1");
        messageProperties.setHeader("name", "yzm");
        Message message = new Message(s.getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.convertAndSend(RabbitConfig.HEADER_EXCHANGE, "", message);
    }

    @GetMapping("/headers2")
    public void headers2() {
        String s = "Hello World";
        System.out.println(" [ 生产者 ] Sent ==> '" + s + "'");
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("key3", "value3");
        messageProperties.setHeader("name", "yzm");
        Message message = new Message(s.getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.convertAndSend(RabbitConfig.HEADER_EXCHANGE, "", message);
    }

}
