package com.yzm.retry.service;

import com.yzm.retry.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SenderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private int count = 1;

    /**
     * 测试重试机制
     * 重试异常 RepublishMessageRecoverer、ImmediateRequeueMessageRecoverer
     */
//    @Scheduled(fixedDelay = 1000, initialDelay = 10000)
    public void send1() {
        if (count <= 1) {
            String message = "Hello.........." + count++;
            rabbitTemplate.convertAndSend("topic.exchange", "topic.123.retry", message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

    /**
     * 测试死信队列
     */
    @Scheduled(fixedDelay = 1000, initialDelay = 10000)
    public void send2() {
        if (count <= 1) {
            String message = "Hello.........." + count++;
            rabbitTemplate.convertAndSend(RabbitConfig.NORMAL_EXCHANGE, "dlk.key", message);
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        }
    }

}
