package com.ivanzhao.IDesign.My.observer.event.listener;

import com.ivanzhao.IDesign.common.LotteryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class MessageEventListener implements EventListener {


    private Logger logger = LoggerFactory.getLogger(com.ivanzhao.IDesign.observer.evnet.listener.MessageEventListener.class);

    /**
     * 消费摇号结果事件，模拟发送短信通知
     * @param result 摇号结果上下文对象
     */
    @Override
    public void doEvent(LotteryResult result) {
        logger.info("给用户 {} 发送短信通知(短信)：{}", result.getuId(), result.getMsg());
    }
}
