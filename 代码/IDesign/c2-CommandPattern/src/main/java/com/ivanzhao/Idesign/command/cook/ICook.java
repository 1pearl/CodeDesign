package com.ivanzhao.Idesign.command.cook;

/**
 * 厨师统一接口
 * 
 * ==================== 【命令模式角色：接收者接口 (Receiver)】 ====================
 * 1. 角色定义：接收者（Receiver）是真正知晓如何实施与执行一个请求所对应具体操作的对象。
 * 2. 核心特点：
 *    - 无论川鲁粤苏哪个菜系的厨师，都具备烹饪技能（doCooking）；
 *    - 接收者不直接与调用者（小二 XiaoEr）打交道，而是被具体命令对象（ICuisine）所持有；
 *    - 实现了真正执行业务逻辑的实体与发出请求的调用者之间的彻底解耦。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public interface ICook {

    /**
     * 烹饪做菜动作（真正执行业务请求的方法）
     */
    void doCooking();
}
