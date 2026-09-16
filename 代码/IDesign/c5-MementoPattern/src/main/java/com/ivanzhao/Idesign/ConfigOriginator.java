package com.ivanzhao.Idesign;

/**
 * 记录者/发起人类 —— 备忘录模式中的【发起人角色（Originator）】
 * <p>
 * 核心设计思想与职责：
 * 1. 业务系统核心主体，拥有需要被保存的内部状态（ConfigFile）；
 * 2. 提供创建备忘录方法 {@link #saveMemento()}：将当前内部状态打包创建为一个新的快照对象 {@link ConfigMemento}；
 * 3. 提供恢复状态方法 {@link #getMemento(ConfigMemento)}：根据传入的备忘录对象恢复先前的内部状态；
 * 4. 彻底屏蔽了外部系统直接篡改内部状态的风险，保证状态保存与恢复的自主权完全在发起人自身。
 *
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class ConfigOriginator {

    /** 当前发起人持有的配置文件状态 */
    private ConfigFile configFile;

    public ConfigOriginator() {}

    public ConfigOriginator(ConfigFile configFile) {
        this.configFile = configFile;
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public void setConfigFile(ConfigFile configFile) {
        this.configFile = configFile;
    }

    /**
     * 创建并保存当前配置状态的备忘录快照
     *
     * @return 包含当前配置文件状态的备忘录对象
     */
    public ConfigMemento saveMemento() {
        return new ConfigMemento(this.configFile);
    }

    /**
     * 从给定的备忘录快照中恢复配置状态
     *
     * @param memento 包含历史配置状态的备忘录对象
     */
    public void getMemento(ConfigMemento memento) {
        if (memento != null) {
            this.configFile = memento.getConfigFile();
        }
    }

}
