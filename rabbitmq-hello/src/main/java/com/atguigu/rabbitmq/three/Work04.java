package com.atguigu.rabbitmq.three;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author Zx
 * @Date 2021/7/5 17:34
 */
public class Work04 {
    public static final String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitmqUtils.getChannel();
        System.out.println("C2消费时间过长");
        DeliverCallback deliverCallback = (consumerTag, message) ->{
            try {
                Thread.sleep(1000 * 30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("C2有消息发送过来了:" + new String(message.getBody()));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        //不公平分发
        channel.basicQos(1);
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {
            System.out.println("C2消费者取消订阅的回调函数");
        });
    }
}
