package com.yzm.callback.config;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * 定义rabbit template用于数据的接收和发送
     * connectionFactory：连接工厂
     */
    @Bean("rabbit")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        //数据转换为json存入消息队列
        template.setMessageConverter(new Jackson2JsonMessageConverter());

        /* 若使用 confirm-callback 或 return-callback，需要配置
         * publisher-confirm-type: correlated
         * publisher-returns: true
         */
        template.setConfirmCallback(confirmCallback());
        template.setReturnsCallback(returnCallback());
        /* 使用return-callback时必须设置mandatory为true，
         * 或者在配置中设置mandatory-expression的值为true
         */
        template.setMandatory(true);
        return template;
    }


    @Bean
    @ConditionalOnMissingBean(value = RabbitTemplate.ConfirmCallback.class)
    public RabbitTemplate.ConfirmCallback confirmCallback() {
        return new RabbitTemplate.ConfirmCallback() {
            /**
             * 消息到达交换机触发回调
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                // ack判断消息发送到交换机是否成功
                if (ack) {
                    // 消息发送成功到达交换机
                    // 数据库操作
                    // ...
                    System.out.println("消息到达交换机");
                    System.out.println(correlationData.getReturned().getRoutingKey());
                } else {
                    System.out.println("回调id:" + correlationData.getId());
                    System.out.println("消息发送到交换机失败");
                    System.out.println("错误信息：" + cause);
                }
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(value = RabbitTemplate.ReturnsCallback.class)
    public RabbitTemplate.ReturnsCallback returnCallback() {
        return new RabbitTemplate.ReturnsCallback() {
            /**
             * 消息路由失败，回调
             * 消息(带有路由键routingKey)到达交换机，与交换机的所有绑定键绑定键进行匹配，匹配不到触发回调
             */
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                // 该交换机没有路由键匹配对应的消息队列
                // 如果信息走了该回调，就不会走confirm回调了
                System.out.println("交换机：" + returnedMessage.getExchange());
                System.out.println("路由键：" + returnedMessage.getRoutingKey());
                System.out.println("消息主体 : " + returnedMessage.getMessage());
                System.out.println("回复代码 : " + returnedMessage.getReplyCode());
                System.out.println("描述：" + returnedMessage.getReplyText());
            }
        };
    }

}
