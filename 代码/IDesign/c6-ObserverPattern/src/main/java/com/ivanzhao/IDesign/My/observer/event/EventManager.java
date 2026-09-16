package com.ivanzhao.IDesign.My.observer.event;


import com.ivanzhao.IDesign.My.observer.event.listener.EventListener;
import com.ivanzhao.IDesign.common.LotteryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class EventManager {
    public enum EventType {
        Message,
        MQ
    }
    Map<Enum<EventType>, List<EventListener>> map = new HashMap<>();
    public EventManager(Enum<EventType>... type) {
        for(Enum<EventType> e : type) {
            map.put(e, new ArrayList<>());
        }
    }
    public void subscribe(EventType type, EventListener listener) {
        List<EventListener> list = map.get(type);
        list.add(listener);
    }
    public void unsubscribe(EventType type, EventListener listener) {
        List<EventListener> list = map.get(type);
        list.remove(listener);
    }
    public void notify(Enum<EventType> type, LotteryResult result) {
        List<EventListener> eventListeners = map.get(type);
        for(EventListener e : eventListeners) {
            e.doEvent(result);
        }
    }
}
