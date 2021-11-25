package com.yzm.rabbitmq_01.receiver;

import com.yzm.rabbitmq_01.config.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息监听
 */
@Component
public class HelloReceiver2 {

    private int count1 = 1;
    private int count2 = 1;

    /**
     * 推荐监听方式
     * 使用 Message 接收消息，不能使用HelloReceiver那种监听队列方式
     * 而是直接在方法上监听队列，不然会报错 No method found for class java.lang.String
     */
//    @RabbitListener(queues = RabbitConfig.HELLO_QUEUE, containerFactory = RabbitConfig.PREFETCH_ONE)
    public void receive1(Message message) throws InterruptedException {
        Thread.sleep(200);
        System.out.println(" [ 消费者@1号 ] Received ==> '" + new String(message.getBody()) + "'");
        System.out.println(" [ 消费者@1号 ] 处理消息数：" + count1++);
    }

//    @RabbitListener(queues = RabbitConfig.HELLO_QUEUE, containerFactory = RabbitConfig.PREFETCH_ONE)
    public void receive2(Message message) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(" [ 消费者@2号 ] Received ==> '" + new String(message.getBody()) + "'");
        System.out.println(" [ 消费者@2号 ] 处理消息数：" + count2++);
    }

    @RabbitListener(queues = RabbitConfig.HELLO_QUEUE)
    public void receive3(Message message) throws InterruptedException {
        Thread.sleep(200);
        System.out.println(" [ 消费者@3号 ] Received ==> '" + new String(message.getBody()) + "'");
        System.out.println(" [ 消费者@3号 ] 处理消息数：" + count1++);
    }

    @RabbitListener(queues = RabbitConfig.HELLO_QUEUE)
    public void receive4(Message message) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(" [ 消费者@4号 ] Received ==> '" + new String(message.getBody()) + "'");
        System.out.println(" [ 消费者@4号 ] 处理消息数：" + count2++);
    }

}
