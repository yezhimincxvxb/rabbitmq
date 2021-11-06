package com.yzm.rabbitmq01.service;

import com.yzm.rabbitmq01.config.RabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 */
//@Component
public class HelloSenderService {

    private final AmqpTemplate template;
    private int count = 1;

    public HelloSenderService(AmqpTemplate template) {
        this.template = template;
    }

    // 项目启动后，过5秒开始执行任务
    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void helloSend() {
        while (count <= 10) {
            String message = "Hello.........." + count++;
            template.convertAndSend(RabbitConfig.HELLO_WORLD, message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

}
