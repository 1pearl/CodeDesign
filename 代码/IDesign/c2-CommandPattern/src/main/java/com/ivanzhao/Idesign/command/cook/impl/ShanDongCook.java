package com.ivanzhao.Idesign.command.cook.impl;

import com.ivanzhao.Idesign.command.cook.ICook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 山东厨师（鲁菜师傅）
 * 
 * ==================== 【命令模式角色：具体接收者 (Concrete Receiver)】 ====================
 * 职责：真正实施“烹饪鲁菜”具体动作的执行者。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class ShanDongCook implements ICook {

    private Logger logger = LoggerFactory.getLogger(ShanDongCook.class);

    /**
     * 实施具体的鲁菜烹饪工作
     */
    @Override
    public void doCooking() {
        logger.info("山东厨师，烹饪鲁菜，宫廷最大菜系，以孔府风味为龙头。");
    }
}
