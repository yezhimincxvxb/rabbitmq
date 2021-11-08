package com.yzm.rabbitmq04.service;

import com.rabbitmq.client.*;
import com.yzm.rabbitmq04.config.RabbitConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ReceiverService {

    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 10000)
    public void consumerA() throws IOException {
        // 1、获取连接
        Connection connection = RabbitConfig.getConnection();
        // 2、创建通道
        Channel channel = connection.createChannel();
        /* 3、消费消息
         * String queue 队列名称
         * boolean autoAck 自动回复，当消费者接收到消息后要告诉mq消息已接收，如果将此参数设置为tru表示会自动回复mq，如果设置为false要通过编程实现回复
         * Consumer consumer，消费方法，当消费者接收到消息要执行的方法
         */
        channel.basicConsume(RabbitConfig.QUEUE, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(" [ received@A_1 ] 消息内容 : " + new String(body, StandardCharsets.UTF_8) + "!");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 10000)
    public void consumerA_2() throws IOException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();

        //取消自动ack
        channel.basicConsume(RabbitConfig.QUEUE, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(" [ received@A_2 ] 消息内容 : " + new String(body, StandardCharsets.UTF_8) + "!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //消息确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }

    //------------------------------------------------------------------------------------------------------------------

    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 10000)
    public void consumerB_1() throws IOException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(RabbitConfig.FANOUT_QUEUE_A, true, getConsumer(channel, " [ received@B_1 ] 消息内容 : ", 500));
    }

    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 10000)
    public void consumerB_2() throws IOException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(RabbitConfig.FANOUT_QUEUE_B, true, getConsumer(channel, " [ received@B_2 ] 消息内容 : ", 1000));
    }

    //------------------------------------------------------------------------------------------------------------------

    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 10000)
    public void consumerC_1() throws IOException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(RabbitConfig.DIRECT_QUEUE_A, true, getConsumer(channel, " [ received@C_1 ] 消息内容 : ", 500));
    }

    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 10000)
    public void consumerC_2() throws IOException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(RabbitConfig.DIRECT_QUEUE_B, true, getConsumer(channel, " [ received@C_2 ] 消息内容 : ", 1000));
    }

    private DefaultConsumer getConsumer(Channel channel, String s, int i) {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(s + new String(body, StandardCharsets.UTF_8) + "!");
                try {
                    Thread.sleep(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    //------------------------------------------------------------------------------------------------------------------

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 10000)
    public void consumerD_1() throws IOException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(RabbitConfig.TOPIC_QUEUE_A, true, consumer(channel, " [ received@D_1 ] 消息内容 : "));
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 10000)
    public void consumerD_2() throws IOException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(RabbitConfig.TOPIC_QUEUE_B, true, consumer(channel, " [ received@D_2 ] 消息内容 : "));
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 10000)
    public void consumerD_3() throws IOException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(RabbitConfig.TOPIC_QUEUE_C, true, consumer(channel, " [ received@D_3 ] 消息内容 : "));
    }

    private DefaultConsumer consumer(Channel channel, String s) {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(s + new String(body, StandardCharsets.UTF_8) + "!");
            }
        };
    }

}
