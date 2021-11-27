package com.yzm.rabbitmq_08.sender;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.ReturnListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

@RestController
public class Sender {

    @Resource(name = "rabbit")
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/callback")
    public void callback() {
        // 全局唯一
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        String message = "Hello world!";
        System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        rabbitTemplate.convertAndSend("callback.exchange", "callback.a.yzm", message, correlationData);
    }

    @GetMapping("/callback2")
    public void callback2() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        String message = "Hello world!";
        System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        rabbitTemplate.convertAndSend("不存在的交换机", "callback.a.yzm", message, correlationData);
    }

    @GetMapping("/callback3")
    public void callback3() {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        String message = "Hello world!";
        System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        rabbitTemplate.convertAndSend("callback.exchange", "不存在的路由键", message, correlationData);
    }

    /**
     * 应用：
     * 确认消息已经成功发送到队列中，如果未发送成功至队列，可进行消息的重新发送等操作。
     */
    @GetMapping("/ret")
    public void ret() throws IOException {
        Channel channel = rabbitTemplate.getConnectionFactory().createConnection().createChannel(false);
        //消息不可达，回调
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText,
                                     String exchange, String routingKey,
                                     AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("replyCode：" + replyCode);
                System.out.println("replyText：" + replyText);
                System.out.println("exchange：" + exchange);
                System.out.println("routingKey：" + routingKey);
                System.out.println("properties：" + properties);
                System.out.println("body：" + new String(body));
            }
        });

        String message = "Hello world!";
        System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        /**
         * Mandatory
         * 如果设置为ture：就表示的是要监听不可达的消息，然后进行处理
         * 如果设置为false：那么队列端会直接删除这个消息（默认值）
         */
        channel.basicPublish("callback.exchange", "不存在的路由键", true,
                MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
    }

}
