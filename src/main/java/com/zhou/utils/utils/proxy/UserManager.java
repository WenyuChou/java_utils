package com.zhou.utils.utils.proxy;

/**
 * @author : zhouwenyu
 * @version : 1.0
 * @Package : com.zhou.utils.utils.proxy
 * @Project : java_utils
 * @date : 2019/11/28 16:50
 */
public interface UserManager {

    void addUser(String userId, String userName);
    void delUser(String userId);
    String findUser(String userId);
    void modifyUser(String userId, String userName);

}
