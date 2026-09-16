package test;

import com.ivanzhao.Idesign.group.Employee;
import com.ivanzhao.Idesign.group.GroupStructure;
import com.ivanzhao.Idesign.group.Link;
import com.ivanzhao.Idesign.lang.Iterator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端测试类（Client）
 * 
 * ==================== 【迭代器模式角色：外部客户端 (Client)】 ====================
 * 1. 角色职责：
 *    - 构建聚合容器（GroupStructure），向其中装配业务实体（Employee）与拓扑层级（Link）；
 *    - 通过面向接口编程（调用 iterator() 方法获取抽象迭代器 Iterator<Employee>）；
 *    - 仅依赖标准的 hasNext() 与 next() 协议遍历整个多叉树组织架构。
 * 
 * 2. 迭代器模式解耦优势验证：
 *    - 客户端完全不需要感知组织架构内部是用的多重 Map、倒排索引，还是多叉树结构；
 *    - 客户端也不需要编写深度递归函数或管理回溯堆栈；
 *    - 即使底层存储从树形拓扑换成图、链表或网络拉取，外部客户端遍历代码完全无需任何变更！
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_iterator() {
        // 1. 初始化具体聚合容器（设置根节点ID为 "1"，组织名为 "小傅哥"）
        GroupStructure groupStructure = new GroupStructure("1", "小傅哥");

        // 2. 填充元素实体（雇员数据）
        groupStructure.add(new Employee("2", "花花", "二级部门"));
        groupStructure.add(new Employee("3", "豆包", "二级部门"));
        groupStructure.add(new Employee("4", "蹦蹦", "三级部门"));
        groupStructure.add(new Employee("5", "大烧", "三级部门"));
        groupStructure.add(new Employee("6", "虎哥", "四级部门"));
        groupStructure.add(new Employee("7", "玲姐", "四级部门"));
        groupStructure.add(new Employee("8", "秋雅", "四级部门"));

        // 3. 构建多叉树层级拓扑关系（Link：fromId -> toId）
        // 根节点 "1" -> 二级部门 "2"(花花), "3"(豆包)
        groupStructure.addLink("1", new Link("1", "2"));
        groupStructure.addLink("1", new Link("1", "3"));

        // 二级节点 "2" -> 三级部门 "4"(蹦蹦), "5"(大烧)
        groupStructure.addLink("2", new Link("2", "4"));
        groupStructure.addLink("2", new Link("2", "5"));

        // 三级节点 "5" -> 四级部门 "6"(虎哥), "7"(玲姐), "8"(秋雅)
        groupStructure.addLink("5", new Link("5", "6"));
        groupStructure.addLink("5", new Link("5", "7"));
        groupStructure.addLink("5", new Link("5", "8"));

        // 4. 【核心体现】：通过聚合对象获取抽象迭代器实例
        Iterator<Employee> iterator = groupStructure.iterator();

        // 5. 【核心体现】：统一且优雅的线性遍历，完全屏蔽底层树形/回溯细节
        logger.info("==================== 开始遍历多叉树组织架构 ====================");
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            logger.info("{} 雇员 ID：{} Name：{}", employee.getDesc(), employee.getuId(), employee.getName());
        }
        logger.info("==================== 组织架构遍历结束 ====================");

    }

}