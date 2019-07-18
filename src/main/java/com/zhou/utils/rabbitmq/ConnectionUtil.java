package com.zhou.utils.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/5/11
 * description : 描述
 */
public class ConnectionUtil {
    public static Connection getConnection() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("localhost");
        //端口
        factory.setPort(5672);
        //设置账号信息，用户名、密码、vhost
        //factory.setVirtualHost("testhost");
        factory.setUsername("zhou");
        factory.setPassword("zhou");
        // 通过工程获取连接
        Connection connection = factory.newConnection();
        return connection;
    }
}
