package com.zhou.utils.utils.proxy;

/**
 * @author : zhouwenyu
 * @version : 1.0
 * @Package : com.zhou.utils.utils.proxy
 * @Project : java_utils
 * @date : 2019/11/28 16:52
 * 实现类
 */
public class UserManagerImpl implements UserManager {

    @Override
    public void addUser(String userId, String userName) {
        System.out.println("UserManagerImpl.addUser");
    }

    @Override
    public void delUser(String userId) {
        System.out.println("UserManagerImpl.delUser");
    }

    @Override
    public String findUser(String userId) {
        System.out.println("UserManagerImpl.findUser");
        return "张三";
    }

    @Override
    public void modifyUser(String userId, String userName) {
        System.out.println("UserManagerImpl.modifyUser");

    }
}
