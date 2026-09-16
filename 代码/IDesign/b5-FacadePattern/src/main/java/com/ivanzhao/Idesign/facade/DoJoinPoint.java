package com.ivanzhao.Idesign.facade;

import com.alibaba.fastjson.JSON;
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

@Aspect
@Component
public class DoJoinPoint {
    private Logger logger = LoggerFactory.getLogger(DoJoinPoint.class);

    @Autowired
    private StarterService starterService;

    @Pointcut("@annotation(com.ivanzhao.Idesign.facade.annotation.DoDoor)")
    public void aopPoint() {

    }
    //class HelloWorldController {
    //@MyDoDoor(
    //    key = "userId",
    //    returnJson = "{\"code\":\"1111\",\"info\":\"非白名单可访问用户拦截！\"}"
    //)
    //public UserInfo queryUserInfo(String userId) {
    //
    //    return new UserInfo(
    //        "虫虫:" + userId,
    //        19,
    //        "天津市南开区"
    //    );
    //}

    @Around("aopPoint()")
    public Object doRouter(ProceedingJoinPoint jp) throws Throwable {
        //
        //jp代表：代表的就是这一次 queryUserInfo("1001") 方法调用的上下文
        //目标对象       HelloWorldController
        //目标方法       queryUserInfo
        //方法参数       "1001"
        Method method = getMethod(jp);//queryUserInfo
        DoDoor door = method.getAnnotation(DoDoor.class);//从这个 Method 对象上，把 MyDoDoor 注解取出来
        //@DoDoor(key = "userId", returnJson = "{\"code\":\"1111\",\"info\":\"非白名单可访问用户拦截！\"}")
        String keyValue = getFiledValue(door.key(), jp.getArgs());
        //door.key()     → "userId"
        //jp.getArgs()   → ["1001"]
        //keyValue       → "1001"
        logger.info("itstack door handler method：{} value：{}", method.getName(), keyValue);
        if (null == keyValue || "".equals(keyValue)) return jp.proceed();
        String[] split = starterService.split(",");
        // 白名单过滤
        for (String str : split) {
            if (keyValue.equals(str)) {
                return jp.proceed();
            }
        }
        // 拦截
        return returnObject(door, method);
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
    private Object returnObject(DoDoor doGate, Method method) throws IllegalAccessException, InstantiationException {
        Class<?> returnType = method.getReturnType();
        String returnJson = doGate.returnJson();
        if ("".equals(returnJson)) {
            return returnType.newInstance();
        }
        return JSON.parseObject(returnJson, returnType);
    }

    //获取属性值:从 AOP 拦截到的方法参数 args 中，找到指定字段 filed 对应的值，并返回成 String
    //请你去 args 里面一个一个找，看看有没有哪个对象拥有名叫 filed 的属性
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