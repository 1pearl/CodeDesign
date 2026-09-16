package com.ivanzhao.Idesign.agent;

import java.lang.annotation.*;

/**
 * 自定义 SQL 查询注解
 * 
 * ==================== 【代理模式中的元数据提供者】 ====================
 * 1. 角色作用：
 *    - 模拟 MyBatis 中的 @Select 注解，将业务 SQL 语句直接绑定在 DAO 接口方法上。
 * 2. 在代理模式中的联动机制：
 *    - 因为 DAO 接口没有具体实现类，代理类在拦截到目标方法调用时（InvocationHandler.invoke），
 *      会通过反射：method.getAnnotation(Select.class) 获取本注解中的 SQL 模板；
 *    - 进而完成动态 SQL 拼接、占位符替换（如 #{uId} -> args[0]）以及底层 JDBC 执行。
 */
@Documented
@Retention(RetentionPolicy.RUNTIME) // 运行时保留，以便动态代理类能够在运行期通过反射读取
@Target({ElementType.METHOD})        // 仅允许标注在接口或类的方法上
public @interface Select {

    /**
     * SQL 语句模板，支持形如 #{uId} 的参数占位符
     * @return 预编译或模板 SQL 字符串
     */
    String value() default "";

}