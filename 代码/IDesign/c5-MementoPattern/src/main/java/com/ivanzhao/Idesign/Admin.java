package com.ivanzhao.Idesign;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理员/负责人角色 —— 备忘录模式中的【管理者角色（Caretaker）】
 * <p>
 * 核心设计思想与职责：
 * 1. 负责保存所有的备忘录对象（ConfigMemento），维护历史快照的时间线；
 * 2. 提供撤销（Undo / 回滚）与重做（Redo / 前进）的操作游标指针（cursorId）；
 * 3. 提供按指定版本号（versionNo）的快速检索定位能力；
 * 4. 严守黑盒原则：管理者只能存储和传递备忘录，不能也不应该检查或修改备忘录内部存储的数据内容。
 *
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class Admin {

    /** 回滚/前进历史记录的操作游标地址 */
    private int cursorId;

    /** 按时间先后顺序线性存储的备忘录快照列表，用于 undo/redo 游标回退与前进 */
    private List<ConfigMemento> mementoList = new ArrayList<>();

    /** 支持多线程并发按版本号快速定位备忘录的哈希映射表（版本号 -> 备忘录快照） */
    private Map<String, ConfigMemento> mementoMap = new ConcurrentHashMap<>();

    /**
     * 追加保存一份新的备忘录快照
     *
     * @param memento 待归档的备忘录快照
     */
    public void append(ConfigMemento memento) {
        if (memento == null || memento.getConfigFile() == null) {
            return;
        }
        mementoList.add(memento);
        mementoMap.put(memento.getConfigFile().getVersionNo(), memento);
        cursorId++;
    }

    /**
     * 回滚/撤销到上一个历史备忘录状态（Undo）
     *
     * @return 回滚后的备忘录快照；若列表为空则返回 null，游标到达最左端时锁定在首个历史版本
     */
    public ConfigMemento undo() {
        if (mementoList.isEmpty()) {
            return null;
        }
        // 游标向左回退，若到达或越过下边界，重置为 0 并返回第 0 个版本
        if (--cursorId <= 0) {
            cursorId = 0;
            return mementoList.get(0);
        }
        return mementoList.get(cursorId);
    }

    /**
     * 前进/重做到下一个历史备忘录状态（Redo）
     *
     * @return 前进后的备忘录快照；若列表为空则返回 null，游标到达最右端时锁定在最新版本
     */
    public ConfigMemento redo() {
        if (mementoList.isEmpty()) {
            return null;
        }
        // 游标向右前进，若到达或越过上边界，重置为最后一条索引并返回最新版本
        if (++cursorId >= mementoList.size()) {
            cursorId = mementoList.size() - 1;
            return mementoList.get(cursorId);
        }
        return mementoList.get(cursorId);
    }

    /**
     * 根据版本号精确获取指定的历史备忘录配置快照
     *
     * @param versionNo 版本号
     * @return 对应的备忘录快照，未查到返回 null
     */
    public ConfigMemento get(String versionNo) {
        if (versionNo == null) {
            return null;
        }
        return mementoMap.get(versionNo);
    }

}
