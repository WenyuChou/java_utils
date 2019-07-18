package com.zhou.utils.rabbitmq;

import com.rabbitmq.client.*;


/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/5/11
 * description : 描述
 */
public class Recv {
    private final static String QUEUE_NAME = "test01";

    public static void main(String[] args) throws Exception {
        //new Recv().listenMq();
        new Recv().getMq();
    }
    public void getMq() throws Exception {

        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(2);
        // 定义队列的消费者
        while(true){
            //如果没有消息，将返回null
            GetResponse getResponse = channel.basicGet(QUEUE_NAME, false);
            if(null!=getResponse){
                System.out.println("received["+getResponse.getEnvelope().getRoutingKey()+"]"+new String(getResponse.getBody()));
                channel.basicAck(getResponse.getEnvelope().getDeliveryTag(), false);
            }else {
                break;
            }
            Thread.sleep(10000);
        }
        channel.close();
        connection.close();
    }
    public void listenMq() throws Exception{
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(2);
        // 定义队列的消费者
        boolean autoAck = false;
        // 监听队列
        channel.basicConsume(QUEUE_NAME, autoAck, new QueueingConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                try {
                    Thread.sleep(1000);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(envelope.getRoutingKey() + " 接收到数据:" + new String(body));
            }
        });
    }
}
