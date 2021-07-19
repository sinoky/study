package com.atguigu.rabbitmq.six;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author Zx
 * @Date 2021/7/6 15:15
 */
public class ReceiveLogDirect03 {
    public static final String EXCHANGE_NAME ="direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitmqUtils.getChannel();

        channel.queueDeclare("disk", false,false,false,null);
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        channel.queueBind("disk",EXCHANGE_NAME,"error");

        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println("disk-error接收到消息时回调" + new String(message.getBody(),"utf-8"));
        };
        channel.basicConsume("disk", true, deliverCallback, consumerTag -> {});
    }
}
