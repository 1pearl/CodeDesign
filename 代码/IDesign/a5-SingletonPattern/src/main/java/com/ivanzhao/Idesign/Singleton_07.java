package com.ivanzhao.Idesign;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
/**
 * 枚举单例模式
 *
 * Java 会保证一个枚举类型中的每一个枚举常量在整个 JVM 中只有一个实例。
 */
public enum Singleton_07 {

    // 枚举常量 INSTANCE
    // INSTANCE 本身就是 Singleton_07 类型的唯一对象。
    // 可以理解为
    // Singleton_07 INSTANCE = new Singleton_07();
    // 但这个对象的创建和管理由 JVM / Java 的枚举机制负责，
    // 我们不能自己 new Singleton_07()。
    INSTANCE;
    // 单例对象的普通实例方法
    public void test() {
        System.out.println("hi~");
    }

}
