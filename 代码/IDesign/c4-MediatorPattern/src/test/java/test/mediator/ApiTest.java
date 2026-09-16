package test.mediator;

import com.alibaba.fastjson.JSON;
import com.ivanzhao.Idesign.mediator.media.Resources;
import com.ivanzhao.Idesign.mediator.media.SqlSession;
import com.ivanzhao.Idesign.mediator.media.SqlSessionFactory;
import com.ivanzhao.Idesign.mediator.media.SqlSessionFactoryBuilder;
import com.ivanzhao.Idesign.mediator.po.School;
import com.ivanzhao.Idesign.mediator.po.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.util.List;

/**
 * 客户端测试类（Client）
 * <p>
 * ==================== 【中介者模式在 ORM 框架中的角色实践】 ====================
 * 1. 抽象中介者 (Mediator): {@link SqlSession}
 *    定义了与数据操作相关的核心协议接口（selectOne, selectList, close）。
 * <p>
 * 2. 具体中介者 (Concrete Mediator): {@link com.ivanzhao.Idesign.mediator.media.DefaultSqlSession}
 *    协调并封装 JDBC Connection、PreparedStatement、ResultSet 解析映射等底层组件的交互。
 * <p>
 * 3. 同事类角色 (Colleague):
 *    - DAO 接口：{@link com.ivanzhao.Idesign.mediator.dao.IUserDao}、{@link com.ivanzhao.Idesign.mediator.dao.ISchoolDao}
 *    - 数据传输实体：{@link User}、{@link School}
 * <p>
 * 4. 中介者模式优势：
 *    - 业务调用方（客户端/DAO）与底层 JDBC 实现彻底解耦；
 *    - 调用方不再关心数据库驱动加载、连接创建、SQL 参数占位符填充、类型反射映射等繁琐过程；
 *    - 只需要通过中介者接口传入 statement 标识和参数，即可由中介者协调各方并返回 POJO 对象。
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    /**
     * 测试通过中介者 SqlSession 根据主键 ID 查询单个用户信息
     */
    @Test
    public void test_queryUserInfoById() {
        String resource = "mybatis-config-datasource.xml";
        Reader reader;
        try {
            // 1. 加载核心配置文件
            reader = Resources.getResourceAsReader(resource);
            // 2. 通过构建器解析 XML 并创建会话工厂
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

            // 3. 从工厂中开启一个中介者会话 SqlSession
            SqlSession session = sqlMapper.openSession();
            try {
                // 4. 由中介者根据 statement 全限定名路由并执行查询、自动反射转换为 User 对象
                User user = session.selectOne("com.ivanzhao.Idesign.mediator.dao.IUserDao.queryUserInfoById", 1L);
                logger.info("测试结果 用户详情：{}", JSON.toJSONString(user));
            } finally {
                // 5. 由中介者统一释放底层连接资源
                session.close();
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试通过中介者 SqlSession 根据入参对象查询用户列表
     */
    @Test
    public void test_queryUserList() {
        String resource = "mybatis-config-datasource.xml";
        Reader reader;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

            SqlSession session = sqlMapper.openSession();
            try {
                User req = new User();
                req.setAge(18);
                List<User> userList = session.selectList("com.ivanzhao.Idesign.mediator.dao.IUserDao.queryUserList", req);
                logger.info("测试结果 用户列表：{}", JSON.toJSONString(userList));
            } finally {
                session.close();
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试通过中介者 SqlSession 查询学校详情
     */
    @Test
    public void test_querySchoolInfoById() {
        String resource = "mybatis-config-datasource.xml";
        Reader reader;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

            SqlSession session = sqlMapper.openSession();
            try {
                School school = session.selectOne("com.ivanzhao.Idesign.mediator.dao.ISchoolDao.querySchoolInfoById", 1L);
                logger.info("测试结果 学校详情：{}", JSON.toJSONString(school));
            } finally {
                session.close();
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
