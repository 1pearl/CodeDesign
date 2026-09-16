package com.ivanzhao.Idesign.agent;

import com.ivanzhao.Idesign.IUserDao;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * 动态注册 BeanDefinition 工厂后置处理器
 * 
 * ==================== 【代理对象在 Spring 容器中的自动化装配与注册桥梁】 ====================
 * 
 * 1. 为什么需要这个后置处理器？
 *    - 在常规开发中，我们只编写了 IUserDao 接口，并没有具体的实现类（如 UserDaoImpl），
 *      因此无法直接在 XML 中配置 <bean class="com.ivanzhao.Idesign.IUserDaoImpl"/>。
 *    - 为了让 Spring 容器中拥有一个可以被 @Autowired 或 getBean("userDao") 注入的实例，
 *      我们需要在 Spring 启动扫描阶段，动态向 BeanDefinitionRegistry（Bean定义注册表）中
 *      注册一个 GenericBeanDefinition。
 * 
 * 2. 代理模式是如何在此处发挥威力的？【重点】
 *    - 我们把注册的 BeanClass 指定为我们自定义的代理工厂 Bean：MapperFactoryBean.class；
 *    - 并把待代理的目标接口 IUserDao.class 作为构造参数传入；
 *    - 当 Spring 容器后续去实例化并获取 "userDao" 这个 Bean 时，因为 MapperFactoryBean 实现了 FactoryBean 接口，
 *      Spring 会自动调用其 getObject() 方法，最终注入到 Spring 容器中的**正是由动态代理技术生成的 IUserDao 代理对象**！
 *    - 这正是 MyBatis-Spring 中 @MapperScan 和 ClassPathMapperScanner 的底层核心原理！
 */
public class RegisterBeanFactory implements BeanDefinitionRegistryPostProcessor {

    /**
     * 在所有 Bean 定义加载完成后、但在 Bean 实例化之前触发的回调
     * 允许在此处编程式地动态添加、修改或注册新的 BeanDefinition
     * 
     * @param beanDefinitionRegistry Bean 定义注册器
     * @throws BeansException 异常
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        // -------------------------------------------------------------
        // 步骤 1：创建一个通用的 BeanDefinition 元数据定义对象
        // -------------------------------------------------------------
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        
        // -------------------------------------------------------------
        // 步骤 2：将 Bean 的 Class 类型指向自定义的代理工厂 MapperFactoryBean
        // 【核心注意】：此处使用的是当前包下的 com.ivanzhao.Idesign.agent.MapperFactoryBean
        // -------------------------------------------------------------
        beanDefinition.setBeanClass(MapperFactoryBean.class);
        
        // 步骤 3：设置作用域为单例 singleton
        beanDefinition.setScope("singleton");
        
        // -------------------------------------------------------------
        // 步骤 4：注入构造函数参数
        // 相当于 <constructor-arg value="com.ivanzhao.Idesign.IUserDao"/>
        // 将目标接口 IUserDao 传入 MapperFactoryBean(Class<T> mapperInterface) 构造方法
        // -------------------------------------------------------------
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(IUserDao.class);

        // -------------------------------------------------------------
        // 步骤 5：创建 BeanDefinitionHolder，为该 Bean 指定名称为 "userDao"
        // -------------------------------------------------------------
        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(beanDefinition, "userDao");
        
        // -------------------------------------------------------------
        // 步骤 6：将构建好的 Bean 定义正式注册到 Spring 上下文容器注册表中
        // 此时，Spring 容器就知道了存在一个名为 "userDao" 的 Bean，其由 MapperFactoryBean 负责生成
        // -------------------------------------------------------------
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, beanDefinitionRegistry);
    }

    /**
     * BeanFactory 的标准后置处理，此处无需额外操作
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        // left blank intentionally
    }

}