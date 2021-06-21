package com.yzm.direct.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    //队列
    public static final String QUEUE_C = "queue-c";
    public static final String QUEUE_D = "queue-d";

    //路由键
    public static final String DIRECT_C = "direct.c";
    public static final String DIRECT_D = "direct.d";

    public static final String DIRECT_EXCHANGE = "direct.exchange";

    @Bean
    public Queue queueC() {
        return new Queue(QUEUE_C);
    }

    @Bean
    public Queue queueD() {
        return new Queue(QUEUE_D);
    }

    /**
     * 消息交换机配置
     * 定义交换机direct exchange，绑定消息队列queue
     * name：交换机名称
     * durable：设置是否持久，设置为true则将Exchange存盘，即使服务器重启数据也不会丢失
     * autoDelete：设置是否自动删除，当最后一个绑定到Exchange上的队列删除后，自动删除该Exchange，简单来说也就是如果该Exchange没有和任何队列Queue绑定则删除
     * internal：设置是否是RabbitMQ内部使用，默认false。如果设置为 true ，则表示是内置的交换器，客户端程序无法直接发送消息到这个交换器中，只能通过交换器路由到交换器这种方式。
     * <p>
     * 广播式交换机：fanout交换器中没有路由键的概念，他会把消息发送到所有绑定在此交换器上面的队列中。
     * 路由式交换机：direct交换器相对来说比较简单，匹配规则为：路由键匹配，消息就被投送到相关的队列
     * 主题式交换机：topic交换器你采用模糊匹配路由键的原则进行转发消息到队列中
     */
    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(DIRECT_EXCHANGE).build();
    }

    /**
     * 将消息队列和交换机进行绑定
     * 交换机相当于Map容器，路由对应key，Queue对应value
     * 发送消息时，指定交换机中的key就能将消息加入到对应的队列中
     */
    @Bean
    public Binding bindingC() {
        return BindingBuilder.bind(queueC()).to(directExchange()).with(DIRECT_C);
    }

    @Bean
    public Binding bindingD() {
        return BindingBuilder.bind(queueD()).to(directExchange()).with(DIRECT_D);
    }

}
