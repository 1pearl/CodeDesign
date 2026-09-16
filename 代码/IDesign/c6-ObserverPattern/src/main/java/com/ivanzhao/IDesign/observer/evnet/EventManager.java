package com.ivanzhao.IDesign.observer.evnet;

import com.ivanzhao.IDesign.common.LotteryResult;
import com.ivanzhao.IDesign.observer.evnet.listener.EventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 事件发布与订阅管理者 —— 观察者模式中的【目标角色 / 被观察者核心调度器（Subject / Event Bus）】
 * <p>
 * 核心职责：
 * 1. 维护事件类型（EventType）与对应观察者列表（EventListener List）的映射注册表；
 * 2. 提供订阅服务 {@link #subscribe(Enum, EventListener)}：允许观察者动态注册自己关心的事件；
 * 3. 提供退订服务 {@link #unsubscribe(Enum, EventListener)}：允许观察者动态取消订阅；
 * 4. 提供事件广播机制 {@link #notify(Enum, LotteryResult)}：当特定业务事件发生时，自动遍历并通知所有注册的观察者执行响应逻辑。
 *
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class EventManager {

    /** 事件类型枚举与对应事件监听器列表的映射注册表 */
    private Map<Enum<EventType>, List<EventListener>> listeners = new HashMap<>();

    /**
     * 构造函数：初始化支持的事件类型通道
     * @param eventTypes 可支持订阅的事件类型数组
     */
    @SafeVarargs
    public EventManager(Enum<EventType>... eventTypes) {
        for (Enum<EventType> eventType : eventTypes) {
            this.listeners.put(eventType, new ArrayList<>());
        }
    }

    /**
     * 事件类型枚举：定义系统支持的各类业务广播事件通道
     */
    public enum EventType {
        /** MQ 消息发送事件通道 */
        MQ,
        /** 短信通知发送事件通道 */
        Message
    }

    /**
     * 订阅事件（注册观察者）
     * @param eventType 目标事件通道类型
     * @param listener  待注册的具体观察者监听器实例
     */
    public void subscribe(Enum<EventType> eventType, EventListener listener) {
        if (eventType == null || listener == null) {
            return;
        }
        List<EventListener> users = listeners.get(eventType);
        users.add(listener);
    }

    /**
     * 取消订阅事件（注销观察者）
     * @param eventType 目标事件通道类型
     * @param listener  待注销的观察者监听器实例
     */
    public void unsubscribe(Enum<EventType> eventType, EventListener listener) {
        if (eventType == null || listener == null) {
            return;
        }
        List<EventListener> users = listeners.get(eventType);
        if (users != null) {
            users.remove(listener);
        }
    }

    /**
     * 广播通知（向对应事件通道的所有观察者推送消息）
     * @param eventType 发生的事件类型
     * @param result    事件发生时携带的上下文结果数据对象
     */
    public void notify(Enum<EventType> eventType, LotteryResult result) {
        List<EventListener> eventListeners = this.listeners.get(eventType);
        if (eventListeners != null && !eventListeners.isEmpty()) {
            for (EventListener listener : eventListeners) {
                listener.doEvent(result);
            }
        }
    }

}
