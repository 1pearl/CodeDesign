package com.ivanzhao.Idesign.command.cook.impl;

import com.ivanzhao.Idesign.command.cook.ICook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 广东厨师（粤菜师傅）
 * 
 * ==================== 【命令模式角色：具体接收者 (Concrete Receiver)】 ====================
 * 职责：真正实施“烹饪粤菜”具体动作的执行者。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class GuangDongCook implements ICook {

    private Logger logger = LoggerFactory.getLogger(GuangDongCook.class);

    /**
     * 实施具体的粤菜烹饪工作
     */
    @Override
    public void doCooking() {
        // 【修正】：文案由原先误写的“鲁菜”修正为“粤菜”
        logger.info("广东厨师，烹饪粤菜，中国八大菜系之一，以选料广博、文火炖煨、清鲜嫩滑为特色。");
    }
}
