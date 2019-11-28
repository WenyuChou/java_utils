静态代理：
 
具体用户管理实现类:

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

代理类--代理用户管理实现类

public class UserManagerImplProxy implements UserManager {  
  
    // 目标对象  
    private UserManager userManager;  
    // 通过构造方法传入目标对象  
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

客户端调用

public class Client {  
  
    public static void main(String[] args){  
        //UserManager userManager=new UserManagerImpl();  
        UserManager userManager=new UserManagerImplProxy(new UserManagerImpl());  
        userManager.addUser("1111", "张三");  
    }  
}  



动态代理：
 
具体实现类

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

动态创建代理对象的类

//动态代理类只能代理接口（不支持抽象类），代理类都需要实现InvocationHandler类，实现invoke方法。该invoke方法就是调用被代理接口的所有方法时需要调用的，该invoke方法返回的值是被代理接口的一个实现类  
     
public class LogHandler implements InvocationHandler {  
  
    // 目标对象  
    private Object targetObject;  
    //绑定关系，也就是关联到哪个接口（与具体的实现类绑定）的哪些方法将被调用时，执行invoke方法。              
    public Object newProxyInstance(Object targetObject){  
        this.targetObject=targetObject;  
        //该方法用于为指定类装载器、一组接口及调用处理器生成动态代理类实例    
        //第一个参数指定产生代理对象的类加载器，需要将其指定为和目标对象同一个类加载器  
        //第二个参数要实现和目标对象一样的接口，所以只需要拿到目标对象的实现接口  
        //第三个参数表明这些被拦截的方法在被拦截时需要执行哪个InvocationHandler的invoke方法  
        //根据传入的目标返回一个代理对象  
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),  
                targetObject.getClass().getInterfaces(),this);  
    }  
    @Override  
    //关联的这个实现类的方法被调用时将被执行  
    /*InvocationHandler接口的方法，proxy表示代理，method表示原对象被调用的方法，args表示方法的参数*/  
    public Object invoke(Object proxy, Method method, Object[] args)  
            throws Throwable {  
        System.out.println("start-->>");  
        for(int i=0;i<args.length;i++){  
            System.out.println(args[i]);  
        }  
        Object ret=null;  
        try{  
            /*原对象方法调用前处理日志信息*/  
            System.out.println("satrt-->>");  
              
            //调用目标方法  
            ret=method.invoke(targetObject, args);  
            /*原对象方法调用后处理日志信息*/  
            System.out.println("success-->>");  
        }catch(Exception e){  
            e.printStackTrace();  
            System.out.println("error-->>");  
            throw e;  
        }  
        return ret;  
    }  
  
} 

被代理对象targetObject通过参数传递进来，我们通过targetObject.getClass().getClassLoader()获取ClassLoader对象，然后通过targetObject.getClass().getInterfaces()获取它实现的所有接口，然后将targetObject包装到实现了InvocationHandler接口的LogHandler对象中。通过newProxyInstance函数我们就获得了一个动态代理对象。

客户端代码

public class Client {  
  
    public static void main(String[] args){  
        LogHandler logHandler=new LogHandler();  
        UserManager userManager=(UserManager)logHandler.newProxyInstance(new UserManagerImpl());  
        //UserManager userManager=new UserManagerImpl();  
        userManager.addUser("1111", "张三");  
    }  
}  


可以看到，我们可以通过LogHandler代理不同类型的对象，如果我们把对外的接口都通过动态代理来实现，那么所有的函数调用最终都会经过invoke函数的转发，因此我们就可以在这里做一些自己想做的操作，比如日志系统、事务、拦截器、权限控制等。这也就是AOP(面向切面编程)的基本原理。

 AOP（AspectOrientedProgramming）：将日志记录，性能统计，安全控制，事务处理，异常处理等代码从业务逻辑代码中划分出来，通过对这些行为的分离，我们希望可以将它们独立到非指导业务逻辑的方法中，进而改变这些行为的时候不影响业务逻辑的代码---解耦。