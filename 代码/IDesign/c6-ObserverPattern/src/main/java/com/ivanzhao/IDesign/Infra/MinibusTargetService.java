package com.ivanzhao.IDesign.Infra;

/**
 * 小客车指标调控基础设施服务（模拟外部业务系统/底层摇号核心服务）
 * 模拟小客车指标摇号的核心计算逻辑，根据传入的用户ID计算是否中签。
 * 在观察者模式案例中，该服务代表“核心主业务”环节，作为触发后续各类事件通知（如短信、MQ）的数据源头。
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class MinibusTargetService {

    /**
     * 模拟摇号处理
     * @param uId 用户申请编码/身份证唯一标识
     * @return 摇号结果通知文案（中签或未中签）
     */
    public String lottery(String uId) {
        return Math.abs(uId.hashCode()) % 2 == 0 
                ? "恭喜你，编码".concat(uId).concat("在本次摇号中签") 
                : "很遗憾，编码".concat(uId).concat("在本次摇号未中签或摇号资格已过期");
    }

}