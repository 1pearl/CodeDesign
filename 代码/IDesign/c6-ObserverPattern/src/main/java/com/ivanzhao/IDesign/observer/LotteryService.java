package com.ivanzhao.IDesign.observer;

import com.ivanzhao.IDesign.common.LotteryResult;
import com.ivanzhao.IDesign.observer.evnet.EventManager;
import com.ivanzhao.IDesign.observer.evnet.listener.MQEventListener;
import com.ivanzhao.IDesign.observer.evnet.listener.MessageEventListener;

/**
 * 抽奖/摇号抽象服务基类 —— 观察者模式中的【抽象被观察者角色（Subject / Observable）】
 * <p>
 * 核心架构思想（结合模板方法模式）：
 * 1. 持有事件管理器 {@link EventManager}，作为事件发布调度枢纽；
 * 2. 在模板方法 {@link #draw(String)} 中固化业务执行骨架：
 *    a. 执行核心摇号逻辑 {@link #doDraw(String)}（延迟到具体子类实现）；
 *    b. 触发广播通知（MQ 消息、短信通知等），由绑定的观察者异步/解耦执行；
 * 3. 实现了核心业务逻辑与周边通知逻辑的彻底解耦，无论增加多少种通知方式，核心摇号流程均无需修改。
 *
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public abstract class LotteryService {
    /** 事件发布与订阅管理器 */
    public EventManager eventManager;

    /**
     * 默认构造函数：自动初始化事件管理器并装配默认观察者（MQ 与短信监听器）
     */
    public LotteryService() {
        this.eventManager = new EventManager(EventManager.EventType.MQ, EventManager.EventType.Message);
        this.eventManager.subscribe(EventManager.EventType.MQ, new MQEventListener());
        this.eventManager.subscribe(EventManager.EventType.Message, new MessageEventListener());
    }

    /**
     * 自定义构造函数：允许外部传入定制化的事件管理器实例
     * @param eventManager 定制化的事件管理器
     */
    public LotteryService(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * 模板方法：执行摇号并自动触发事件广播
     * @param uid 用户ID
     * @return 摇号结果
     */
    public LotteryResult draw(String uid) {
        // 1. 调用抽象方法执行核心摇号业务（由具体子类 LotteryServiceImpl 实现）
        LotteryResult lotteryResult = doDraw(uid);

        // 2. 通过事件管理器向已订阅的观察者发送 MQ 广播通知
        eventManager.notify(EventManager.EventType.MQ, lotteryResult);

        // 3. 通过事件管理器向已订阅的观察者发送短信广播通知
        eventManager.notify(EventManager.EventType.Message, lotteryResult);

        // 4. 返回最终结果
        return lotteryResult;
    }

    /**
     * 核心业务抽象方法：执行具体摇号（由具体子类实现，与通知逻辑解耦）
     * @param uid 用户ID
     * @return 摇号结果
     */
    public abstract LotteryResult doDraw(String uid);

}
