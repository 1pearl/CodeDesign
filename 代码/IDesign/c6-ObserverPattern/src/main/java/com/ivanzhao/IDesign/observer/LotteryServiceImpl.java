package com.ivanzhao.IDesign.observer;

import com.ivanzhao.IDesign.Infra.MinibusTargetService;
import com.ivanzhao.IDesign.common.LotteryResult;

import java.util.Date;

/**
 * 观察者模式下的摇号服务具体实现类 —— 【具体被观察者角色（Concrete Subject）】
 * 核心设计优势：
 * 1. 单一职责原则（SRP）：只关注核心摇号业务逻辑的实现，不再耦合发短信、发MQ等辅助逻辑；
 * 2. 开闭原则（OCP）：当需要新增或移除通知渠道时，完全不需要修改该类的代码；
 * 3. 业务纯粹：仅调用底层基础设施服务 {@link MinibusTargetService} 产生摇号结果，后续通知统一由父类中介机制交由观察者处理。
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class LotteryServiceImpl extends LotteryService {
    /** 模拟摇号基础设施服务 */
    private MinibusTargetService minibusTargetService = new MinibusTargetService();

    /**
     * 实现父类抽象方法：执行具体摇号逻辑
     * @param uId 用户唯一ID
     * @return 摇号结果实体
     */
    @Override
    public LotteryResult doDraw(String uId) {
        // 1. 调用核心摇号服务
        String lottery = minibusTargetService.lottery(uId);
        // 2. 组装并返回纯净的业务结果对象（短信与MQ发送由父类模板方法中的事件广播统一触发）
        return new LotteryResult(uId, lottery, new Date());
    }
}
