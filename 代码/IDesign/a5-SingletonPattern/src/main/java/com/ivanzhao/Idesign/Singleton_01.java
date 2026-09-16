package com.ivanzhao.Idesign;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
//懒汉式单例模式：存在线程安全问题
public class Singleton_01 {

    // 静态成员变量：整个类只维护这一份 instance 引用
    // 初始值为 null，此时还没有创建 Singleton_01 对象
    private static Singleton_01 instance;

    // 私有构造方法：禁止外部通过 new 创建对象
    // 对象的创建权被限制在 Singleton_01 类内部
    private Singleton_01() {
    }

    // 静态方法：外部可以直接通过 Singleton_01.getInstance() 获取对象
    public static Singleton_01 getInstance() {

        // 第一次调用时，instance == null
        // 创建 Singleton_01 对象，并让 instance 指向这个对象
        if (instance == null) {
            instance = new Singleton_01();
        }

        // 第一次调用：返回刚创建的对象
        // 后续调用：instance 已经指向原来的对象，直接返回同一个对象
        return instance;
    }
}