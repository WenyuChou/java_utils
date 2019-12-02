package com.zhou.utils.utils.proxy;

/**
 * @author : zhouwenyu
 * @version : 1.0
 * @date : 2019/11/28 17:04
 */
public class Proxy {

    public static void main(String[] args){

        /*静态代理
        UserManager userManager=new UserManagerImpl();
        UserManager userManager=new UserManagerImplProxy(new UserManagerImpl());
        userManager.addUser("1111", "张三");
        */

        LogHandler logHandler=new LogHandler();
        UserManager userManager=(UserManager)logHandler.newProxyInstance(new UserManagerImpl());
        //UserManager userManager=new UserManagerImpl();
        userManager.findUser("01");
    }

}
