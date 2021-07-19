package com.atguigu.rabbitmq.eight;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.*;

/**
 * @author Zx
 * @Date 2021/7/7 11:23
 */
public class Consumer01 {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitmqUtils.getChannel();

        Map<String, Object> argument = new HashMap<>();
        argument.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        argument.put("x-dead-letter-routing-key","lisi");

        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE,BuiltinExchangeType.DIRECT);

        channel.queueDeclare(NORMAL_QUEUE,false,false,false,argument);
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);

        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");

        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println("消费者1收到信息");
        };
        channel.basicConsume(NORMAL_QUEUE,true,deliverCallback,consumerTag -> {});
    }
}
