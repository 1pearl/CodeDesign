package com.ivanzhao.Idesign.mediator.media;

/**
 * SQL 会话工厂接口（中介者创建工厂）
 * <p>
 * 负责创建并提供中介者实例（SqlSession），将中介者的创建与使用解耦。
 */
public interface SqlSessionFactory {

    /**
     * 开启并获取一个中介者会话实例（SqlSession）
     *
     * @return 具体中介者实例
     */
    SqlSession openSession();

}