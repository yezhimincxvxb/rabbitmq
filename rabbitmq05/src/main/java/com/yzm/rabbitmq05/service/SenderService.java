package com.yzm.rabbitmq05.service;

import com.rabbitmq.client.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

@Component
public class SenderService {

    @Resource(name = "rabbit")
    private RabbitTemplate rabbitTemplate;
    @Resource(name = "channel")
    private Channel channel;


    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void sendA() {
        // 全局唯一
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        String message = "Hello world! @yzm";
        System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        rabbitTemplate.convertAndSend("callback.exchange", "callback.yzm.yzm", message, correlationData);
    }

    /**
     * 应用：
     * 确认消息已经成功发送到队列中，如果未发送成功至队列，可进行消息的重新发送等操作。
     */
//    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void sendB() throws IOException {
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

        String message = "Hello world! @yzm";
        System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        /**
         * Mandatory
         * 如果设置为ture：就表示的是要监听不可达的消息，然后进行处理
         * 如果设置为false：那么队列端会直接删除这个消息（默认值）
         */
        channel.basicPublish("callback.exchange", "callback.yzm.yzm", true,
                MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
    }

    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void sendC() throws IOException {
        String message = "Hello world! @yzm";
        System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
        try {
            // 声明事务
            channel.txSelect();
            // 发送消息
            channel.basicPublish("tx.exchange", "tx.yzm", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            //int i = 1/0;
            // 提交事务
            channel.txCommit();
        } catch (Exception e) {
            // 事务回滚
            channel.txRollback();
        }
    }

    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void sendD() {
        try {
            String message = "Hello world! @yzm";
            System.out.println(" [ 生产者 ] Sent ==> '" + message + "'");
            // 开启confirm确认模式
            channel.confirmSelect();
            // 发送消息
            channel.basicPublish("confirm.exchange", "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

            // 等待消息被确认
            if (channel.waitForConfirms()) {
                System.out.println("消息发送成功");
            } else {
                // 返回false可以进行补发。
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // channel.waitForConfirms 可能返回超时异常
            // 可以进行补发。
        }

    }

    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void sendE() {
        try {
            StringBuilder message = new StringBuilder("Hello world! @yzm");
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
        } catch (InterruptedException e) {
            // 可以进行补发。
        } catch (IOException e) {
            //
        }

        System.out.println("全部执行完成");
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void sendF() {
        try {
            StringBuilder message = new StringBuilder("Hello world! @yzm");
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
