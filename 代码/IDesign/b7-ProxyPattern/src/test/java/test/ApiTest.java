package test;

import com.ivanzhao.Idesign.IUserDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 代理模式集成测试验证用例
 * 
 * ==================== 【验证目标：从 Spring 容器获取并调用代理对象】 ====================
 * 
 * 核心验证链路：
 * 1. 初始化 Spring 上下文：ClassPathXmlApplicationContext("spring-config.xml")
 *    - 容器解析 XML，加载 RegisterBeanFactory 后置处理器；
 *    - 后置处理器向容器注册了 MapperFactoryBean 针对 IUserDao 的 BeanDefinition；
 * 2. 从容器索取 Bean：beanFactory.getBean("userDao", IUserDao.class)
 *    - Spring 发现该 Bean 实现了 FactoryBean，自动调用 MapperFactoryBean.getObject()；
 *    - MapperFactoryBean 通过 Proxy.newProxyInstance() 返回动态生成的 IUserDao 代理对象；
 * 3. 业务调用：userDao.queryUserInfo("100001")
 *    - 调用被 InvocationHandler.invoke() 拦截；
 *    - 拦截器提取 @Select 注解中的 SQL 模板，替换入参并打印 SQL；
 *    - 模拟执行后返回结果。
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_IUserDao() {
        // 步骤 1：加载 Spring 容器配置文件，触发 BeanDefinitionRegistryPostProcessor 后置处理器执行
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring-config.xml");
        
        // 步骤 2：从 Spring 容器中获取名为 "userDao" 的 Bean
        // 【关键点】：此时容器返回的不是 MapperFactoryBean 本身，而是由动态代理生成的实现了 IUserDao 接口的代理对象！
        IUserDao userDao = beanFactory.getBean("userDao", IUserDao.class);
        
        // 步骤 3：打印当前获取到的对象的底层实际 Class 名称（直观见证代理对象的存在）
        logger.info("【代理模式验证】从 Spring 容器获取到的 userDao 实际运行类型：{}", userDao.getClass().getName());
        
        // 步骤 4：调用接口的业务查询方法，此时该调用会被 MapperFactoryBean 中的 InvocationHandler 完全拦截
        String res = userDao.queryUserInfo("100001");
        
        // 步骤 5：输出代理增强后的执行结果
        logger.info("测试结果：{}", res);
    }

}