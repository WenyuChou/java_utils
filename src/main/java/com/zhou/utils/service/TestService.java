package com.zhou.utils.service;

import java.util.Map;

/**
 * @author : Wenyu Zhou
 * @version : v1.0
 * @date : 2019/7/18
 * description : 描述
 */
public interface TestService {

    Map<String,Object> lockTest(Integer id, Integer lock);
}
