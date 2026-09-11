package com.ivanzhao.IDesign.abstractFactory.util;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
/**
 * 遍历所有方法参数，判断每个参数实际是什么对象，然后把它转换成反射 getMethod() 所需要的参数类型数组。
 */
public class ClassLoaderUtils {

    public static Class<?>[] getClazzByArgs(Object[] args) {
        Class<?>[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ArrayList) {
                parameterTypes[i] = List.class;
                continue;
            }
            //因为原方法声明的都是基于List接口实现的类都统一以List为方法类，因此具体类要对齐为List接口对象
            if (args[i] instanceof LinkedList) {
                parameterTypes[i] = List.class;
                continue;
            }
            if (args[i] instanceof HashMap) {
                parameterTypes[i] = Map.class;
                continue;
            }
            //args 里拿到的是实际传入的对象类型，而反射 getMethod() 要的是方法声明中的参数类型。
            //long timeout
            //    ↓
            //调用代理方法时
            //    ↓
            //因为 args 是 Object[]
            //    ↓
            //long 自动装箱
            //    ↓
            //Long 对象
            //    ↓
            //放进 Object[] args
            //    ↓
            //instanceof Long 判断出来
            //    ↓
            //ClassLoaderUtils 把“参数类型信息”设置成 long.class
            if (args[i] instanceof Long){
                parameterTypes[i] = long.class;
                continue;
            }
            if (args[i] instanceof Double){
                parameterTypes[i] = double.class;
                continue;
            }
            if (args[i] instanceof TimeUnit){
                parameterTypes[i] = TimeUnit.class;
                continue;
            }
            //如果前面的特殊情况都没有匹配上，就直接获取当前参数对象运行时的实际类型，并把这个 Class 对象放进 parameterTypes[i]。
            parameterTypes[i] = args[i].getClass();
        }
        return parameterTypes;

    }

}
