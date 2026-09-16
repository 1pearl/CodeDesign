package com.ivanzhao.Idesign.group;

/**
 * 拓扑链路/层级关联类（Link / Edge）
 * 
 * ==================== 【迭代器模式角色：聚合容器内部拓扑关系 (Relation / Edge)】 ====================
 * 1. 角色定义：用于构建非线性数据结构（树/多叉树/拓扑图）中节点之间父子上下级关系的边。
 * 2. 模式中的作用：
 *    - 在很多经典迭代器案例中，元素仅以线性数组或链表存放；
 *    - 本案例中，业务组织架构是一个典型的“多叉树”或“有向图”；
 *    - Link 定义了由上级（fromId）指向下级（toId）的父子级联指针，帮助聚合对象构建非线性存储拓扑；
 *    - 迭代器在遍历时借助 Link 的指向关系与逆向查找（invertedMap）进行树形上下级回溯与横向扫描，
 *      进而将复杂的多叉树拓扑结构对外部调用者“压平”为线性的 Iterator 遍历。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class Link {

    /** 源节点ID（父级/领导节点） */
    private String fromId;

    /** 目标节点ID（子级/下属节点） */
    private String toId;

    public Link(String fromId, String toId) {
        this.fromId = fromId;
        this.toId = toId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

}