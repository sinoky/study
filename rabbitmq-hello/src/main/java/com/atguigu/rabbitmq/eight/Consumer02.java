package com.atguigu.rabbitmq.eight;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author Zx
 * @Date 2021/7/7 17:29
 */
public class Consumer02 {
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitmqUtils.getChannel();

        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println("消费2");
        };
        channel.basicConsume(DEAD_QUEUE,false,deliverCallback,consumerTag -> {});
    }
}
