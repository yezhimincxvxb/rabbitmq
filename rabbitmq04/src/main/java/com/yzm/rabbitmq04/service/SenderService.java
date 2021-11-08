package com.yzm.rabbitmq04.service;

import com.rabbitmq.client.*;
import com.yzm.rabbitmq04.config.RabbitConfig;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Component
public class SenderService {

    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void producerA() throws IOException, TimeoutException {
        //1、获取连接
        Connection connection = RabbitConfig.getConnection();
        //2、创建通道，使用通道才能完成消息相关的操作
        Channel channel = connection.createChannel();
        /*
         * 3、声明队列
         * String queue 队列名称
         * boolean durable 是否持久化，如果持久化，mq重启后队列还在
         * boolean exclusive 是否独占连接，队列只允许在该连接中访问，如果connection连接关闭队列则自动删除,如果将此参数设置true可用于临时队列的创建
         * boolean autoDelete 自动删除，队列不再使用时是否自动删除此队列，如果将此参数和exclusive参数设置为true就可以实现临时队列（队列不用了就自动删除）
         * Map<String, Object> arguments 参数，可以设置一个队列的扩展参数，比如：可设置存活时间
         */
        channel.queueDeclare(RabbitConfig.QUEUE, true, false, false, null);
        /*
         * 4、发送消息
         * exchange，交换机，如果不指定将使用mq的默认交换机（设置为""）
         * routingKey，路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机，routingKey设置为队列的名称
         * props，向消费者传递的 消息属性(比如文本持久化)
         * body，消息内容
         */
        for (int i = 1; i <= 10; i++) {
            String message = "Hello World!...... " + i;
            System.out.println(" [ Sent ] 消息内容 " + message);
            channel.basicPublish("", RabbitConfig.QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        }

        // 5、释放资源
        channel.close();
        connection.close();
    }

    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void producerB() throws IOException, TimeoutException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        /*
         * 声明 fanout 交换机
         *  exchange 交换机名称
         *  type 交换机类型
         *  durable 是否可持久化
         *  autoDelete 是否自动删除
         *  internal 是否设置为内置交换机
         */
        channel.exchangeDeclare(RabbitConfig.FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT, true, false, false, null);
        // 声明队列
        channel.queueDeclare(RabbitConfig.FANOUT_QUEUE_A, true, false, false, null);
        channel.queueDeclare(RabbitConfig.FANOUT_QUEUE_B, true, false, false, null);
        // 队列绑定交换机，不需要路由键，用空字符串表示
        channel.queueBind(RabbitConfig.FANOUT_QUEUE_A, RabbitConfig.FANOUT_EXCHANGE, "");
        channel.queueBind(RabbitConfig.FANOUT_QUEUE_B, RabbitConfig.FANOUT_EXCHANGE, "");

        for (int i = 1; i <= 10; i++) {
            String message = "Hello World!...... " + i;
            System.out.println(" [ Sent ] 消息内容 " + message);
            channel.basicPublish(RabbitConfig.FANOUT_EXCHANGE, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        }

        channel.close();
        connection.close();
    }

    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void producerC() throws IOException, TimeoutException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        /*
         * 声明 direct 交换机
         */
        channel.exchangeDeclare(RabbitConfig.DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT, true, false, false, null);
        channel.queueDeclare(RabbitConfig.DIRECT_QUEUE_A, true, false, false, null);
        channel.queueDeclare(RabbitConfig.DIRECT_QUEUE_B, true, false, false, null);
        channel.queueBind(RabbitConfig.DIRECT_QUEUE_A, RabbitConfig.DIRECT_EXCHANGE, "direct.a");
        channel.queueBind(RabbitConfig.DIRECT_QUEUE_B, RabbitConfig.DIRECT_EXCHANGE, "direct.a.b");

        for (int i = 1; i <= 10; i++) {
            String message = "Hello World! " + i;
            if (i % 2 == 0) {
                channel.basicPublish(RabbitConfig.DIRECT_EXCHANGE, "direct.a", null, message.getBytes());
            } else {
                channel.basicPublish(RabbitConfig.DIRECT_EXCHANGE, "direct.a.b", null, message.getBytes());
            }
            System.out.println(" [ Sent ] 消息内容 " + message);
        }

        channel.close();
        connection.close();
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void producerD() throws IOException, TimeoutException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();
        /*
         * 声明 topic 交换机
         */
        channel.exchangeDeclare(RabbitConfig.TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC, true, false, false, null);
        channel.queueDeclare(RabbitConfig.TOPIC_QUEUE_A, true, false, false, null);
        channel.queueDeclare(RabbitConfig.TOPIC_QUEUE_B, true, false, false, null);
        channel.queueDeclare(RabbitConfig.TOPIC_QUEUE_C, true, false, false, null);
        channel.queueBind(RabbitConfig.TOPIC_QUEUE_A, RabbitConfig.TOPIC_EXCHANGE, "topic.*.a");
        channel.queueBind(RabbitConfig.TOPIC_QUEUE_B, RabbitConfig.TOPIC_EXCHANGE, "topic.#");
        channel.queueBind(RabbitConfig.TOPIC_QUEUE_C, RabbitConfig.TOPIC_EXCHANGE, "topic.a.*");

        for (int i = 1; i <= 30; i++) {
            String message = "Hello World! " + i;
            if (i % 3 == 0) {
                channel.basicPublish(RabbitConfig.TOPIC_EXCHANGE, "topic.a.a", null, message.getBytes());
            } else if (i % 3 == 1) {
                channel.basicPublish(RabbitConfig.TOPIC_EXCHANGE, "topic.b.b", null, message.getBytes());
            } else {
                channel.basicPublish(RabbitConfig.TOPIC_EXCHANGE, "topic.a.c", null, message.getBytes());
            }
            System.out.println(" [ Sent ] 消息内容 " + message);
        }

        channel.close();
        connection.close();
    }

