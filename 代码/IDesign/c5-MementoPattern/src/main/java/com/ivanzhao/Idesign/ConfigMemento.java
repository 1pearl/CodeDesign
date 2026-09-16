package com.ivanzhao.Idesign;

/**
 * 备忘录类 —— 备忘录模式中的【备忘录角色（Memento）】
 * <p>
 * 核心设计思想与职责：
 * 1. 负责存储发起人（ConfigOriginator）在某一时刻的内部状态快照（ConfigFile）；
 * 2. 保护状态封装性：备忘录对象由发起人创建，并交由负责人（Admin / Caretaker）保管；
 * 3. 负责人（Admin）只能存储和传递备忘录，不能也不应该修改备忘录内部的状态数据，从而保证历史快照的安全与只读性。
 *
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class ConfigMemento {

    /** 存储在备忘录中的历史配置状态快照 */
    private ConfigFile configFile;

    /**
     * 构造备忘录快照
     *
     * @param configFile 需要保存的配置文件状态快照
     */
    public ConfigMemento(ConfigFile configFile) {
        this.configFile = configFile;
    }

    /**
     * 获取备忘录保存的配置文件状态
     *
     * @return 历史配置文件对象
     */
    public ConfigFile getConfigFile() {
        return configFile;
    }

    /**
     * 设置/更新备忘录中的配置文件状态
     *
     * @param configFile 配置文件对象
     */
    public void setConfigFile(ConfigFile configFile) {
        this.configFile = configFile;
    }

}
