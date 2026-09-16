package com.ivanzhao.Idesign.mediator.media;

/**
 * 默认 SQL 会话工厂实现类
 * <p>
 * 持有解析后的全局配置 Configuration 对象，每次调用 openSession() 时，
 * 组装并创建具体的会话中介者 DefaultSqlSession 实例。
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    /** 全局配置对象，包含连接、数据源和 SQL 节点映射 */
    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 开启一个 DefaultSqlSession 会话中介者
     *
     * @return 具体中介者 DefaultSqlSession 实例
     */
    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration.connection, configuration.mapperElement);
    }

}