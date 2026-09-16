package com.ivanzhao.IDesign.observer.evnet.listener;

import com.ivanzhao.IDesign.common.LotteryResult;

/**
 * 事件监听器接口 —— 观察者模式中的【抽象观察者角色（Observer）】
 * <p>
 * 统一定义所有观察者接收到被观察者（Subject）广播事件时的回调契约。
 * 不同的下游辅助业务（如发短信、发MQ、发邮件、记审计日志等）只需实现该接口并覆写 {@link #doEvent(LotteryResult)} 方法，
 * 即可无侵入地接入到主干业务流转中，完美符合面向接口编程与开闭原则（OCP）。
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public interface EventListener {

    /**
     * 事件触发时的响应回调动作
     * @param result 摇号结果上下文数据对象
     */
    void doEvent(LotteryResult result);

}
