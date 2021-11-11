package com.yzm.rabbitmq02.sender;

import com.yzm.rabbitmq02.config.TopicRabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TopicSender {

    private final RabbitTemplate rabbitTemplate;

    public TopicSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void send_1() {
        for (int i = 1; i <= 30; i++) {
            String message = "Hello World ..." + i;
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
            if (i % 3 == 0) {
                // topic.yzm.key，可以匹配 topic.yzm.* 和 topic.#
                rabbitTemplate.convertAndSend(TopicRabbitConfig.TOPIC_EXCHANGE, "topic.yzm.key", message);
            } else if (i % 3 == 1) {
                // topic.yzm.yzm，可以匹配 topic.yzm.* 、 topic.# 和 topic.*.yzm
                rabbitTemplate.convertAndSend(TopicRabbitConfig.TOPIC_EXCHANGE, "topic.yzm.yzm", message);
            } else {
                // topic.key.yzm，可以匹配 topic.# 和 topic.*.yzm
                rabbitTemplate.convertAndSend(TopicRabbitConfig.TOPIC_EXCHANGE, "topic.key.yzm", message);
            }
        }
    }

}
