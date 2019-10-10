package com.zhou.utils.scheduler;

import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/4/18
 * description : com.polycis.mqtt.scheduler
 */
public class Scheduler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /**每隔2秒执行一次*/
    @Scheduled(fixedRate = 2000)
    public void testTasks() {
        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
    }

    /*//每天3：05执行
    @Scheduled(cron = "0 05 03 * * ?")
    public void testTasks() {
        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
    }*/

}
