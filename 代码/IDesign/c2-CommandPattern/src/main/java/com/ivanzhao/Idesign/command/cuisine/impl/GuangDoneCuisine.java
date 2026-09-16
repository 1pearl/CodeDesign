package com.ivanzhao.Idesign.command.cuisine.impl;

import com.ivanzhao.Idesign.command.cook.ICook;
import com.ivanzhao.Idesign.command.cuisine.ICuisine;

/**
 * 粤菜菜品命令（广东菜）
 * 
 * ==================== 【命令模式角色：具体命令 (ConcreteCommand)】 ====================
 * 1. 角色职责：
 *    - 对应具体的业务请求（点了一份粤菜）；
 *    - 内部持有真正的业务接收者（cook）；
 * 2. 命令模式精髓体现：
 *    - 在 doCuisine() 实现方法中，将请求委托给所持有的接收者 cook.doCooking() 执行；
 *    - 封装了调用接收者所需的所有上下文信息。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class GuangDoneCuisine implements ICuisine {

    /**
     * 持有接收者（Receiver）引用：负责烹饪粤菜的厨师
     */
    private ICook cook;

    public GuangDoneCuisine() {}

    /**
     * 构造函数：依赖注入具体的做菜厨师（接收者）
     * 
     * @param cook 负责做粤菜的厨师
     */
    public GuangDoneCuisine(ICook cook) {
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
