package com.ivanzhao.Idesign.mediator.media;

import java.util.Map;

/**
 * SQL 映射节点元数据类（MappedStatement / XNode）
 * <p>
 * 对应 Mapper XML 中定义的一条完整 SQL 节点信息（如 &lt;select&gt; 标签）。
 * 封装了：
 * 1. 接口命名空间（namespace，通常是 DAO 接口全类名）
 * 2. 方法唯一标识（id，对应 DAO 接口方法名）
 * 3. 入参类型（parameterType）
 * 4. 返回值结果类型（resultType）
 * 5. 替换 #{...} 为 '?' 后的原生预编译 SQL
 * 6. SQL 中占位符位置与原入参属性名的映射字典（如 1 -> "id"）
 */
public class XNode {

    /** 映射文件中的命名空间（如 "com.ivanzhao.Idesign.mediator.dao.IUserDao"） */
    private String namespace;

    /** SQL 节点 ID（如 "queryUserInfoById"） */
    private String id;

    /** 入参全类名（如 "java.lang.Long" 或 "com.ivanzhao.Idesign.mediator.po.User"） */
    private String parameterType;

    /** 结果集映射的目标实体类全类名（如 "com.ivanzhao.Idesign.mediator.po.User"） */
    private String resultType;

    /** 预编译 SQL 语句（已将 #{...} 转换成 JDBC 的 '?'） */
    private String sql;

    /** 参数占位符位置索引与参数字段名的映射表（序号从 1 开始 -> 属性名） */
    private Map<Integer, String> parameter;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Map<Integer, String> getParameter() {
        return parameter;
    }

    public void setParameter(Map<Integer, String> parameter) {
        this.parameter = parameter;
    }
}