package com.ivanzhao.IDesign.observer.evnet.listener;

import com.ivanzhao.IDesign.common.LotteryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 短信消息事件监听器 —— 观察者模式中的【具体观察者角色（Concrete Observer）】
 * <p>
 * 职责：
 * 专门负责订阅并消费短信通知广播通道中的事件。
 * 当摇号结果产生后，被观察者通知该监听器，该监听器模拟调用第三方短信网关服务，
 * 将中签或未中签的通知文案实时推送至用户的手机，与核心业务解耦，可按需启停。
 *
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class MessageEventListener implements EventListener {

    private Logger logger = LoggerFactory.getLogger(MessageEventListener.class);

    /**
     * 消费摇号结果事件，模拟发送短信通知
     * @param result 摇号结果上下文对象
     */
    @Override
    public void doEvent(LotteryResult result) {
        logger.info("给用户 {} 发送短信通知(短信)：{}", result.getuId(), result.getMsg());
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
