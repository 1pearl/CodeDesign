package com.ivanzhao.IDesign.My.observer.event.listener;

import com.ivanzhao.IDesign.common.LotteryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Observer;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class MQEventListener implements EventListener {

    private Logger logger = LoggerFactory.getLogger(com.ivanzhao.IDesign.observer.evnet.listener.MQEventListener.class);

    /**
     * 消费摇号结果事件，模拟发送 MQ 消息
     * @param result 摇号结果上下文对象
     */
    @Override
    public void doEvent(LotteryResult result) {
        logger.info("记录用户 {} 摇号结果(MQ)：{}", result.getuId(), result.getMsg());
    }

}
