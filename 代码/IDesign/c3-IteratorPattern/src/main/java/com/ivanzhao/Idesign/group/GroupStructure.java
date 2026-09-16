package com.ivanzhao.Idesign.group;

import com.ivanzhao.Idesign.lang.Collection;
import com.ivanzhao.Idesign.lang.Iterator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 组织架构集合类（ConcreteAggregate - 具体聚合角色）
 * 
 * ==================== 【迭代器模式核心角色：具体聚合类 (ConcreteAggregate)】 ====================
 * 1. 角色定义：实现抽象聚合接口（Collection / Aggregate），负责存储和管理具体的数据元素，
 *    并提供创建对应具体迭代器对象（ConcreteIterator）的工厂方法 iterator()。
 * 
 * 2. 本类内部复杂数据结构的设计解耦：
 *    - employeeMap: 存储所有人员信息（底层散列表，Key为雇员ID，Value为雇员实体）；
 *    - linkMap: 存储组织拓扑的有向边（正向索引，Key为父部门/领导ID，Value为直属下级关联列表）；
 *    - invertedMap: 存储向上追溯的父子映射（倒排索引，Key为下属ID，Value为上级ID），用于遍历叶子回溯。
 * 
 * 3. 迭代器模式的核心价值体现：
 *    - 数据组织虽然是极度复杂的“多叉树/拓扑图”非线性网状结构；
 *    - 但是外部客户端（Client）完全不需要理解这些父子指针、倒排映射或递归回溯算法；
 *    - 通过实现 iterator() 方法，将图/树结构的深度优先搜索（DFS）状态机完全封装在 ConcreteIterator 内部，
 *      向外暴露出纯粹而简洁的线性 hasNext() / next() 访问协议。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class GroupStructure implements Collection<Employee, Link> {

    /** 根组织/顶级部门ID */
    private String groupId;

    /** 组织机构名称 */
    private String groupName;

    /**
     * 雇员实体缓存容器：存储所有节点人员数据
     * Key: 雇员ID(uId), Value: 雇员对象(Employee)
     */
    private Map<String, Employee> employeeMap = new ConcurrentHashMap<>();

    /**
     * 正向拓扑关系容器：记录父节点与其所有直属子节点的关系列表（多叉树向下分支）
     * Key: 父节点ID(fromId), Value: 直属子节点关联关系列表(List<Link>)
     */
    private Map<String, List<Link>> linkMap = new ConcurrentHashMap<>();

    /**
     * 倒排拓扑关系容器：记录子节点对应的直接父节点（多叉树向上回溯指针）
     * Key: 子节点ID(toId), Value: 父节点ID(fromId)
     */
    private Map<String, String> invertedMap = new ConcurrentHashMap<>();

    public GroupStructure(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    /**
     * 添加雇员实体到组织架构容器中
     * 
     * @param employee 待加入的雇员
     * @return 添加成功返回 true
     */
    @Override
    public boolean add(Employee employee) {
        employeeMap.put(employee.getuId(), employee);
        return true;
    }

    /**
     * 从组织架构容器中移除雇员
     * 
     * @param employee 待移除的雇员
     * @return 移除成功返回 true，若原先不存在则返回 false
     */
    @Override
    public boolean remove(Employee employee) {
        return null != employeeMap.remove(employee.getuId());
    }

    /**
     * 建立两节点之间的上下级关联链路（构建树形多叉拓扑分支）
     * 
     * @param key  父节点ID
     * @param link 父子关联边对象（fromId -> toId）
     * @return 是否成功建立关系
     */
    @Override
    public boolean addLink(String key, Link link) {
        // 1. 维护倒排索引：记录“子节点 -> 父节点”映射，方便后续在叶子节点回溯父级
        invertedMap.put(link.getToId(), link.getFromId());

        // 2. 维护正向多叉链表：如果当前父节点已存在分支，追加到列表末尾
        if (linkMap.containsKey(key)) {
            return linkMap.get(key).add(link);
        } else {
            // 首次为该父节点创建分支子列表
            LinkedList<Link> links = new LinkedList<>();
            links.add(link);
            linkMap.put(key, links);
            return true;
        }
    }

    /**
     * 解除指定父节点下的所有关系链
     * 
     * @param key 父节点ID
     * @return 解除成功返回 true
     */
    @Override
    public boolean removeLink(String key) {
        return null != linkMap.remove(key);
    }

    /**
     * ==================== 【迭代器工厂方法：创建并返回具体迭代器】 ====================
     * 
     * 此处使用匿名内部类（Anonymous Inner Class）实现具体的抽象迭代器 Iterator<Employee>。
     * 
     * 该迭代器内部维护了非递归、带游标记忆与回溯能力的状态机：
     * 1. 深度优先搜索（DFS）：优先探索当前节点向下的子分支；
     * 2. 游标状态记录（keyMap）：针对每个分支父节点，记录当前已遍历到了第几个子链路索引；
     * 3. 逆向回溯机制（invertedMap）：当一条分支走到叶子节点（或当前层子节点已遍历完）时，
     *    利用倒排索引逐层上溯回退到尚未遍历完兄弟分支的祖先节点继续扫描；
     * 4. 计数器限制（totalIdx）：精确控制总遍历步数，防止死循环越界。
     * 
     * @return com.ivanzhao.Idesign.lang.Iterator<Employee> 抽象迭代器的具体实现实例
     */
    @Override
    public Iterator<Employee> iterator() {

        return new Iterator<Employee>() {

            /**
             * 游标位置映射表：
             * Key: 某一父节点的ID, Value: 该父节点下当前已经访问过的子节点Link索引（从0开始递增）
             */
            private HashMap<String, Integer> keyMap = new HashMap<>();

            /** 已经成功遍历的雇员总数量计数器 */
            private int totalIdx = 0;

            /** 当前扫描所在的父节点ID（初始为根组织groupId） */
            private String fromId = groupId;

            /** 当前扫描到的目标节点ID（初始为根组织groupId） */
            private String toId = groupId;

            /**
             * 判断是否还有未遍历的雇员
             * 当已访问总数小于雇员散列表总大小时，说明仍有雇员尚未访问
             */
            @Override
            public boolean hasNext() {
                return totalIdx < employeeMap.size();
            }

            /**
             * 迭代核心：获取下一个雇员，并驱动树形遍历游标推进一步
             * 
             * 算法流程：
             * 1. 尝试以当前 toId 作为父节点向下探测子分支（向下深入）；
             * 2. 若当前节点为叶子节点（无子分支），则退回当前同级（以 fromId 为基准继续推进下一个兄弟节点）；
             * 3. 若同级兄弟节点全部访问完毕（cursorIdx > links.size() - 1），通过 invertedMap 逐级向上回溯（Backtracking）；
             * 4. 命中有效子链路后，更新 fromId、toId 状态，累加已访问计数 totalIdx，最终返回雇员实体。
             */
            @Override
            public Employee next() {

                // 尝试向下获取目标节点的子关联列表
                List<Link> links = linkMap.get(toId);
                int cursorIdx = getCursorIdx(toId);

                // 阶段一：叶子节点判断——若当前节点没有子分支，退回同级，从当前父节点继续获取后续兄弟节点
                if (null == links) {
                    cursorIdx = getCursorIdx(fromId);
                    links = linkMap.get(fromId);
                }

                // 阶段二：逐级向上回溯——若当前父节点下的所有子分支都已遍历完毕，沿着倒排链向上追溯
                while (cursorIdx > links.size() - 1) {
                    fromId = invertedMap.get(fromId);
                    cursorIdx = getCursorIdx(fromId);
                    links = linkMap.get(fromId);
                }

                // 阶段三：获取命中分支的关联边，更新迭代状态机指针
                Link link = links.get(cursorIdx);
                toId = link.getToId();
                fromId = link.getFromId();
                totalIdx++;

                // 阶段四：从员工哈希表中取出对应实体返回给客户端
                return employeeMap.get(link.getToId());
            }

            /**
             * 获取并递增指定节点对应的子分支遍历游标
             * 
             * @param key 节点ID
             * @return 当前可用的子分支下标索引
             */
            public int getCursorIdx(String key) {
                int idx = 0;
                if (keyMap.containsKey(key)) {
                    idx = keyMap.get(key);
                    keyMap.put(key, ++idx);
                } else {
                    keyMap.put(key, idx);
                }
                return idx;
            }

        };

    }

}