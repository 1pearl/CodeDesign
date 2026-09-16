package com.ivanzhao.Idesign.My.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Configuration
 * Spring Boot 自动配置类
 * 这个类的核心职责：
 * 1. 让 StarterServiceProperties 能够读取 application.yml 中的配置
 * 2. 根据配置自动创建 StarterService Bean
 * 3. 根据不同条件决定是否创建 StarterService
 * 可以把这个类理解成：
 *     application.yml
 *           ↓
 *     StarterServiceProperties
 *           ↓
 *     StarterAutoConfigure
 *           ↓
 *     StarterService Bean
 * 最终 DoJoinPoint 就可以直接：
 *     @Autowired
 *     private StarterService starterService;
 * 而不需要自己 new StarterService。
 */
@Configuration
/**
 * @EnableConfigurationProperties
 * 作用：
 * 将 StarterServiceProperties 注册到 Spring 容器中，
 * 并启用它的 @ConfigurationProperties 配置绑定功能。
 * StarterServiceProperties 上有：
 *     @ConfigurationProperties("ivanzhao.door")
 * 所以 Spring 会寻找：
 *     ivanzhao.door.xxx
 * 这样的配置，并绑定到 StarterServiceProperties 对象。
 * 例如 application.yml：
 *     ivanzhao:
 *       door:
 *         enabled: true
 *         userStr: 1001,aaaa,ccc
 * 最终：
 *     properties.getUserStr()
 * 可以得到：
 *     "1001,aaaa,ccc"
 * 注意：
 * @ConfigurationProperties 本身主要负责“配置绑定规则”，
 * @EnableConfigurationProperties 则负责让这个配置属性类
 * 被 Spring 注册并启用。
 */
@EnableConfigurationProperties(StarterServiceProperties.class)
/**
 * @ConditionalOnClass
 * 条件注解：
 * 只有当当前运行环境的 classpath 中存在 StarterService.class 时，
 * 这个自动配置类才会生效。
 * 可以简单理解为：
 *     “如果 StarterService 这个类存在，
 *      我才启用这套自动配置。”
 * 这是 Spring Boot 自动配置中非常常见的一种条件判断。
 * 注意：
 * 它不是判断 StarterService Bean 是否存在，
 * 而是判断 StarterService 这个“类”是否存在。
 */
@ConditionalOnClass(StarterService.class)
public class StarterAutoConfigure {

    /**
     * 注入 StarterServiceProperties。
     * 这个对象中的数据来自 application.yml。
     * 例如：
     * application.yml：
     *     ivanzhao:
     *       door:
     *         userStr: 1001,aaaa,ccc
     * Spring 完成配置绑定后：
     *     properties.getUserStr()
     * 得到：
     *     "1001,aaaa,ccc"
     */
    @Autowired
    private StarterServiceProperties properties;


    /**
     * @Bean
     * 告诉 Spring：
     * “请执行这个方法，并把这个方法返回的对象
     *  注册到 Spring IoC 容器中。”
     * 所以：
     *     return new StarterService(...);
     * 创建出来的 StarterService，
     * 最终会成为 Spring 容器中的一个 Bean。
     * 之后其他 Spring 管理的类就可以：
     *     @Autowired
     *     private StarterService starterService;
     * 直接使用它。
     * --------------------------------------------------
     * @ConditionalOnMissingBean
     * 意思是
     * “只有 Spring 容器中还没有 StarterService Bean 时，
     *  我才创建这个 Bean。”
     * 为什么？
     * 因为这是一个 Starter 的“默认配置”。
     * 正常情况下：
     *     用户没有自己提供 StarterService
     *             ↓
     *     自动配置帮用户创建
     * 如果用户自己提供：
     *     @Bean
     *     StarterService starterService() {
     *         ...
     *     }
     * 那么：
     *     用户自己的 Bean
     *             ↓
     *     优先使用
     *     自动配置
     *             ↓
     *     不再重复创建
     * 这体现的是 Spring Boot Starter 的一个重要思想：
     *     “提供默认配置，但允许用户覆盖。”
     * --------------------------------------------------
     * @ConditionalOnProperty
     * 根据 application.yml 中的配置决定
     * 是否创建 StarterService。
     * prefix = "ivanzhao.door"
     *     ↓
     *     指定配置前缀
     * value = "enabled"
     *     ↓
     *     指定具体配置项
     * havingValue = "true"
     *     ↓
     *     要求这个配置项的值必须是 true
     * 所以它实际检查的是：
     *     ivanzhao.door.enabled == true
     * 例如：
     *     ivanzhao:
     *       door:
     *         enabled: true
     *     → 创建 StarterService
     * 如果：
     *     ivanzhao:
     *       door:
     *         enabled: false
     *     → 不创建 StarterService
     * 这样用户就可以通过配置文件控制
     * 这个功能是否启用。
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "ivanzhao.door",
            value = "enabled",
            havingValue = "true"
    )
    StarterService starterService() {
        /**
         * properties.getUserStr()
         * 从 StarterServiceProperties 中取得
         * application.yml 配置的白名单字符串。
         * 例如：
         *     "1001,aaaa,ccc"
         * 然后传给 StarterService 的构造方法：
         *     new StarterService("1001,aaaa,ccc")
         * 最终这个 StarterService 会被 Spring 注册为 Bean。
         */
        return new StarterService(properties.getUserStr());
    }
}
