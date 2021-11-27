package com.yzm.rabbitmq_10.sender;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@RestController
public class Sender {

    @Resource(name = "channel")
    private Channel channel;

    @GetMapping("/tx")
    public void tx(int normal) throws IOException {
        String message = "Hello world!";
        System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        try {
            // 声明事务
            channel.txSelect();
            // 发送消息
            channel.basicPublish("tx.exchange", "tx.yzm", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            int i = 1;
            if (normal == 1) i = 1 / 0;
            log.info("提交事务");
            channel.txCommit();
        } catch (Exception e) {
            log.info("事务回滚");
            channel.txRollback();
        }
    }

    @GetMapping("/confirm")
    public void confirm() {
        try {
            String message = "Hello world!";
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
            // 开启confirm确认模式
            channel.confirmSelect();
            // 发送消息
            channel.basicPublish("confirm.exchange", "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

            // 等待消息被确认
            if (channel.waitForConfirms()) {
                System.out.println("生产者确认消费者收到消息");
            } else {
                // 返回false可以进行补发。
                System.out.println("消费者未收到消息，是否重发");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // channel.waitForConfirms 可能返回超时异常
            // 可以进行补发。
        }

    }

    @GetMapping("/confirm2")
    public void confirm2() {
        try {
            StringBuilder message = new StringBuilder("Hello world!");
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
            // 开启confirm确认模式
            channel.confirmSelect();
            // 发送消息
            for (int i = 1; i <= 5; i++) {
                message.append("_").append(i);
                channel.basicPublish("confirm.exchange", "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.toString().getBytes());
            }

            // 阻塞线程，等待消息被确认。该方法可以指定一个等待时间。该方法无返回值，只能根据抛出的异常进行判断。
            channel.waitForConfirmsOrDie();
            System.out.println("只有确认消费者收到消息了才会打印此日志");
        } catch (InterruptedException e) {
            // 可以进行补发。
        } catch (IOException e) {
            //
        }
    }

    @GetMapping("/confirm3")
    public void confirm3() {
        try {
            StringBuilder message = new StringBuilder("Hello world!");
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
            // 开启confirm确认模式
            channel.confirmSelect();
            // 发送消息
            for (int i = 1; i <= 5; i++) {
                message.append("_").append(i);
                channel.basicPublish("confirm.exchange", "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.toString().getBytes());
            }

            //异步监听确认和未确认的消息
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("未确认消息，标识：" + deliveryTag + "是否批量处理：" + multiple);
                }

                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("已确认消息，标识：" + deliveryTag + "是否批量处理：" + multiple);
                }
            });

        } catch (IOException e) {
            //
        }

    }
}
