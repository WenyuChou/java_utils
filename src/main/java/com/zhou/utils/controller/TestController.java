package com.zhou.utils.controller;

import com.github.pagehelper.PageInfo;
import com.zhou.utils.repository.database.User;
import com.zhou.utils.repository.UserRepository;
import com.zhou.utils.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
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

    @Autowired
    private UserRepository repository;
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

    @GetMapping("/getjpa")
    public Page<User> getJpa(int page){
        return repository.findAll(PageRequest.of(page,5,Sort.by(Sort.Order.asc("id"))));
    }
    @GetMapping("/savejpa")
    public void saveJpa(){
        User user = new User();
        user.setName("测试");
        user.setEmail("邮箱");
        user.setCreateTime(new Date());
        repository.save(user);
    }
    @GetMapping("/pagehelper")
    public PageInfo<HashMap<String,Object>> pagehelper(int pageNum, int pageSize){
        return service.selectUser(pageNum, pageSize);
    }
}
