package com.ivanzhao.Idesign;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
//懒汉式：静态内部类单例 / Holder 模式
// 利用静态内部类只有在第一次主动使用时才初始化的特性，
// 把单例对象的创建延迟到 getInstance() 第一次调用，同时利用 JVM 的类初始化机制保证线程安全。
public class Singleton_04 {
    // 静态内部类
    // 注意：这里并不会在 Singleton_04 加载时立即创建 Singleton_04 对象
    // 把 Singleton_04 实例的创建时机推迟到 SingletonHolder(静态内部类) 被初始化的时候。
    private static class SingletonHolder {
        // SingletonHolder 初始化时，才创建 Singleton_04 对象
        private static final Singleton_04 instance = new Singleton_04();
    }

    // 私有构造方法：禁止外部通过 new 创建 Singleton_04 对象
    private Singleton_04() {
    }

    // 对外提供唯一的实例获取入口
    public static Singleton_04 getInstance() {
        // 第一次访问 SingletonHolder.instance 时，
        // JVM 会初始化 SingletonHolder 类
        // 初始化过程中执行：
        // new Singleton_04()
        return SingletonHolder.instance;
    }
}
