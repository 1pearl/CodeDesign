package com.ivanzhao.IDesign.abstractFactory.factory;

import com.ivanzhao.IDesign.abstractFactory.workshop.ICacheAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class JDKProxyFactory {

    /**
     * 传进来一个“我要什么接口”和一个“具体使用哪个缓存实现”，然后帮我动态生成一个实现这个接口的代理对象。
     * @param cacheClazz T 这个类型对应的 Class 对象。
     * @param cacheAdapter 某个 ICacheAdapter 的子类型，但具体是谁我不知道
     * @return
     * @param <T>
     * @throws Exception
     */
    public static <T> T getProxy(Class<T> cacheClazz,Class<? extends ICacheAdapter> cacheAdapter) throws Exception {
        // cacheAdapter 是一个 Class 对象，
        // 例如传入 EGM.class，那么 cacheAdapter 就是 EGM.class
        // cacheAdapter.newInstance()
        // 相当于 new EGM()
        // 得到的是 EGM 的实例对象。
        // 因为 EGM implements ICacheAdapter，
        // 所以这个 EGM 对象可以当作 ICacheAdapter 使用。
        // 最终把这个“真实干活的对象”交给 JDKInvocationHandler 保存起来。
        InvocationHandler handler =
                new JDKInvocationHandler(cacheAdapter.newInstance());


        // 获取当前线程的上下文 ClassLoader（类加载器）。
        // 后面 Proxy.newProxyInstance() 创建 JDK 动态代理对象时需要使用这个类加载器，
        // 用它来加载/生成运行时创建的代理类。
        ClassLoader classLoader =
                Thread.currentThread().getContextClassLoader();


        // Proxy 是 java.lang.reflect.Proxy 类，
        // 它是 JDK 提供的一个用于创建“动态代理对象”的工具类。
        //
        // Proxy.newProxyInstance(...) 是一个静态方法，
        // 不需要 new Proxy，直接通过 Proxy 类调用。
        //
        // 第1个参数：
        // classLoader
        // → 指定使用哪个类加载器来创建/加载代理类。
        //
        // 第2个参数：
        // new Class[]{cacheClazz}
        // → 指定这个代理对象需要实现哪些接口。
        //
        // 如果 cacheClazz = ICacheAdapter.class，
        // 那么相当于：
        // new Class[]{ICacheAdapter.class}
        // 表示：创建出来的代理对象实现 ICacheAdapter 接口。
        //
        // 第3个参数：
        // handler
        // → 指定这个代理对象收到方法调用之后，
        //   交给哪个 InvocationHandler 处理。
        //
        // 最终：
        // Proxy.newProxyInstance(...)
        // → 动态创建一个“代理对象”
        // → 这个代理对象实现 cacheClazz 指定的接口
        // → 调用代理对象的方法时，会进入 handler.invoke(...)
        //
        // Proxy.newProxyInstance() 返回的是 Object，
        // 所以这里通过 (T) 转换成 T 类型再返回。
        return (T) Proxy.newProxyInstance(
                classLoader,
                new Class[]{cacheClazz},
                handler
        );
    }
    //我想使用 ICacheAdapter
    //        ↓
    //但我不想自己决定到底是 EGM 还是 IIR
    //        ↓
    //getProxy(...)
    //        ↓
    //帮我生成一个代理
    //        ↓
    //代理背后连接 EGM / IIR
}
