package com.ivanzhao.Idesign.mediator.media;

import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL 会话工厂构建器 —— 中介者环境构建中心
 * <p>
 * 职责：
 * 1. 使用 Dom4j 解析 mybatis-config-datasource.xml 全局配置文件；
 * 2. 提取数据源配置（driver, url, username, password）并建立数据库物理连接 Connection；
 * 3. 提取并逐个解析 &lt;mapper&gt; 映射文件；
 * 4. 正则解析 SQL 中的占位符 #{field}，将其转换为 JDBC 预编译占位符 '?'，并记录序号与字段名称的映射关系；
 * 5. 将所有元数据组装为 Configuration 对象，最终构建并返回包含完整配置的 DefaultSqlSessionFactory。
 */
public class SqlSessionFactoryBuilder {

    /**
     * 根据 XML 字符流构建 SqlSessionFactory
     *
     * @param reader 配置文件输入流
     * @return 构建成功的 DefaultSqlSessionFactory 工厂实例
     */
    public DefaultSqlSessionFactory build(Reader reader) {
        SAXReader saxReader = new SAXReader();
        try {
            // 使用 MyBatis 的 XMLMapperEntityResolver 防止解析 DTD 约束时发生网络连接挂起或超时
            saxReader.setEntityResolver(new XMLMapperEntityResolver());
            Document document = saxReader.read(new InputSource(reader));
            Configuration configuration = parseConfiguration(document.getRootElement());
            return new DefaultSqlSessionFactory(configuration);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析根标签 &lt;configuration&gt;，组装全局配置类 Configuration
     *
     * @param root 根节点
     * @return 组装好的 Configuration
     */
    private Configuration parseConfiguration(Element root) {
        Configuration configuration = new Configuration();
        // 1. 解析数据源配置
        configuration.setDataSource(dataSource(root.selectNodes("//dataSource")));
        // 2. 根据数据源创建数据库物理连接
        configuration.setConnection(connection(configuration.dataSource));
        // 3. 解析 mappers 标签下的所有 mapper 映射文件
        configuration.setMapperElement(mapperElement(root.selectNodes("mappers")));
        return configuration;
    }

    /**
     * 解析数据源连接池配置信息（driver, url, username, password）
     *
     * @param list 匹配到 &lt;dataSource&gt; 的节点列表
     * @return 键值对形式的数据源属性字典
     */
    private Map<String, String> dataSource(List<Element> list) {
        Map<String, String> dataSource = new HashMap<>(4);
        if (list == null || list.isEmpty()) {
            return dataSource;
        }
        Element element = list.get(0);
        // 使用 elements() 过滤文本换行节点，仅获取 Element 子节点
        List<Element> content = element.elements();
        for (Element e : content) {
            String name = e.attributeValue("name");
            String value = e.attributeValue("value");
            dataSource.put(name, value);
        }
        return dataSource;
    }

    /**
     * 根据数据源配置加载 JDBC 驱动并获取数据库物理连接
     *
     * @param dataSource 数据源配置字典
     * @return Connection 数据库连接
     */
    private Connection connection(Map<String, String> dataSource) {
        try {
            Class.forName(dataSource.get("driver"));
            return DriverManager.getConnection(dataSource.get("url"), dataSource.get("username"), dataSource.get("password"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析所有 Mapper XML 映射文件，将每个 &lt;select&gt; 节点封装为 XNode 对象
     *
     * @param list 匹配到 &lt;mappers&gt; 的节点列表
     * @return Map&lt;String, XNode&gt;，Key 为 "命名空间.语句ID"（如 com.ivanzhao.Idesign.mediator.dao.IUserDao.queryUserInfoById）
     */
    private Map<String, XNode> mapperElement(List<Element> list) {
        Map<String, XNode> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }

        Element element = list.get(0);
        List<Element> content = element.elements();
        for (Element e : content) {
            String resource = e.attributeValue("resource");

            try {
                Reader reader = Resources.getResourceAsReader(resource);
                SAXReader saxReader = new SAXReader();
                saxReader.setEntityResolver(new XMLMapperEntityResolver());
                Document document = saxReader.read(new InputSource(reader));
                Element root = document.getRootElement();
                // 获取命名空间（对应 DAO 接口全类名）
                String namespace = root.attributeValue("namespace");

                // 解析所有 SELECT 查询标签
                List<Element> selectNodes = root.selectNodes("select");
                for (Element node : selectNodes) {
                    String id = node.attributeValue("id");
                    String parameterType = node.attributeValue("parameterType");
                    String resultType = node.attributeValue("resultType");
                    String sql = node.getText().trim();

                    // 使用正则提取形如 #{id} 的占位符，转换为 JDBC 预编译占位符 '?'
                    // 同时按序号记录占位符对应的属性名（如 1 -> "id"）
                    Map<Integer, String> parameter = new HashMap<>();
                    Pattern pattern = Pattern.compile("(#\\{(.*?)})");
                    Matcher matcher = pattern.matcher(sql);
                    for (int i = 1; matcher.find(); i++) {
                        String g1 = matcher.group(1); // 如 #{id}
                        String g2 = matcher.group(2); // 如 id
                        parameter.put(i, g2);
                        sql = sql.replace(g1, "?");
                    }

                    // 封装映射节点元数据
                    XNode xNode = new XNode();
                    xNode.setNamespace(namespace);
                    xNode.setId(id);
                    xNode.setParameterType(parameterType);
                    xNode.setResultType(resultType);
                    xNode.setSql(sql);
                    xNode.setParameter(parameter);

                    // 注册到全局映射表，唯一标识为 "namespace.id"
                    map.put(namespace + "." + id, xNode);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }

}