package com.atguigu.rabbitmq.seven;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

/**
 * @author Zx
 * @Date 2021/7/6 16:22
 */
public class TopicLogs {
    public static final String EXCHANGE_NAME ="topic_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitmqUtils.getChannel();
        String message = "消息发送";
        channel.basicPublish(EXCHANGE_NAME,"lazy.rabbit",null, message.getBytes());
        System.out.println("发送成功");
    }
}
