package com.ivanzhao.Idesign;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
//饿汉式单例:类一加载/初始化，就立即创建对象，不管你以后用不用。
//Singleton_03 是饿汉式单例模式。通过 private 构造方法禁止外部创建对象，
// 通过 static final 持有类级别的唯一实例，并通过 getInstance() 提供统一访问入口。
// 实例在类初始化阶段就被创建，因此不需要额外的同步机制，天然具有线程安全性。
public class Singleton_03 {

    // 静态成员变量：整个类只有这一份 instance 引用
    // 类初始化时就创建 Singleton_03 对象
    // 加上final的原因：instance 这个引用初始化之后，不能再重新指向其他对象。
    private static final Singleton_03 instance = new Singleton_03();

    // 私有构造方法：禁止外部通过 new 创建对象
    private Singleton_03() {
    }

    // 静态方法：通过类名直接获取唯一实例
    public static Singleton_03 getInstance() {
        return instance;
    }
}
