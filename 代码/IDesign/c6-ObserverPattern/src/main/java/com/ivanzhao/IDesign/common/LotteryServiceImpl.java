package com.ivanzhao.IDesign.common;

import com.ivanzhao.IDesign.Infra.MinibusTargetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 传统模式抽奖服务实现类（对照组 / 反模式案例）
 * 【未引入观察者模式前的代码痛点】：
 * 1. 违反单一职责原则（SRP）：该实现类既负责调用核心服务执行摇号，又负责发送短信通知，还负责发送 MQ 消息同步数据，承担了过多的职责；
 * 2. 违反开闭原则（OCP）：如果后续新增需求（如：中签用户还要发送微信公众号模板消息、发送邮件、增加风控审计等），
 *    或者需要对未中签用户暂停发短信，开发人员必须直接修改此方法的核心业务流程代码，极易引发线上回归故障；
 * 3. 难以维护与测试：核心业务链路与下游边缘业务紧密耦合，无法针对短信或 MQ 发送逻辑进行单独替换、降级或单元测试。
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class LotteryServiceImpl implements LotteryService {
    private Logger logger = LoggerFactory.getLogger(LotteryServiceImpl.class);
    /** 模拟摇号基础设施服务 */
    private MinibusTargetService minibusTargetService = new MinibusTargetService();
    /**
     * 一坨式执行摇号、发短信与发MQ
     * @param uId 用户唯一ID
     * @return 摇号结果
     */
    @Override
    public LotteryResult doDraw(String uId) {
        // 1. 执行核心业务：调用摇号服务
        String lottery = minibusTargetService.lottery(uId);
        // 2. 强耦合辅助业务：直接同步发短信
        logger.info("给用户 {} 发送短信通知(短信)：{}", uId, lottery);
        // 3. 强耦合辅助业务：直接同步发MQ消息
        logger.info("记录用户 {} 摇号结果(MQ)：{}", uId, lottery);
        // 4. 返回组装结果
        return new LotteryResult(uId, lottery, new Date());
    }
}