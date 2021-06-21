package com.yzm.retry.service;

import com.rabbitmq.client.Channel;
import com.yzm.retry.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class ReceiverService {

    /**
     * 可以看到重试次数是5次（包含自身消费的一次），重试时间依次是2s，4s，8s，10s（上一次间隔时间*间隔时间乘子），
     * 最后一次重试时间理论上是16s，但是由于设置了最大间隔时间是10s，因此最后一次间隔时间只能是10s，和配置相符合。
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "retry-a"),
            exchange = @Exchange(value = "topic.exchange", type = ExchangeTypes.TOPIC),
            key = {"topic.*.retry"}
    ))
    public void retry(Message message, Channel channel, @Headers Map<String, Object> map) throws IOException {
        log.info(" [ 消费者@A号 ] 接收到消息 ==> '" + new String(message.getBody()));
//        int i = 1 / 0;
        log.info(" [ 消费者@A号 ] 消费了消息 ==> '" + new String(message.getBody()));
//        channel.basicAck((Long) map.get(AmqpHeaders.DELIVERY_TAG), false);
    }

    @RabbitListener(queues = RabbitConfig.NORMAL_QUEUE)
    public void dlx(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info(" [ 消费者@A号 ] 接收到消息 ==> '" + new String(message.getBody()));
//        int i = 1 / 0;
        log.info(" [ 消费者@A号 ] 消费了消息 ==> '" + new String(message.getBody()));
//        channel.basicAck(deliveryTag, false);
    }

}
