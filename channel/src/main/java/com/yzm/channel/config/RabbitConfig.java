package com.yzm.channel.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RabbitConfig {

    public static final String QUEUE = "queue-a";
    public static final String FANOUT_EXCHANGE = "fanout-exchange-a";
    public static final String FANOUT_QUEUE_A = "fanout-queue-a";
    public static final String FANOUT_QUEUE_B = "fanout-queue-b";
    public static final String DIRECT_EXCHANGE = "direct-exchange-a";
    public static final String DIRECT_QUEUE_A = "direct-queue-a";
    public static final String DIRECT_QUEUE_B = "direct-queue-b";
    public static final String TOPIC_EXCHANGE = "topic-exchange-a";
    public static final String TOPIC_QUEUE_A = "topic-queue-a";
    public static final String TOPIC_QUEUE_B = "topic-queue-b";
    public static final String TOPIC_QUEUE_C = "topic-queue-c";

    public static final String NO_QUEUE_EXCHANGE = "no-queue-exchange";
    public static final String SPARE_EXCHANGE = "spare-exchange";
    public static final String SPARE_QUEUE = "spare-queue";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("127.0.0.1");
            factory.setPort(5672);
            factory.setUsername("guest");
            factory.setPassword("guest");
            connection = factory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
