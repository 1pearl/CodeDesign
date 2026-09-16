package com.ivanzhao.Idesign.command;

import com.ivanzhao.Idesign.command.cuisine.ICuisine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令模式下的店小二（服务员）
 * 
 * ==================== 【命令模式核心角色：调用者/请求者 (Invoker)】 ====================
 * 1. 角色职责：
 *    - 负责接收客户端发来的各种命令（ICuisine）；
 *    - 维护一个待执行的命令清单（cuisineList）；
 *    - 在合适的时机统一发出指令，批量触发所有命令的执行（placeOrder）。
 * 
 * 2. 核心架构优势与命令模式体现：
 *    - 完全与具体厨师解耦：小二只面向抽象命令 ICuisine 编程，根本不知道也不需要知道厨房里哪位厨师做哪道菜；
 *    - 具备命令的排队与批处理能力：小二可以把顾客点的多道菜先收集在 cuisineList 中，待点单完毕后统一通知后厨；
 *    - 具备极高扩展性：若新增菜系（如湘菜），小二类无需改动一行代码，完全符合开闭原则（OCP）；
 *    - 可轻松支持撤销（Undo）、重做（Redo）、命令记录日志及延时执行等高级功能。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class XiaoEr {

    private Logger logger = LoggerFactory.getLogger(XiaoEr.class);

    /**
     * 命令池/待执行命令队列：收集客户点的菜品命令对象
     */
    private List<ICuisine> cuisineList = new ArrayList<>();

    /**
     * 点单接收命令：将客户端发出的业务请求转化为 Command 对象加入队列
     * 
     * @param cuisine 抽象菜品命令实例（例如：粤菜、苏菜、鲁菜、川菜）
     */
    public void order(ICuisine cuisine) {
        cuisineList.add(cuisine);
    }

    /**
     * 正式下单并批处理执行所有排队的命令
     * 
     * 核心流转机制：
     * 1. 遍历命令队列中的每一个 ICuisine 实例；
     * 2. 调用命令的统一入口 cuisine.doCuisine()；
     * 3. 内部自动触发各具体命令所绑定的厨师（Receiver）进行烹饪；
     * 4. 执行完成后清空命令队列，重置小二状态。
     */
    public synchronized void placeOrder() {
        for (ICuisine cuisine : cuisineList) {
            // 【命令模式体现】：统一调用抽象方法，自动分发给各具体厨师执行
            cuisine.doCuisine();
        }
        // 执行完毕后清空当前菜单命令列表
        cuisineList.clear();
    }

}
