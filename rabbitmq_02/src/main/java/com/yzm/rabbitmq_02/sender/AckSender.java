package com.yzm.rabbitmq_02.sender;

import com.yzm.rabbitmq_02.config.RabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息发布
 */
@RestController
public class AckSender {

    private final AmqpTemplate template;

    public AckSender(AmqpTemplate template) {
        this.template = template;
    }

    @GetMapping("/send")
    public void send(@RequestParam(value = "message", required = false, defaultValue = "Hello World") String message) {
        for (int i = 1; i <= 10; i++) {
            String msg = message + " ..." + i;
            System.out.println(" [ 生产者 ] Sent ==> '" + msg + "'");
            template.convertAndSend(RabbitConfig.ACK_QUEUE, msg);
        }
    }
}
