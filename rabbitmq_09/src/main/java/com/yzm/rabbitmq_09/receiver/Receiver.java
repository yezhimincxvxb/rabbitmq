package com.yzm.rabbitmq_09.receiver;

import com.rabbitmq.client.*;
import com.yzm.rabbitmq_09.config.RabbitConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/receiver")
public class Receiver {

    @GetMapping("/simple")
    public void simple() throws IOException {
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
                System.out.println(" [ received@simple_1 ] 消息内容 : " + new String(body, StandardCharsets.UTF_8) + "!");
            }
        });

        //取消自动ack
/*        channel.basicConsume(RabbitConfig.QUEUE, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(" [ received@simple_2 ] 消息内容 : " + new String(body, StandardCharsets.UTF_8) + "!");

                //消息确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });*/
    }

    @GetMapping("/fanout")
    public void fanout() {
        Thread t1 = new Thread(() -> {
            Connection connection = RabbitConfig.getConnection();
            Channel channel = null;
            try {
                channel = connection.createChannel();
                channel.basicConsume(RabbitConfig.FANOUT_QUEUE_A, true, getConsumer(channel, " [ received@fanout_1 ] : "));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            Connection connection = RabbitConfig.getConnection();
            Channel channel = null;
            try {
                channel = connection.createChannel();
                channel.basicConsume(RabbitConfig.FANOUT_QUEUE_B, true, getConsumer(channel, " [ received@fanout_2 ] : "));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t2");

        t1.start();
        t2.start();
    }

    @GetMapping("/direct")
    public void direct() {
        Thread t3 = new Thread(() -> {
            Connection connection = RabbitConfig.getConnection();
            Channel channel = null;
            try {
                channel = connection.createChannel();
                channel.basicConsume(RabbitConfig.DIRECT_QUEUE_A, true, getConsumer(channel, " [ received@direct_1 ] : "));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t3");

        Thread t4 = new Thread(() -> {
            Connection connection = RabbitConfig.getConnection();
            Channel channel = null;
            try {
                channel = connection.createChannel();
                channel.basicConsume(RabbitConfig.DIRECT_QUEUE_B, true, getConsumer(channel, " [ received@direct_2 ] : "));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t4");

        t3.start();
        t4.start();
    }

    @GetMapping("/topic")
    public void topic() {
        Thread t5 = new Thread(() -> {
            Connection connection = RabbitConfig.getConnection();
            Channel channel = null;
            try {
                channel = connection.createChannel();
                channel.basicConsume(RabbitConfig.TOPIC_QUEUE_A, true, getConsumer(channel, " [ received@topic_1 ] : "));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t5");

        Thread t6 = new Thread(() -> {
            Connection connection = RabbitConfig.getConnection();
            Channel channel = null;
            try {
                channel = connection.createChannel();
                channel.basicConsume(RabbitConfig.TOPIC_QUEUE_B, true, getConsumer(channel, " [ received@topic_2 ] : "));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t6");

        Thread t7 = new Thread(() -> {
            Connection connection = RabbitConfig.getConnection();
            Channel channel = null;
            try {
                channel = connection.createChannel();
                channel.basicConsume(RabbitConfig.TOPIC_QUEUE_C, true, getConsumer(channel, " [ received@topic_3 ] : "));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t7");

        t5.start();
        t6.start();
        t7.start();
    }

    @GetMapping("/headers")
    public void headers() {
        Thread t8 = new Thread(() -> {
            Connection connection = RabbitConfig.getConnection();
            Channel channel = null;
            try {
                channel = connection.createChannel();
                channel.basicConsume(RabbitConfig.HEADER_QUEUE_A, true, getConsumer(channel, " [ received@headers_1 ] : "));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t8");

        Thread t9 = new Thread(() -> {
            Connection connection = RabbitConfig.getConnection();
            Channel channel = null;
            try {
                channel = connection.createChannel();
                channel.basicConsume(RabbitConfig.HEADER_QUEUE_B, true, getConsumer(channel, " [ received@headers_2 ] : "));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t9");

        t8.start();
        t9.start();
    }

    private DefaultConsumer getConsumer(Channel channel, String s) {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(s + new String(body, StandardCharsets.UTF_8) + "!");
            }
        };
    }

}
