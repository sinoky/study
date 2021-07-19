package com.atguigu.rabbitmq.two;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author Zx
 * @Date 2021/7/5 14:56
 */
public class Worker01 {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitmqUtils.getChannel();
        //当一个消息发送过来后的回调接口
        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println("有消息发送过来了:" + new String(message.getBody()));
        };

        //当一个消费者取消订阅时的回调接口
        CancelCallback cancelCallback = consumerTag ->{
            System.out.println("消费者取消订阅");
        };
        System.out.println("C2即将消费消息");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
