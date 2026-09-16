package com.ivanzhao.IDesign.common;

/**
 * 传统模式抽奖/摇号服务接口（对照组）
 * 定义了一站式的摇号抽奖操作，未引入观察者模式之前，所有的摇号、发短信、发MQ等逻辑都强耦合在此接口的实现类中。
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public interface LotteryService {

    /**
     * 执行摇号并同步完成所有辅助操作（短信、MQ）
     * @param uId 用户唯一ID
     * @return 摇号结果实体
     */
    LotteryResult doDraw(String uId);

}