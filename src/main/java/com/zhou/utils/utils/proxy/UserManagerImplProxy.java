package com.zhou.utils.utils.proxy;

/**
 * @author : zhouwenyu
 * @version : 1.0
 * @Package : com.zhou.utils.utils.proxy
 * @Project : java_utils
 * @date : 2019/11/28 16:54
 * 静态代理类（需要与实现类实现同一个接口，弊端）
 */
public class UserManagerImplProxy implements UserManager {

    /**
     * 目标对象
     */
    private UserManager userManager;
    /**
     * 通过构造方法传入目标对象
     */
    public UserManagerImplProxy(UserManager userManager){
        this.userManager=userManager;
    }

    @Override
    public void addUser(String userId, String userName) {
        try{
            //添加打印日志的功能
            //开始添加用户
            System.out.println("start-->addUser()");
            userManager.addUser(userId, userName);
            //添加用户成功
            System.out.println("success-->addUser()");
        }catch(Exception e){
            //添加用户失败
            System.out.println("error-->addUser()");
        }
    }

    @Override
    public void delUser(String userId) {
        userManager.delUser(userId);
    }

    @Override
    public String findUser(String userId) {
        userManager.findUser(userId);
        return "张三";
    }

    @Override
    public void modifyUser(String userId, String userName) {
        userManager.modifyUser(userId,userName);
    }

}
