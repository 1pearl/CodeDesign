package com.ivanzhao.IDesign.My.observer;

import com.ivanzhao.IDesign.My.observer.event.EventManager;
import com.ivanzhao.IDesign.My.observer.event.listener.MQEventListener;
import com.ivanzhao.IDesign.My.observer.event.listener.MessageEventListener;
import com.ivanzhao.IDesign.common.LotteryResult;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public abstract class LotteryService {

    EventManager eventManager;

    public LotteryService() {
        eventManager = new EventManager(EventManager.EventType.Message,EventManager.EventType.MQ);
        eventManager.subscribe(EventManager.EventType.Message,new MessageEventListener());
        eventManager.subscribe(EventManager.EventType.MQ,new MQEventListener());
    }

    public LotteryService(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public LotteryResult draw(String uid) {
        LotteryResult result = doDraw(uid);
        eventManager.notify(EventManager.EventType.Message,result);
        eventManager.notify(EventManager.EventType.MQ,result);
        return result;
    }

    public abstract LotteryResult doDraw(String uid);

}
