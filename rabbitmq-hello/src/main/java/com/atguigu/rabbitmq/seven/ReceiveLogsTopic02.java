package com.atguigu.rabbitmq.seven;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author Zx
 * @Date 2021/7/6 16:14
 */
public class ReceiveLogsTopic02 {
    public static final String EXCHANGE_NAME ="topic_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitmqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queue = "Q2";
        channel.queueDeclare(queue,false,false,false,null);
        channel.queueBind(queue,EXCHANGE_NAME,"*.*.rabbit");
        channel.queueBind(queue,EXCHANGE_NAME,"lazy.#");

        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println("Q2接收到消息时回调" + new String(message.getBody(),"utf-8") + "------绑定键：" + message.getEnvelope().getRoutingKey());
        };
        channel.basicConsume(queue, true, deliverCallback, consumerTag -> {});
    }
}
