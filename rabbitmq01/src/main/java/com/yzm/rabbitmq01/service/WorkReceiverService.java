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
public class WorkReceiverService {

    private int count = 1;
    private int count2 = 1;

    //@RabbitListener(queues = RabbitConfig.WORK_QUEUE, containerFactory = RabbitConfig.PREFETCH_ONE)
    public void workReceive(Message message, Channel channel) throws InterruptedException, IOException {
        System.out.println(" [ 消费者@1号 ] Received ==> '" + new String(message.getBody()) + "'");
        Thread.sleep(1000);
        System.out.println(" [ 消费者@1号 ] Dealt with：" + count++);
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    //@RabbitListener(queues = RabbitConfig.WORK_QUEUE, containerFactory = RabbitConfig.PREFETCH_ONE)
    public void workReceive2(Message message, Channel channel) throws InterruptedException, IOException {
        System.out.println(" [ 消费者@2号 ] Received ==> '" + new String(message.getBody()) + "'");
        Thread.sleep(2000);
        System.out.println(" [ 消费者@2号 ] Dealt with：" + count2++);
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitConfig.WORK_QUEUE)
    public void workReceive3(Message message, Channel channel) throws InterruptedException, IOException {
        System.out.println(" [ 消费者@1号 ] Received ==> '" + new String(message.getBody()) + "'");
        Thread.sleep(1000);
        System.out.println(" [ 消费者@1号 ] Dealt with：" + count++);
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitConfig.WORK_QUEUE)
    public void workReceive4(Message message, Channel channel) throws InterruptedException, IOException {
        System.out.println(" [ 消费者@2号 ] Received ==> '" + new String(message.getBody()) + "'");
        Thread.sleep(2000);
        System.out.println(" [ 消费者@2号 ] Dealt with：" + count2++);
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


}
