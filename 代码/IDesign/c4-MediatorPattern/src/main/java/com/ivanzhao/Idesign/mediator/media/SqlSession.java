package com.ivanzhao.Idesign.mediator.media;

import java.util.List;

/**
 * SQL 会话接口 —— 中介者模式中的【抽象中介者（Mediator）】
 * <p>
 * 核心设计思想：
 * 传统 JDBC 操作中，业务类/DAO 需要直接与 DriverManager、Connection、PreparedStatement、ResultSet
 * 以及复杂的 SQL 拼装、结果反射映射强耦合，导致网状复杂的依赖关系。
 * <p>
 * SqlSession 作为中介者，将这些繁琐复杂的底层协作封装起来，为上层提供统一、简洁的数据库操作门面。
 * 外部调用者（Colleague 同事类）只需向中介者发送指令（如传入 statement 标识和参数），
 * 中介者内部协调 SQL 查找、参数绑定、执行查询、结果反射映射等各个协作组件，将结果返回给调用者。
 */
public interface SqlSession {

    /**
     * 查询单条记录（无入参）
     *
     * @param statement MappedStatement 唯一标识（命名空间 + SQL ID）
     * @param <T>       泛型返回值类型
     * @return 映射后的实体对象
     */
    <T> T selectOne(String statement);

    /**
     * 根据参数查询单条记录
     *
     * @param statement MappedStatement 唯一标识（命名空间 + SQL ID）
     * @param parameter 查询参数（支持基本包装类 Long/Integer/String 或 POJO 实体对象）
     * @param <T>       泛型返回值类型
     * @return 映射后的实体对象
     */
    <T> T selectOne(String statement, Object parameter);

    /**
     * 查询多条记录（无入参）
     *
     * @param statement MappedStatement 唯一标识（命名空间 + SQL ID）
     * @param <T>       泛型返回值类型
     * @return 映射后的实体对象列表
     */
    <T> List<T> selectList(String statement);

    /**
     * 根据参数查询多条记录
     *
     * @param statement MappedStatement 唯一标识（命名空间 + SQL ID）
     * @param parameter 查询参数
     * @param <T>       泛型返回值类型
     * @return 映射后的实体对象列表
     */
    <T> List<T> selectList(String statement, Object parameter);

    /**
     * 关闭当前会话，释放底层物理连接资源
     */
    void close();

}