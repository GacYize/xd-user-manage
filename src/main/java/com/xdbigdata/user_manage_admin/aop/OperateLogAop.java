package com.xdbigdata.user_manage_admin.aop;

import com.xdbigdata.user_manage_admin.annotation.OperateLog;
import com.xdbigdata.user_manage_admin.service.OperateLogService;
import com.xdbigdata.user_manage_admin.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
/**
 * @创建人:huangjianfeng
 * @简要描述:切面实现日志填写
 * @创建时间: 2018/10/25 10:45
 * @参数:
 * @返回:
 */
public class OperateLogAop {

    @Autowired
    private OperateLogService operateLogService;

    @Pointcut("execution(public * com.xdbigdata.user_manage_admin.service.impl..*.*(..))")
    public void pointCut() {
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
        //System.out.println("after aspect executed");
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        //如果需要这里可以取出参数进行处理

    }

    @AfterReturning(pointcut = "pointCut()", returning = "returnVal")
    public void afterReturning(JoinPoint joinPoint, Object returnVal) {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //System.out.println("拦截到了" + pjp.getSignature().getName() +"方法...");
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();

        Class clazz = targetMethod.getDeclaringClass();

        Method realMethod = pjp.getTarget().getClass().getDeclaredMethod(signature.getName(), targetMethod.getParameterTypes());
        if(!realMethod.isAnnotationPresent(OperateLog.class)){
            return pjp.proceed();
        }
        OperateLog operateLog = (OperateLog)realMethod.getAnnotation(OperateLog.class);
        Object obj = null;
        try {
            obj = pjp.proceed();
        } catch (Throwable ex) {
            throw ex;
        }
        String detail=ContextUtil.LOG_THREADLOCAL.get();
        if(StringUtils.isNotEmpty(detail)) {
            operateLogService.addOperateLog(operateLog.module(),detail);
        }
        return obj;
    }

    @AfterThrowing(pointcut = "pointCut()", throwing = "error")
    public void afterThrowing(JoinPoint jp, Throwable error) {

    }

}