package com.ivanzhao.Idesign.command.cuisine.impl;

import com.ivanzhao.Idesign.command.cook.ICook;
import com.ivanzhao.Idesign.command.cuisine.ICuisine;

/**
 * 川菜菜品命令（四川菜）
 * 
 * ==================== 【命令模式角色：具体命令 (ConcreteCommand)】 ====================
 * 1. 角色职责：
 *    - 封装点川菜的具体请求；
 *    - 持有做川菜的厨师（接收者）；
 * 2. 命令模式精髓体现：
 *    - 在 doCuisine() 中调用 cook.doCooking()。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class SiChuanCuisine implements ICuisine {

    /**
     * 持有接收者（Receiver）引用：负责烹饪川菜的厨师
     */
    private ICook cook;

    /**
     * 构造函数：强制依赖注入具体的做菜厨师（接收者）
     * 
     * @param cook 负责做川菜的厨师
     */
    public SiChuanCuisine(ICook cook) {
        this.cook = cook;
    }

    /**
     * 触发命令执行：委托给厨师进行烹饪
     */
    @Override
    public void doCuisine() {
        if (cook != null) {
            cook.doCooking();
        }
    }
}