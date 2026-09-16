package com.ivanzhao.Idesign;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
//双重检查锁单例(懒汉模式)
public class Singleton_05 {

    // 静态成员变量：整个类共享这一份 instance 引用
    // volatile 有两个重要作用：
    // 1. 保证一个线程对 instance 的修改能够被其他线程及时看到
    // 2. 禁止 JVM 对 instance = new Singleton_05() 相关的内存操作进行
    //    某些可能导致“对象尚未完全初始化就被其他线程看到”的重排序
    // 初始值为 null，说明此时还没有创建 Singleton_05 对象
    //禁止与对象发布相关的危险重排序，保证其他线程不会通过一个已经变成非 null 的引用看到一个尚未完成初始化的对象。
    private static volatile Singleton_05 instance;

    // 私有构造方法：禁止外部通过 new 创建 Singleton_05 对象
    private Singleton_05() {
    }

    // 对外提供唯一的实例获取入口
    public static Singleton_05 getInstance() {
        // 第一次检查：
        // 如果 instance 已经创建好了，就直接返回
        // 绝大多数情况下，单例对象已经存在，
        // 因此不需要进入 synchronized，减少加锁开销
        if (null != instance) {
            return instance;
        }
        // 第一次检查发现 instance == null，
        // 说明单例可能还没有创建
        // 对 Singleton_05.class 加锁，
        // 保证同一时刻只有一个线程能够进入下面的代码
        synchronized (Singleton_05.class) {
            // 第二次检查：
            // 线程进入锁之前，可能已经有其他线程创建好了 instance
            // 所以进入 synchronized 后必须再次检查
            if (null == instance) {
                // 确认还没有创建对象，
                // 当前线程负责创建唯一的 Singleton_05 对象
                instance = new Singleton_05();
            }
        }
        // 返回已经创建好的单例对象
        return instance;
    }
}