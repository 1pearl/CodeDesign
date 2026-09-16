package com.ivanzhao.IDesign.observer.evnet.listener;

import com.ivanzhao.IDesign.common.LotteryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MQ 消息事件监听器 —— 观察者模式中的【具体观察者角色（Concrete Observer）】
 * <p>
 * 职责：
 * 专门负责订阅并消费 MQ 消息广播通道中的事件。
 * 当摇号结果产生后，被观察者通知该监听器，该监听器模拟将中签结果异步推送至消息队列（如 RocketMQ / Kafka），
 * 供下游大数据分析、财务结算、归档等系统异步消费，实现核心业务与下游系统的彻底解耦。
 *
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class MQEventListener implements EventListener {

    private Logger logger = LoggerFactory.getLogger(MQEventListener.class);

    /**
     * 消费摇号结果事件，模拟发送 MQ 消息
     * @param result 摇号结果上下文对象
     */
    @Override
    public void doEvent(LotteryResult result) {
        logger.info("记录用户 {} 摇号结果(MQ)：{}", result.getuId(), result.getMsg());
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
