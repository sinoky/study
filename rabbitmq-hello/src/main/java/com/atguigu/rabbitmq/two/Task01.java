package com.atguigu.rabbitmq.two;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;

import javax.annotation.PostConstruct;
import java.util.Scanner;

/**
 * @author Zx
 * @Date 2021/7/5 15:34
 */
public class Task01 {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitmqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,false, false, false,null);

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("",QUEUE_NAME,null, message.getBytes());
            System.out.println("发送消息完成:" + message);
        }
    }
}
