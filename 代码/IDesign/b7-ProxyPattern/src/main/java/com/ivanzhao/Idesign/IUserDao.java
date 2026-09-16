package com.ivanzhao.Idesign;

import com.ivanzhao.Idesign.agent.Select;

/**
 * 用户数据访问接口（DAO）
 * 
 * ==================== 【代理模式：抽象主题角色 (Subject)】 ====================
 * 1. 角色定义：代理模式中的抽象接口，定义了客户端所需调用的业务方法契约。
 * 2. 核心特点：
 *    - 开发者只需要定义接口方法和 SQL 映射注解（如 @Select），无需编写具体的实现类（如 UserDaoImpl）；
 *    - 真实的业务逻辑由代理工厂（MapperFactoryBean）在运行时动态生成代理对象（Proxy）去执行；
 *    - 这种设计思想正是 MyBatis、Spring Data JPA 等现代持久层框架能够实现“写接口即用”的灵魂所在。
 */
public interface IUserDao {

    /**
     * 根据用户ID查询用户姓名
     * 
     * @param uId 用户ID
     * @return 用户名称
     * 
     * 注解说明：
     * @Select 注解标注在接口方法上，携带了待执行的 SQL 模板元数据。
     * 运行期间将被代理对象的 InvocationHandler 反射提取并绑定参数执行。
     */
    @Select("select userName from user where id = #{uId}")
    String queryUserInfo(String uId);

}