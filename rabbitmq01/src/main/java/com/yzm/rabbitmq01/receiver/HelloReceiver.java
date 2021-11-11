package com.yzm.rabbitmq01.receiver;

import com.rabbitmq.client.Channel;
import com.yzm.rabbitmq01.config.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 消息消费者
 */
@Component
//@RabbitListener(queues = RabbitConfig.HELLO_QUEUE)
public class HelloReceiver {

    //    @RabbitHandler
    public void helloReceive(String message) {
        System.out.println(" [ 消费者 ] Received ==> '" + message + "'");
    }

    /**
     * 推荐监听方式
     * 使用 Message 接收消息，不能使用上面那种监听队列方式
     * 而是直接在方法上监听队列，不然会报错 No method found for class java.lang.String
     */
    //@RabbitListener(queues = RabbitConfig.HELLO_QUEUE)
    public void helloReceive(
            Message message, Channel channel,
            @Headers Map<String, Object> map, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        System.out.println(" [ 消费者 ] Received ==> '" + new String(message.getBody()));

        // 确认消息
        // 第一个参数，交付标签，相当于消息ID 64位的长整数
        // 第二个参数，false表示仅确认提供的交付标签；true表示批量确认所有消息，包括提供的交付标签
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        //channel.basicAck((Long) map.get(AmqpHeaders.DELIVERY_TAG), false);
        //channel.basicAck(deliveryTag, false);

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
    }

    private int count = 1;
    private int count2 = 1;

//    @RabbitListener(queues = RabbitConfig.HELLO_QUEUE, containerFactory = RabbitConfig.PREFETCH_ONE)
    public void workReceive(Message message, Channel channel) throws InterruptedException, IOException {
        System.out.println(" [ 消费者@1号 ] Received ==> '" + new String(message.getBody()) + "'");
        Thread.sleep(500);
        System.out.println(" [ 消费者@1号 ] Dealt with：" + count++);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

//    @RabbitListener(queues = RabbitConfig.HELLO_QUEUE, containerFactory = RabbitConfig.PREFETCH_ONE)
    public void workReceive2(Message message, Channel channel) throws InterruptedException, IOException {
        System.out.println(" [ 消费者@2号 ] Received ==> '" + new String(message.getBody()) + "'");
        Thread.sleep(1000);
        System.out.println(" [ 消费者@2号 ] Dealt with：" + count2++);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitConfig.HELLO_QUEUE)
    public void workReceive3(Message message, Channel channel) throws InterruptedException, IOException {
        System.out.println(" [ 消费者@3号 ] Received ==> '" + new String(message.getBody()) + "'");
        Thread.sleep(500);
        System.out.println(" [ 消费者@3号 ] Dealt with：" + count++);
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitConfig.HELLO_QUEUE)
    public void workReceive4(Message message, Channel channel) throws InterruptedException, IOException {
        System.out.println(" [ 消费者@4号 ] Received ==> '" + new String(message.getBody()) + "'");
        Thread.sleep(1000);
        System.out.println(" [ 消费者@4号 ] Dealt with：" + count2++);
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
