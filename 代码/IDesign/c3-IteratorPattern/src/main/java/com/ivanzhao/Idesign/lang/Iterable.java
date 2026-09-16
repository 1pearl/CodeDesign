package com.ivanzhao.Idesign.lang;

/**
 * 可迭代契约接口（Iterable）
 * 
 * ==================== 【迭代器模式角色：支持迭代的顶层契约】 ====================
 * 1. 角色作用：表明实现该接口的集合/聚合对象（Aggregate）具备被外部迭代器遍历的能力。
 * 2. 核心方法：提供获取专属迭代器实例的工厂方法 iterator()。
 * 
 * @param <E> 集合元素的类型
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public interface Iterable<E> {

    /**
     * 获取访问当前集合的专用迭代器
     * 
     * @return 自定义的抽象迭代器 Iterator<E>
     */
    Iterator<E> iterator();

}