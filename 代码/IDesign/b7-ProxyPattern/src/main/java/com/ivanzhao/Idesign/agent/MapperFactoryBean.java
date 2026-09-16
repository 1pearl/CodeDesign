package com.ivanzhao.Idesign.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

/**
 * 通用 Mapper 代理工厂 Bean（MapperFactoryBean）
 * ==================== 【代理模式：核心代理工厂 (Proxy Factory) 与调用拦截器】 ====================
 * 1. 为什么需要这个类？
 *    - 在 MyBatis-Spring 架构中，DAO 接口（如 IUserDao）没有具体的实现类，Spring 无法直接通过 new 或反射创建实例。
 *    - Spring 提供了 FactoryBean 接口，当一个 Bean 实现了 FactoryBean 时，Spring 容器从该 Bean 中获取到的实际对象
 *      不是该 FactoryBean 本身，而是调用其 getObject() 方法返回的对象。
 * 2. 代理模式是如何运用的？【重点】
 *    - 本类通过结合 Spring 的 FactoryBean<T> 机制与动态代理技术，在 getObject() 中动态为 DAO 接口生成代理实例（Proxy Instance）；
 *    - 实现了 InvocationHandler 接口：所有调用 IUserDao 接口的方法，都会被重定向到此 Handler 的 invoke() 方法中；
 *    - 在 invoke() 方法中，获取方法上的 @Select 注解，动态解析并替换 SQL 占位符，模拟真正的数据库查询操作并返回结果；
 *    - 这样调用方在使用 IUserDao 时，根本感觉不到具体实现类的缺失，完全由代理对象透明地完成了请求拦截与业务代理！
 *
 * @param <T> 被代理的接口类型（如 IUserDao）
 */
public class MapperFactoryBean<T> implements FactoryBean<T> {

    private Logger logger = LoggerFactory.getLogger(MapperFactoryBean.class);

    /**
     * 被代理的目标接口 Class 对象（如 IUserDao.class）
     */
    private Class<T> mapperInterface;

    /**
     * 构造函数：注入待代理的目标接口类型
     * 
     * @param mapperInterface 目标 DAO 接口类型
     */
    public MapperFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    /**
     * Spring 容器获取 Bean 实例时的回调方法
     * ==================== 【代理模式的诞生点：动态生成代理对象】 ====================
     * @return 实现了 mapperInterface 接口的动态代理对象（Proxy）
     * @throws Exception 异常
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        // -------------------------------------------------------------
        // 【代理模式运用点 1】：定义方法调用的调用处理器（InvocationHandler）
        // 任何对代理对象方法的调用，都会被拦截并路由到这个 lambda 表达式中执行
        // 参数说明：
        //   - proxy: 运行时动态生成的代理对象本身
        //   - method: 当前被调用的接口方法反射对象（如 IUserDao.queryUserInfo）
        //   - args: 调用该方法时传入的实际参数数组（如 ["100001"]）
        // -------------------------------------------------------------
        InvocationHandler handler = (proxy, method, args) -> {
            // 步骤 1：通过反射提取接口方法上的 @Select 注解元数据
            Select select = method.getAnnotation(Select.class);

            // 步骤 2：动态解析 SQL，将 #{uId} 占位符替换为实际入参
            String rawSql = select.value();
            String parsedSql = rawSql.replace("#{uId}", args[0].toString());
            logger.info("【代理模式拦截执行】提取注解SQL模板并绑定参数，最终执行SQL：{}", parsedSql);

            // 步骤 3：模拟底层 JDBC / 数据库查询逻辑，并构造业务返回值
            // 真实场景下此处会调用 SqlSession.selectOne(statement, parameter) 执行数据库查询
            return args[0] + " 小傅哥，沉淀、分享、成长，让自己和他人都能有所收获！";
        };

        // -------------------------------------------------------------
        // 【代理模式运用点 2】：动态创建代理对象实例
        // 参数 1：类加载器（ClassLoader），负责加载在内存中动态生成的代理类字节码
        // 参数 2：代理类需要实现的接口数组（这里是传入的 mapperInterface，即 IUserDao）
        // 参数 3：调用处理器（handler），用于处理所有方法调用拦截
        // 底层机制：在内存中动态构建一个命名类似 $Proxy0 的全新类，该类实现 mapperInterface，
        //           并在实现方法中将调用全部委托给 handler.invoke()。
        // -------------------------------------------------------------
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{mapperInterface}, handler);
    }

    /**
     * 返回被代理对象的类型（即目标接口类型）
     */
    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }

    /**
     * 控制该代理对象在 Spring 容器中是否为单例
     * @return false 表示每次获取可创建新代理，若为 true 则在 Spring 容器内复用单例
     */
    @Override
    public boolean isSingleton() {
        return false;
    }

}