    /*
     * 消息到达交换机，但交换机没有绑定队列或没有匹配的队列时，正常会消息丢失
     * 回调处理
     * 使用备用交换机
     */
//    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void producerE() throws IOException, TimeoutException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();

        //定义交换机，不绑定任何队列
        channel.exchangeDeclare(RabbitConfig.NO_QUEUE_EXCHANGE, BuiltinExchangeType.FANOUT, true, false, false, null);

        String message = "Hello World!";
        channel.basicPublish(RabbitConfig.NO_QUEUE_EXCHANGE, "confirm", true, false,
                MessageProperties.PERSISTENT_BASIC, message.getBytes());
        System.out.println(" [ Sent ] 消息内容 " + message);

        //消息不可达，回调
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey,
                                     AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("没有到达目的地的消息：：" + new String(body));
            }
        });

        channel.close();
        connection.close();
    }

    //    @Scheduled(fixedDelay = 60 * 60 * 1000, initialDelay = 5000)
    public void producerF() throws IOException, TimeoutException {
        Connection connection = RabbitConfig.getConnection();
        Channel channel = connection.createChannel();

        //定义交换机，绑定备用交换机
        Map<String, Object> args = new HashMap<>();
        args.put("alternate-exchange", RabbitConfig.SPARE_EXCHANGE);
        channel.exchangeDeclare(RabbitConfig.SPARE_EXCHANGE, BuiltinExchangeType.FANOUT, true, false, false, null);
        channel.queueDeclare(RabbitConfig.SPARE_QUEUE, true, false, false, null);
        channel.queueBind(RabbitConfig.SPARE_QUEUE, RabbitConfig.SPARE_EXCHANGE, "");

        channel.exchangeDeclare(RabbitConfig.NO_QUEUE_EXCHANGE, BuiltinExchangeType.FANOUT, true, false, false, args);

        String message = "Hello World!";
        channel.basicPublish(RabbitConfig.NO_QUEUE_EXCHANGE, "confirm", true, false,
                MessageProperties.PERSISTENT_BASIC, message.getBytes());
        System.out.println(" [ Sent ] 消息内容 " + message);

        channel.close();
        connection.close();
    }
}
