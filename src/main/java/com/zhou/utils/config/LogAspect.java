package com.zhou.utils.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.TreeMap;

/**
 * 请求响应日志
 * @author wenyu zhou
 * @date 2020-04-08 17：03
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Around("execution(* com.zhou.utils.controller..*.*(..)))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();
        TreeMap<String,Object> logMap = printRequestLog(joinPoint);
        Object restObj = joinPoint.proceed();
        printResponseLog(logMap,startTime,restObj);
        return restObj;
    }

    private void printResponseLog(TreeMap<String,Object> logMap, Long startTime, Object restObj) {
        try {
            Long endTime = System.currentTimeMillis();
            logMap.put("q_opt_duration", (endTime - startTime));
            logMap.put("q_opt_result", restObj);
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String authorization = request.getHeader("authorization");
            if(authorization != null){
                logMap.put("authorization",authorization);
            }
            log.info(" <== response:" + JSON.toJSONString(logMap));
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
        }
    }

    private TreeMap<String,Object> printRequestLog(ProceedingJoinPoint joinPoint) {
        TreeMap<String,Object> logMap = new TreeMap<>();
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Object[] args = joinPoint.getArgs();
            String url = request.getRequestURL().toString();
            logMap.put("q_req_url", url);
            logMap.put("q_req_args", Arrays.toString(args));
            String requestInfo = JSON.toJSONString(logMap);
            log.info("==> request:" + requestInfo);
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
        return logMap;
    }
}
