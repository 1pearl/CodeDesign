package com.ivanzhao.Idesign;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
//同步懒汉式单例模式：没有线程安全问题
public class Singleton_02 {

    // 保存唯一的实例
    private static Singleton_02 instance;

    // 私有构造方法，禁止外部直接 new
    private Singleton_02() {
    }

    // 获取唯一实例
    // static synchronized属于锁住了Singleton_02这个类，不可以并发了
    // synchronized属于锁住了Singleton_02创建的对象，因此还可以并发
    public static synchronized Singleton_02 getInstance() {
        if (instance == null) {
            instance = new Singleton_02();
        }
        return instance;
    }
}
