package com.ivanzhao.Idesign.lang;

/**
 * 抽象聚合集合接口（Aggregate）
 * 
 * ==================== 【迭代器模式核心角色：抽象聚合角色 (Aggregate)】 ====================
 * 1. 角色定义：定义存储、添加、删除元素以及创建相应迭代器对象的接口。
 * 2. 核心架构关系：
 *    - 继承自 Iterable<E>，拥有 iterator() 工厂方法；
 *    - 既封装了基础的元素增删行为（add/remove），又支持关系连接（addLink/removeLink）；
 *    - 使得具体聚合容器（如组织架构 GroupStructure）能够将复杂的元素管理与纯粹的迭代遍历行为彻底分离。
 * 
 * @param <E> 集合实体类型（如雇员 Employee）
 * @param <T> 实体拓扑关系类型（如节点连线 Link）
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public interface Collection<E, T> extends Iterable<E> {

    /**
     * 向集合中添加一个实体元素
     * @param e 实体对象
     * @return 是否添加成功
     */
    boolean add(E e);

    /**
     * 从集合中移除一个实体元素
     * @param e 待移除对象
     * @return 是否移除成功
     */
    boolean remove(E e);

    /**
     * 建立实体之间的层级关联关系（构建树形或拓扑网络结构）
     * @param key 关系标识（通常为父节点ID）
     * @param t   关系对象（如 Link）
     * @return 是否建立成功
     */
    boolean addLink(String key, T t);

    /**
     * 解除指定节点的层级关系
     * @param key 关系标识
     * @return 是否解除成功
     */
    boolean removeLink(String key);

    /**
     * 创建并返回用于遍历本聚合容器的迭代器实例
     * @return 自定义迭代器 Iterator<E>
     */
    @Override
    Iterator<E> iterator();

}