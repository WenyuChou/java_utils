package com.zhou.utils.utils.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/4/26
 * description : com.polycis.api.mqtt.common.thread
 */
@Slf4j
@Component
public class RunThread {

    @Async("newPool")
    public void getTime(){
        for (int i = 0; i < 10; i++) {
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String now = s.format(new Date());
            log.info(now);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
