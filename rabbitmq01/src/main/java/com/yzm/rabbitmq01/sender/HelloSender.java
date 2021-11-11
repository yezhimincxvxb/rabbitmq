package com.yzm.rabbitmq01.sender;

import com.yzm.rabbitmq01.config.RabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 */
@Component
public class HelloSender {

    private final AmqpTemplate template;

    public HelloSender(AmqpTemplate template) {
        this.template = template;
    }

    // 项目启动后，过5秒开始执行任务
//    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void helloSend() {
        for (int i = 1; i <= 10; i++) {
            String message = "Hello World ..." + i;
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
            template.convertAndSend(RabbitConfig.HELLO_QUEUE, message);
        }
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void workSend() {
        for (int i = 1; i <= 20; i++) {
            String message = "Hello World ..." + i;
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
            template.convertAndSend(RabbitConfig.HELLO_QUEUE, message);
        }
    }

}
