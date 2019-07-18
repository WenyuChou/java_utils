package com.zhou.utils.controller;

import com.zhou.utils.service.TestService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/7/18
 * description : 描述
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestService service;

    /**
     * 悲观锁实现🔒
     *
     * @param id   数据id
     * @param lock 上锁后是否等待，0-不等待 / 1-等待10秒钟
     * @return
     */
    @GetMapping("/lock")
    public Map<String, Object> lock(Integer id, Integer lock) {
        return service.lockTest(id, lock);
    }
}
