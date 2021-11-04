package com.yzm.rabbitmq01.service;

import com.rabbitmq.client.Channel;
import com.yzm.rabbitmq01.config.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消息消费者
 */
@Component
//@RabbitListener(queues = RabbitConfig.HELLO_WORLD)
public class HelloReceiverService {

//    @RabbitHandler
//    public void helloReceive(String message) {
//        System.out.println(" [ 消费者 ] Received ==> '" + message + "'");
//    }

    /**
     * 使用 Message 接收消息，不能使用上面那种绑定队列方式
     * 而是直接在方法上绑定队列，不然会报错 No method found for class java.lang.String
     */
    /*@RabbitListener(queues = RabbitConfig.HELLO_WORLD)
    public void helloReceive(Message message, Channel channel) throws IOException {
        System.out.println(" [ 消费者 ] 接收到消息 ==> '" + new String(message.getBody()));

        // 确认消息
        // 第一个参数，交付标签，相当于消息ID 64位的长整数
        // 第二个参数，false表示仅确认提供的交付标签；true表示批量确认所有消息，包括提供的交付标签
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        // 拒绝消息方式一
        // 第一个参数，交付标签
        // 第二个参数，false表示仅拒绝提供的交付标签；true表示批量拒绝所有消息，包括提供的交付标签
        // 第三个参数，false表示直接丢弃消息，true表示重新排队
        //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);

        // 拒绝消息方式二
        // 第一个参数，交付标签
        // 第二个参数，false表示直接丢弃消息，true表示重新排队
        // 跟basicNack的区别就是始终只拒绝提供的交付标签
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }*/

    @RabbitListener(queues = RabbitConfig.HELLO_WORLD)
    public void helloReceive(Message message, Channel channel) throws IOException {
        System.out.println(" [ 消费者 ] 接收到消息 ==> '" + new String(message.getBody()));
        try {
            // 制造异常
            int i = 1 / 0;
            System.out.println("成功处理了消息");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 这里拒绝后，可以选择将异常消息发送到死信队列
            System.out.println("有异常情况，将异常消息发送到死信队列，请尽快处理");
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}
