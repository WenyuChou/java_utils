package com.zhou.utils.utils.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/4/26
 * description : com.polycis.api.mqtt.common
 */
@Configuration
@EnableAsync
public class ExecutorPool {
    private int corePoolSize = 10;

    private int maxPoolSize = 50;

    private int queueSize = 10;

    private int keepAlive = 60;

    @Bean
    public Executor newPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueSize);
        executor.setKeepAliveSeconds(keepAlive);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.setThreadNamePrefix("newPool thread -");
        executor.initialize();
        return executor;
    }

}
