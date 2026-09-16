package com.ivanzhao.Idesign;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
//初始案例，非单例模式
public class Singleton_00 {

    public static Map<String,String> cache = new ConcurrentHashMap<>();

}
