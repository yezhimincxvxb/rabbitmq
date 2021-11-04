package com.yzm.rabbitmq02.service;

import com.yzm.rabbitmq02.config.TopicRabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TopicSenderService {

    private final RabbitTemplate rabbitTemplate;
    private int count = 0;

    public TopicSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 500, initialDelay = 10000)
    public void send_1() {
        count++;
        if (count <= 30) {
            String message = "Hello.........." + count;
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
            if (count % 3 == 0) {
                // 一个单词yzm，可以匹配 topic.* 和 topic.#
                rabbitTemplate.convertAndSend(TopicRabbitConfig.TOPIC_EXCHANGE, "topic.yzm", message);
            } else if (count % 3 == 1) {
                // 两个单词yzm，可以匹配 topic.#
                rabbitTemplate.convertAndSend(TopicRabbitConfig.TOPIC_EXCHANGE, "topic.yzm.yzm", message);
            } else {
                // 两个单词yzm、key，可以匹配 topic.# 和 topic.*.key
                rabbitTemplate.convertAndSend(TopicRabbitConfig.TOPIC_EXCHANGE, "topic.yzm.key", message);
            }
        }
    }

}
