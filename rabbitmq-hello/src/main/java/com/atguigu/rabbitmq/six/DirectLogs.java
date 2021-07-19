package com.atguigu.rabbitmq.six;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @author Zx
 * @Date 2021/7/6 15:22
 */
public class DirectLogs {
    public static final String EXCHANGE_NAME ="direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitmqUtils.getChannel();

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"warning", null, message.getBytes());
            System.out.println("生产者发送消息：" + message);
        }
    }
}
