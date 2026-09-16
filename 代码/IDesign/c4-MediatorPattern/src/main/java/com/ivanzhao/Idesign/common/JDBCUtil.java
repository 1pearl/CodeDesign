package com.ivanzhao.Idesign.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 原生 JDBC 数据库操作工具类（对比对照组案例）
 * <p>
 * 【未引入中介者模式前的痛点】：
 * 1. 强耦合：业务调用方必须直接与 Driver、Connection、Statement、ResultSet 等底层 JDBC API 进行深度绑定；
 * 2. 代码冗余：每次执行 SQL 都需要重复写加载驱动、获取连接、构建语句、遍历结果集映射字段以及 try-catch-finally 释放资源的代码；
 * 3. 难以维护：SQL 硬编码在 Java 代码中，字段增减或改动会导致大面积的编译或运行时修改。
 * <p>
 * 【中介者模式的解决之道】：
 * 通过设计类似 MyBatis 的简易 ORM 框架，将 SQL 配置抽离到 XML 中，使用 SqlSession 作为中介者。
 * 业务只需调用接口方法，由中介者内部统一调度连接管理、SQL 解析绑定与反射映射，极大降低了组件间的耦合度。
 */
public class JDBCUtil {

    private static Logger logger = LoggerFactory.getLogger(JDBCUtil.class);

    public static final String URL = "jdbc:mysql://127.0.0.1:3306/CodeDesign";
    public static final String USER = "root";
    public static final String PASSWORD = "123456";

    public static void main(String[] args) throws Exception {
        // 1. 加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        // 2. 获得数据库连接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        // 3. 操作数据库，执行 SQL 查询
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT id, name, age, createTime, updateTime FROM user");
        // 4. 手动逐行遍历结果集并提取字段（硬编码强耦合）
        while (resultSet.next()) {
            logger.info("测试结果 姓名：{} 年龄：{}", resultSet.getString("name"), resultSet.getInt("age"));
        }
        // 5. 释放资源
        resultSet.close();
        stmt.close();
        conn.close();
    }

}