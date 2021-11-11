package com.yzm.rabbitmq02.receiver;

import com.yzm.rabbitmq02.config.DirectRabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

//@Component
public class DirectReceiver {

    private int countC = 1;
    private int countD = 1;
    private int countD2 = 1;

    @RabbitListener(queues = DirectRabbitConfig.QUEUE_C)
    public void receiveC(String message) throws InterruptedException {
        System.out.println(" [ 消费者@C号 ] Received ==> '" + message + "'");
        Thread.sleep(500);
        System.out.println(" [ 消费者@C号 ] Dealt with：" + countC++);
    }

    @RabbitListener(queues = DirectRabbitConfig.QUEUE_D)
    public void receiveD(String message) throws InterruptedException {
        System.out.println(" [ 消费者@D号 ] Received ==> '" + message + "'");
        Thread.sleep(500);
        System.out.println(" [ 消费者@D号 ] Dealt with：" + countD++);
    }

    @RabbitListener(queues = DirectRabbitConfig.QUEUE_D)
    public void receiveD2(String message) throws InterruptedException {
        System.out.println(" [ 消费者@D2号 ] Received ==> '" + message + "'");
        Thread.sleep(1000);
        System.out.println(" [ 消费者@D2号 ] Dealt with：" + countD2++);
    }
}
