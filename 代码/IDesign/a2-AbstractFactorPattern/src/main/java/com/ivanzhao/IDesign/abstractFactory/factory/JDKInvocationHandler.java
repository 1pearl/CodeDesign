package com.ivanzhao.IDesign.abstractFactory.factory;

import com.ivanzhao.IDesign.abstractFactory.util.ClassLoaderUtils;
import com.ivanzhao.IDesign.abstractFactory.workshop.ICacheAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class JDKInvocationHandler implements InvocationHandler {

    // 保存真正干活的对象。
    // 注意：
    // 这里虽然声明成 ICacheAdapter 类型，
    // 但实际存进去的是具体实现类的对象，例如：
    // ICacheAdapter cacheAdapter = new EGM();
    // 或
    // ICacheAdapter cacheAdapter = new IIR();
    // 所以：
    // “变量的类型”是 ICacheAdapter，
    // “实际对象的类型”可能是 EGM / IIR。
    private ICacheAdapter cacheAdapter;


    public JDKInvocationHandler(ICacheAdapter cacheAdapter) {

        // 把真正干活的具体对象保存下来。
        // 例如：
        // new JDKInvocationHandler(new EGM());
        // 那么这里的 this.cacheAdapter 实际指向 EGM 对象。
        this.cacheAdapter = cacheAdapter;
    }


    /**
     * 当代理对象调用某个方法时，JDK 会自动进入这里。
     * 例如：
     * ICacheAdapter proxy = getProxy(ICacheAdapter.class, EGM.class);
     * proxy.get("abc");
     * 那么 JDK 动态代理就会自动调用：
     * invoke(proxy, method, args)
     * 此时：
     * proxy  = JDK 创建的代理对象
     * method = get 方法对应的 Method 对象
     * args   = {"abc"}，实际传入的参数
     * 本方法要做的事情就是：
     * 1. 根据 method 的方法名和 args 的参数类型，在 ICacheAdapter 接口中找到对应的 Method。
     * 2. 在真正的 cacheAdapter 对象上执行这个 Method。
     * 3. 把真实方法执行后的返回值返回给代理对象。
     */
    @Override
    public Object invoke(
            Object proxy,
            Method method,
            Object[] args
    ) throws Throwable {

        /*
         * 第一步：
         * ICacheAdapter.class
         * 得到 ICacheAdapter 接口对应的 Class 对象。
         * 注意：
         * Class 对象不是“方法的返回值”，
         * 而是用来代表 ICacheAdapter 这个接口，
         * 后面通过它进行反射操作。
         */
        return ICacheAdapter.class
                /*
                 * 第二步：
                 * getMethod(方法名, 参数类型)
                 * 根据：method.getName() → 得到方法名
                 * ClassLoaderUtils.getClazzByArgs(args) → 得到方法的参数类型
                 * 然后在 ICacheAdapter 接口中找到对应的 Method 对象。
                 * 例如：method.getName() → "get"
                 * ClassLoaderUtils.getClazzByArgs(args) → {String.class}
                 * 最终相当于：
                 * ICacheAdapter.class.getMethod("get",String.class);
                 * 得到：Method 对象
                 */
                .getMethod(
                        method.getName(),
                        ClassLoaderUtils.getClazzByArgs(args)
                )

                /*
                 * 第三步：
                 * invoke(对象, 实际参数)
                 * 在 cacheAdapter 这个真实对象上，
                 * 执行刚才通过反射找到的 Method。
                 * 第一个参数：
                 * cacheAdapter → 指定“在哪个对象上执行”
                 * 第二个参数：args → 指定“执行时传入什么实际参数”
                 * 例如：
                 * cacheAdapter = new EGM();
                 * args = {"abc"};
                 * 那么这里最终相当于：
                 * EGM.get("abc");
                 * 如果 EGM.get("abc") 返回 "hello"，
                 * 那么 invoke(...) 就返回 "hello"。
                 * 如果真实方法是 void，
                 * invoke(...) 执行完成后返回 null。
                 */
                .invoke(cacheAdapter, args);
    }
}
