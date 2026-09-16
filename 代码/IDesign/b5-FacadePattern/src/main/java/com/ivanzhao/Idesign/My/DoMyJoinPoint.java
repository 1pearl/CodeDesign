package com.ivanzhao.Idesign.My;

import com.alibaba.fastjson.JSON;
import com.ivanzhao.Idesign.My.annotation.MyDoDoor;
import com.ivanzhao.Idesign.facade.annotation.DoDoor;
import com.ivanzhao.Idesign.facade.config.StarterService;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
// 告诉 Spring：这是一个 AOP 切面类，如果没用这个注解和切面有关的注解就失效了，比如@Around
@Aspect
// 告诉 Spring：把这个类交给 Spring 容器管理
@Component
public class DoMyJoinPoint {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StarterService starterService;

    @Pointcut("@annotation(com.ivanzhao.Idesign.My.annotation.MyDoDoor)")
    public void aopPoint() {

    }

    @Around("aopPoint()")
    public Object doRouter(ProceedingJoinPoint jp) throws Throwable {
        Method method = getMethod(jp);
        MyDoDoor myDoDoor = method.getAnnotation(MyDoDoor.class);
        String keyValue = getFiledValue(myDoDoor.key(), jp.getArgs());
        logger.info("method:{} value:{}",method.getName() , keyValue);
        if(null == keyValue || "".equals(keyValue)) return jp.proceed();
        String[] split = starterService.split(",");
        for (String s : split) {
            if(keyValue.equals(s)) {
                return jp.proceed();
            }
        }
        return returnObject(myDoDoor,method);
    }



    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return getClass(jp).getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

    private Class<? extends Object> getClass(JoinPoint jp) throws NoSuchMethodException {
        return jp.getTarget().getClass();
    }

    //返回对象
    private Object returnObject(MyDoDoor doGate, Method method) throws IllegalAccessException, InstantiationException {
        Class<?> returnType = method.getReturnType();
        String returnJson = doGate.returnJson();
        if ("".equals(returnJson)) {
            return returnType.newInstance();
        }
        return JSON.parseObject(returnJson, returnType);
    }

    //获取属性值
    private String getFiledValue(String filed, Object[] args) {
        String filedValue = null;
        for (Object arg : args) {
            try {
                if (null == filedValue || "".equals(filedValue)) {
                    filedValue = BeanUtils.getProperty(arg, filed);
                } else {
                    break;
                }
            } catch (Exception e) {
                if (args.length == 1) {
                    return args[0].toString();
                }
            }
        }
        return filedValue;
    }
}
