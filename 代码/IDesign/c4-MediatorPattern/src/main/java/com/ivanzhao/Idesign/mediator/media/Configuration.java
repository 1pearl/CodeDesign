package com.ivanzhao.Idesign.mediator.media;

import java.sql.Connection;
import java.util.Map;

/**
 * ORM 全局配置核心类
 * <p>
 * 保存解析自 XML 文件的所有配置元数据，包括：
 * 1. 数据库物理连接（Connection）
 * 2. 数据源连接池配置（dataSource: driver, url, username, password 等）
 * 3. 映射节点信息表（mapperElement: namespace.id -> XNode）
 * <p>
 * 作为中介者环境的“上下文配置中心”，在整个会话生命周期中为中介者提供元数据支持。
 */
public class Configuration {

    /** 数据库物理连接对象 */
    protected Connection connection;

    /** 数据源配置属性（驱动名、连接串、账号、密码等） */
    protected Map<String, String> dataSource;

    /** SQL 映射节点集合，Key 格式为 "命名空间.SQL语句ID"（如 com.ivanzhao.Idesign.mediator.dao.IUserDao.queryUserInfoById） */
    protected Map<String, XNode> mapperElement;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Map<String, String> getDataSource() {
        return dataSource;
    }

    public void setDataSource(Map<String, String> dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, XNode> getMapperElement() {
        return mapperElement;
    }

    public void setMapperElement(Map<String, XNode> mapperElement) {
        this.mapperElement = mapperElement;
    }

}