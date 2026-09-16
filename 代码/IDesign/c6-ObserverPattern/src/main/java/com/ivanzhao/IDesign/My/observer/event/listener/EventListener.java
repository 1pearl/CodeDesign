package com.ivanzhao.IDesign.My.observer.event.listener;

import com.ivanzhao.IDesign.common.LotteryResult;

/**
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
