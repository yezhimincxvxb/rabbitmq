package com.yzm.rabbitmq_03.receiver;

import com.yzm.rabbitmq_03.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private int count1 = 1;
    private int count2 = 1;
    private int count3 = 1;

    @RabbitListener(queues = RabbitConfig.QUEUE_A)
    public void receiveA(String message) throws InterruptedException {
        System.out.println(" [ 消费者@A号 ] Received ==> '" + message + "'");
        Thread.sleep(200);
        System.out.println(" [ 消费者@A号 ] Dealt with：" + count1++);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_B)
    public void receiveB(String message) throws InterruptedException {
        System.out.println(" [ 消费者@B号 ] Received ==> '" + message + "'");
        Thread.sleep(200);
        System.out.println(" [ 消费者@B号 ] Dealt with：" + count2++);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_B)
    public void receiveB2(String message) throws InterruptedException {
        System.out.println(" [ 消费者@B2号 ] Received ==> '" + message + "'");
        Thread.sleep(500);
        System.out.println(" [ 消费者@B2号 ] Dealt with：" + count3++);
    }

    //----------------------------------------------------------------------------------

    @RabbitListener(queues = RabbitConfig.QUEUE_C)
    public void receiveC(String message) throws InterruptedException {
        System.out.println(" [ 消费者@C号 ] Received ==> '" + message + "'");
        Thread.sleep(200);
        System.out.println(" [ 消费者@C号 ] Dealt with：" + count1++);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_D)
    public void receiveD(String message) throws InterruptedException {
        System.out.println(" [ 消费者@D号 ] Received ==> '" + message + "'");
        Thread.sleep(200);
        System.out.println(" [ 消费者@D号 ] Dealt with：" + count2++);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_D)
    public void receiveD2(String message) throws InterruptedException {
        System.out.println(" [ 消费者@D2号 ] Received ==> '" + message + "'");
        Thread.sleep(500);
        System.out.println(" [ 消费者@D2号 ] Dealt with：" + count3++);
    }

    //-------------------------------------------------------------------------------------
    @RabbitListener(queues = RabbitConfig.QUEUE_E)
    public void receiveE(String message) throws InterruptedException {
        System.out.println(" [ 消费者@E号 ] Received ==> '" + message + "'");
        Thread.sleep(200);
        System.out.println(" [ 消费者@E号 ] Dealt with：" + count1++);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_F)
    public void receiveF(String message) throws InterruptedException {
        System.out.println(" [ 消费者@F号 ] Received ==> '" + message + "'");
        Thread.sleep(200);
        System.out.println(" [ 消费者@F号 ] Dealt with：" + count2++);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_G)
    public void receiveG(String message) throws InterruptedException {
        System.out.println(" [ 消费者@G号 ] Received ==> '" + message + "'");
        Thread.sleep(200);
        System.out.println(" [ 消费者@G号 ] Dealt with：" + count3++);
    }

    //---------------------------------------------------------------------------------

    @RabbitListener(queues = RabbitConfig.QUEUE_X)
    public void receiveX(String message) {
        System.out.println(" [ 消费者@X号 ] Received ==> '" + message + "'");
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_Y)
    public void receiveY(String message) {
        System.out.println(" [ 消费者@Y号 ] Received ==> '" + message + "'");
    }

}
