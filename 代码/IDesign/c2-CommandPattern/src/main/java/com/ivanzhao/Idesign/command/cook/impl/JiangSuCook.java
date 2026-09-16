package com.ivanzhao.Idesign.command.cook.impl;

import com.ivanzhao.Idesign.command.cook.ICook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 江苏厨师（苏菜师傅）
 * 
 * ==================== 【命令模式角色：具体接收者 (Concrete Receiver)】 ====================
 * 职责：真正实施“烹饪苏菜”具体动作的执行者。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class JiangSuCook implements ICook {

    private Logger logger = LoggerFactory.getLogger(JiangSuCook.class);

    /**
     * 实施具体的苏菜烹饪工作
     */
    @Override
    public void doCooking() {
        logger.info("江苏厨师，烹饪苏菜，宫廷第二大菜系，古今国宴上最受人欢迎的菜系。");
    }
}
