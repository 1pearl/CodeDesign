package com.ivanzhao.Idesign.mediator.media;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * 默认 SQL 会话实现类 —— 中介者模式中的【具体中介者（Concrete Mediator）】
 * <p>
 * 职责：
 * 协调各个同事组件完成数据库交互：
 * 1. 映射路由：根据传入的 statement（命名空间 + id）在中介者配置库中找到对应的 XNode；
 * 2. 连接调度：从 Connection 中获取预编译语句 PreparedStatement；
 * 3. 参数解析与填充：根据入参类型（基础类型或 POJO 对象）自动将参数填充到 SQL 占位符 '?' 中；
 * 4. SQL 执行：调用 JDBC 驱动执行查询获取结果集 ResultSet；
 * 5. 结果集 ORM 反射映射：遍历结果集并将每一列通过反射 setXxx 注入到目标实体对象中；
 * 6. 连接生命周期管理：统一关闭并释放连接。
 */
public class DefaultSqlSession implements SqlSession {

    /** 底层数据库物理连接 */
    private Connection connection;

    /** SQL 映射配置节点表（namespace.id -> XNode） */
    private Map<String, XNode> mapperElement;

    public DefaultSqlSession(Connection connection, Map<String, XNode> mapperElement) {
        this.connection = connection;
        this.mapperElement = mapperElement;
    }

