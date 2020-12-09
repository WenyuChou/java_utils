package com.zhou.utils.annotation;

import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author wenyu zhou
 * @version 2020-11-13
 */
@Aspect
@Component
public class ParamInterceptor {
    @Before("execution(* com.zhou.utils.controller.*.*(..)))")
    public void before(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            Class cls = arg.getClass();
            if (cls.isAnnotationPresent(CheckParam.class)) {
                checkParam(arg);
            }
        }
    }

    public void checkParam(Object object) {
        Class tClass = object.getClass();
        Field[] fields = tClass.getDeclaredFields();
        for (Field item : fields) {
            if (item.isAnnotationPresent(CheckBlank.class)) {
                checkBlank(item, object);
            }
        }
    }

    @SneakyThrows
    private void checkBlank(Field item, Object object) {
        //得到私有
        item.setAccessible(true);
        //获取属性
        String name = item.getName();
        //获取属性值
        Object value = item.get(object);
        if (value == null || StringUtils.isBlank(String.valueOf(value))) {
            String desc = item.getAnnotation(CheckBlank.class).desc();
            throw new RuntimeException("参数:" + desc + "(" + name + ")不可为空");
        }
    }
}
