package com.ivanzhao.Idesign.command.cook.impl;

import com.ivanzhao.Idesign.command.cook.ICook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 四川厨师（川菜师傅）
 * 
 * ==================== 【命令模式角色：具体接收者 (Concrete Receiver)】 ====================
 * 职责：真正实施“烹饪川菜”具体动作的执行者。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class SiChuanCook implements ICook {

    private Logger logger = LoggerFactory.getLogger(SiChuanCook.class);

    /**
     * 实施具体的川菜烹饪工作
     */
    @Override
    public void doCooking() {
        logger.info("四川厨师，烹饪川菜，中国最有特色的菜系，也是民间最大菜系。");
    }
}
