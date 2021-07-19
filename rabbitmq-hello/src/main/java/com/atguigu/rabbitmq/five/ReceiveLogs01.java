package com.atguigu.rabbitmq.five;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author Zx
 * @Date 2021/7/6 14:28
 */
public class ReceiveLogs01 {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtils.getChannel();
        String queue = channel.queueDeclare().getQueue();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        channel.queueBind(queue,EXCHANGE_NAME,"");

        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println("01接收到消息时回调" + new String(message.getBody(),"utf-8"));
        };
        channel.basicConsume(queue, true, deliverCallback, consumerTag -> {});
    }
}
