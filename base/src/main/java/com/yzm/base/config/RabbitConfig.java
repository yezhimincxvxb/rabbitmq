package com.yzm.base.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String HELLO_WORLD = "hello-world";

    /**
     * 消息队列
     * <p>
     * durable 设置是否持久化。持久化的队列会存盘，在服务器重启的时候可以保证不丢失相关信息
     * exclusive 设置是否排他。如果一个队列被声明为排他队列，该队列仅对首次声明它的连接可见，并在连接断开时自动删除
     * autoDelete 设置是否自动删除。自动删除的前提是：至少有一个消费者连接到这个队列，之后所有与这个队列连接的消费者都断开时，才会自动删除
     * map arguments.put("x-message-ttl", 20 * 1000); // 设置 20s超时
     */
    @Bean
    public Queue queue() {
        return new Queue(HELLO_WORLD, true, false, false);
    }
}
