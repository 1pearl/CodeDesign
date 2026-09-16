package com.ivanzhao.Idesign.lang;

/**
 * 抽象迭代器接口（Iterator）
 * 
 * ==================== 【迭代器模式核心角色：抽象迭代器 (Iterator)】 ====================
 * 1. 角色定义：定义访问和遍历集合元素的统一契约接口。
 * 2. 核心价值：
 *    - 抹平底层物理数据结构的差异（无论是数组、链表、树、图还是哈希表）；
 *    - 对外暴露极其简单、一致的顺序遍历方式（hasNext 和 next）；
 *    - 使得调用方无需了解底层复杂的数据存储和指针连接细节，即可安全遍历整个数据集合。
 * 
 * @param <E> 集合中元素的泛型类型
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public interface Iterator<E> {

    /**
     * 判断集合中是否还有下一个可以访问的元素
     * 
     * @return true 表示还有元素未遍历，false 表示已到达集合末尾
     */
    boolean hasNext();

    /**
     * 获取集合中的下一个元素，并将遍历游标后移一位
     * 
     * @return 下一个元素对象
     */
    E next();

}