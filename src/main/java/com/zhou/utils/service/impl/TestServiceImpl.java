package com.zhou.utils.service.impl;

import com.zhou.utils.dao.TestDao;
import com.zhou.utils.service.TestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/7/18
 * description : 描述
 */
@Service
public class TestServiceImpl implements TestService {
    @Resource
    private TestDao dao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> lockTest(Integer id, Integer lock) {
        Map<String, Object> testMap = dao.lockTest(id);
        if(lock == 1){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return testMap;
    }
}