    /**
     * 查询单条记录（无入参）
     *
     * @param statement SQL 唯一标识（命名空间 + SQL ID）
     * @param <T>       结果泛型
     * @return 查询结果实体对象，未查到返回 null
     */
    @Override
    public <T> T selectOne(String statement) {
        try {
            XNode xNode = mapperElement.get(statement);
            if (null == xNode) {
                throw new RuntimeException("未能找到 statement 对应的 SQL 映射: " + statement);
            }
            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> objects = resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));
            return (objects != null && !objects.isEmpty()) ? objects.get(0) : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据参数查询单条记录
     *
     * @param statement SQL 唯一标识（命名空间 + SQL ID）
     * @param parameter 查询参数（基础类型或 POJO）
     * @param <T>       结果泛型
     * @return 查询结果实体对象，未查到返回 null
     */
    @Override
    public <T> T selectOne(String statement, Object parameter) {
        XNode xNode = mapperElement.get(statement);
        if (null == xNode) {
            throw new RuntimeException("未能找到 statement 对应的 SQL 映射: " + statement);
        }
        Map<Integer, String> parameterMap = xNode.getParameter();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
            buildParameter(preparedStatement, parameter, parameterMap);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> objects = resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));
            return (objects != null && !objects.isEmpty()) ? objects.get(0) : null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询多条记录（无入参）
     *
     * @param statement SQL 唯一标识（命名空间 + SQL ID）
     * @param <T>       结果泛型
     * @return 查询结果实体列表
     */
    @Override
    public <T> List<T> selectList(String statement) {
        XNode xNode = mapperElement.get(statement);
        if (null == xNode) {
            throw new RuntimeException("未能找到 statement 对应的 SQL 映射: " + statement);
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据参数查询多条记录
     *
     * @param statement SQL 唯一标识（命名空间 + SQL ID）
     * @param parameter 查询参数
     * @param <T>       结果泛型
     * @return 查询结果实体列表
     */
    @Override
    public <T> List<T> selectList(String statement, Object parameter) {
        XNode xNode = mapperElement.get(statement);
        if (null == xNode) {
            throw new RuntimeException("未能找到 statement 对应的 SQL 映射: " + statement);
        }
        Map<Integer, String> parameterMap = xNode.getParameter();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(xNode.getSql());
            buildParameter(preparedStatement, parameter, parameterMap);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet2Obj(resultSet, Class.forName(xNode.getResultType()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 中介者参数填充核心方法：
     * 将调用方传入的入参自动匹配并绑定到 PreparedStatement 的占位符（'?'）中
     *
     * @param preparedStatement JDBC 预编译语句
     * @param parameter         调用方传入的入参对象
     * @param parameterMap      XML 中解析出的占位符位置及参数名字典（如 1 -> "id"）
     * @throws SQLException           SQL异常
     * @throws IllegalAccessException 反射异常
     */
    private void buildParameter(PreparedStatement preparedStatement, Object parameter, Map<Integer, String> parameterMap) throws SQLException, IllegalAccessException {
        int size = parameterMap.size();
        if (parameter == null || size == 0) {
            return;
        }

        // 1. 单个基础数据类型参数处理（Long、Integer、String 等）
        if (parameter instanceof Long) {
            for (int i = 1; i <= size; i++) {
                preparedStatement.setLong(i, Long.parseLong(parameter.toString()));
            }
            return;
        }

        if (parameter instanceof Integer) {
            for (int i = 1; i <= size; i++) {
                preparedStatement.setInt(i, Integer.parseInt(parameter.toString()));
            }
            return;
        }

        if (parameter instanceof String) {
            for (int i = 1; i <= size; i++) {
                preparedStatement.setString(i, parameter.toString());
            }
            return;
        }

        // 2. 对象类型参数处理（通过反射读取 POJO 各属性值放入 fieldMap 中）
        Map<String, Object> fieldMap = new HashMap<>();
        Field[] declaredFields = parameter.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            String name = field.getName();
            field.setAccessible(true);
            Object obj = field.get(parameter);
            field.setAccessible(false);
            fieldMap.put(name, obj);
        }

        // 按 SQL 占位符序号从 fieldMap 中取出对应的值设置进 PreparedStatement
        for (int i = 1; i <= size; i++) {
            String parameterDefine = parameterMap.get(i);
            Object obj = fieldMap.get(parameterDefine);
            if (obj == null) {
                continue;
            }

            if (obj instanceof Short) {
                preparedStatement.setShort(i, Short.parseShort(obj.toString()));
                continue;
            }

            if (obj instanceof Integer) {
                preparedStatement.setInt(i, Integer.parseInt(obj.toString()));
                continue;
            }

            if (obj instanceof Long) {
                preparedStatement.setLong(i, Long.parseLong(obj.toString()));
                continue;
            }

            if (obj instanceof String) {
                preparedStatement.setString(i, obj.toString());
                continue;
            }

            if (obj instanceof Date) {
                preparedStatement.setDate(i, new java.sql.Date(((Date) obj).getTime()));
            }
        }
    }

    /**
     * 中介者 ORM 映射核心方法：
     * 将 JDBC 查询结果集 ResultSet 通过反射机制自动封装成目标 Java 对象列表
     *
     * @param resultSet 查询结果集
     * @param clazz     目标实体 Class
     * @param <T>       实体泛型
     * @return 映射后的实体对象列表
     */
    private <T> List<T> resultSet2Obj(ResultSet resultSet, Class<?> clazz) {
        List<T> list = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            // 遍历结果集中的每一行记录
            while (resultSet.next()) {
                T obj = (T) clazz.getDeclaredConstructor().newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    if (value == null) {
                        continue;
                    }
                    // 根据列名拼接 setter 方法名（如 name -> setName）
                    String columnName = metaData.getColumnName(i);
                    String setMethod = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    Method method;
                    if (value instanceof Timestamp) {
                        // 时间类型适配：兼顾 java.util.Date 与 Timestamp
                        try {
                            method = clazz.getMethod(setMethod, Date.class);
                            method.invoke(obj, new Date(((Timestamp) value).getTime()));
                        } catch (NoSuchMethodException e) {
                            method = clazz.getMethod(setMethod, Timestamp.class);
                            method.invoke(obj, value);
                        }
                    } else {
                        method = clazz.getMethod(setMethod, value.getClass());
                        method.invoke(obj, value);
                    }
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 关闭数据库物理连接
     */
    @Override
    public void close() {
        if (null == connection) return;
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}