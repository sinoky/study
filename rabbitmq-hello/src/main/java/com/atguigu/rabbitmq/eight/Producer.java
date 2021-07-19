package com.atguigu.rabbitmq.eight;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

/**
 * @author Zx
 * @Date 2021/7/7 11:51
 */
public class Producer {
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitmqUtils.getChannel();

        AMQP.BasicProperties propes = new AMQP.BasicProperties().builder().expiration("10000").build();
        for (int i = 0; i < 10; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",propes, message.getBytes());
        }
    }
}
