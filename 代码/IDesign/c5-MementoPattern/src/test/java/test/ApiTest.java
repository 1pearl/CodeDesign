package test;

import com.alibaba.fastjson.JSON;
import com.ivanzhao.Idesign.Admin;
import com.ivanzhao.Idesign.ConfigFile;
import com.ivanzhao.Idesign.ConfigOriginator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 客户端测试类（Client）
 * <p>
 * ==================== 【备忘录模式（Memento Pattern）业务实战验证】 ====================
 * 1. 模式意图：
 *    在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，
 *    以便以后当需要时能将该对象恢复到原先保存的状态。
 * <p>
 * 2. 核心角色职责划分：
 *    - 【发起人 (Originator)】: {@link ConfigOriginator}，业务核心主体，负责创建快照并使用快照恢复状态；
 *    - 【备忘录 (Memento)】: {@link com.ivanzhao.Idesign.ConfigMemento}，存储发起人内部状态的快照包装器；
 *    - 【负责人/管理者 (Caretaker)】: {@link Admin}，负责管理备忘录历史列表、游标推进（Undo/Redo）及根据版本号检索；
 *    - 【状态对象 (State)】: {@link ConfigFile}，具体的配置状态数据对象。
 * <p>
 * 3. 业务场景模拟：
 *    互联网配置中心（如 Nacos / Apollo）在多版本发布、线上故障回滚（Undo）、二次发布（Redo）
 *    以及任意历史版本快照恢复（Get by versionNo）等核心功能。
 *
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test() {
        // 1. 初始化负责人（Caretaker）与发起人（Originator）
        Admin admin = new Admin();
        ConfigOriginator configOriginator = new ConfigOriginator();

        // 2. 模拟配置发布流转：依次发布 4 个版本配置，每次发布后通过备忘录归档到 Admin
        // 版本 1：1000001
        configOriginator.setConfigFile(new ConfigFile("1000001", "配置内容A=哈哈", new Date(), "小傅哥"));
        admin.append(configOriginator.saveMemento()); // 保存配置快照到负责人中

        // 版本 2：1000002
        configOriginator.setConfigFile(new ConfigFile("1000002", "配置内容A=嘻嘻", new Date(), "小傅哥"));
        admin.append(configOriginator.saveMemento()); // 保存配置快照

        // 版本 3：1000003
        configOriginator.setConfigFile(new ConfigFile("1000003", "配置内容A=么么", new Date(), "小傅哥"));
        admin.append(configOriginator.saveMemento()); // 保存配置快照

        // 版本 4：1000004
        configOriginator.setConfigFile(new ConfigFile("1000004", "配置内容A=嘿嘿", new Date(), "小傅哥"));
        admin.append(configOriginator.saveMemento()); // 保存配置快照

        // 3. 【历史配置回滚验证 1 (Undo)】：游标左移，回滚至上一版本
        configOriginator.getMemento(admin.undo());
        logger.info("历史配置(回滚)undo：{}", JSON.toJSONString(configOriginator.getConfigFile()));

        // 4. 【历史配置回滚验证 2 (Undo)】：游标再次左移，回滚至上上版本
        configOriginator.getMemento(admin.undo());
        logger.info("历史配置(回滚)undo：{}", JSON.toJSONString(configOriginator.getConfigFile()));

        // 5. 【历史配置前进验证 (Redo)】：游标右移，前进至较新的下一个版本
        configOriginator.getMemento(admin.redo());
        logger.info("历史配置(前进)redo：{}", JSON.toJSONString(configOriginator.getConfigFile()));

        // 6. 【精确版本获取验证 (Get)】：通过版本号从哈希索引中直接提取特定历史配置快照并恢复
        configOriginator.getMemento(admin.get("1000002"));
        logger.info("历史配置(获取)get：{}", JSON.toJSONString(configOriginator.getConfigFile()));
    }

}
