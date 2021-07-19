package com.atguigu.rabbitmq.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Zx
 * @Date 2021/7/5 14:10
 */
public class Consumer {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //当一个消息发送过来后的回调接口
        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println("有消息发送过来了:" + new String(message.getBody()));
        };

        //当一个消费者取消订阅时的回调接口
        CancelCallback cancelCallback = consumerTag ->{
            System.out.println("消费者取消订阅");
        };
        System.out.println("C3即将消费消息");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
