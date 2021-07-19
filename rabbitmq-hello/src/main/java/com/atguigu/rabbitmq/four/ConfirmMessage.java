package com.atguigu.rabbitmq.four;

import com.atguigu.rabbitmq.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Zx
 * @Date 2021/7/6 10:09
 */
public class ConfirmMessage {

    public static final int MESSAGE_COUNT = 1000;

    //单个确认
    public static void publishMessageSingle() throws Exception{
        Channel channel = RabbitmqUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        //开启确认
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        for(int i = 0;i < MESSAGE_COUNT; i++){
            String message = i + "";
            channel.basicPublish("",queueName,null, message.getBytes());
            boolean flag = channel.waitForConfirms();
            if(flag){
                System.out.println("消息发送成功");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "条消息单独确认，共耗时：" + (end - begin) + "毫秒");
    }

    //批量确认
    public static void publishMessageMultiple() throws Exception{
        Channel channel = RabbitmqUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        //开启确认
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        int batchSize = 100;
        for(int i = 0;i < MESSAGE_COUNT; i++){
            String message = i + "";
            channel.basicPublish("",queueName,null, message.getBytes());
            if(i / batchSize == 0){
                channel.waitForConfirms();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "条消息批量确认，共耗时：" + (end - begin) + "毫秒");
    }

    //异步确认
    public static void publishMessageAsync() throws Exception{
        Channel channel = RabbitmqUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        //开启确认
        channel.confirmSelect();
        long begin = System.currentTimeMillis();

        ConcurrentSkipListMap confirmMap = new ConcurrentSkipListMap();
        ConfirmCallback ackCallback = (deliveryTag, multiple) ->{
            if(multiple){
                ConcurrentNavigableMap concurrentNavigableMap = confirmMap.headMap(deliveryTag);
                concurrentNavigableMap.clear();
            }else{
                confirmMap.remove(deliveryTag);
            }
            System.out.println("确认的消息： " + deliveryTag);
        };
        ConfirmCallback nackCallback = (deliveryTag, multiple) ->{
            System.out.println("未确认的消息： " + deliveryTag);
        };
        channel.addConfirmListener(ackCallback, nackCallback);
        for(int i = 0;i < MESSAGE_COUNT; i++){
            String message = i + "";
            channel.basicPublish("",queueName,null, message.getBytes());

            confirmMap.put(channel.getNextPublishSeqNo(), message);
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "条消息异步确认，共耗时：" + (end - begin) + "毫秒");
    }

    public static void main(String[] args) throws Exception{
        //发布1000条消息单独确认，共耗时：324毫秒
//        publishMessageSingle();
        //发布1000条消息批量确认，共耗时：30毫秒
//        publishMessageMultiple();
        //发布1000条消息异步确认，共耗时：31毫秒
        publishMessageAsync();
    }
}
