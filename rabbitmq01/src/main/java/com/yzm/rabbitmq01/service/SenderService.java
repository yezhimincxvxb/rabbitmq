package com.yzm.rabbitmq01.service;

import com.yzm.rabbitmq01.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 */
@Slf4j
@Component
public class SenderService {

    private final AmqpTemplate template;
    private int count = 1;

    public SenderService(AmqpTemplate template) {
        this.template = template;
    }

    // 项目启动后，过5秒开始第一个任务执行，之后每过1秒执行一次任务
    @Scheduled(fixedDelay = 1000, initialDelay = 5000)
    public void send() {
        if (count <= 10) {
            String message = "Hello.........." + count++;
            template.convertAndSend(RabbitConfig.HELLO_WORLD, message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

}
