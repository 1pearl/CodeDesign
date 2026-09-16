package com.ivanzhao.Idesign.command.cuisine;

/**
 * 菜品抽象命令接口（Command）
 * 
 * ==================== 【命令模式核心角色：抽象命令接口 (Command)】 ====================
 * 1. 角色定义：声明执行命令的统一抽象接口或方法。
 * 2. 核心精髓：
 *    - 将一个“做某道菜”的业务请求抽象为一个对象（菜品实例）；
 *    - 使得调用者（店小二 XiaoEr）无需知道具体是哪位厨师做菜，只需面向 ICuisine 接口统一触发 doCuisine()；
 *    - 将传统的“小二直接呼叫厨师”的耦合关系，转变为“小二收集菜品命令列表，统一通知执行”，实现了请求者与执行者的解耦。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public interface ICuisine {

    /**
     * 执行菜品烹饪命令（Command 模式核心执行方法）
     */
    void doCuisine();

}
