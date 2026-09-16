package com.ivanzhao.Idesign.My.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
//程序运行的时候仍然保留这个注解
//AOP 需要在运行时读取 @DoDoor 的信息
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyDoDoor {
    String key() default "";
    String returnJson() default "";
}
