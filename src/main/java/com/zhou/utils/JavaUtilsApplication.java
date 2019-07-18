package com.zhou.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Wenyu Zhou
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class JavaUtilsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaUtilsApplication.class, args);
    }

}
