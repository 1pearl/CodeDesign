package com.ivanzhao.IDesign.common;

import java.util.Date;

/**
 * 摇号结果业务实体（事件数据载体 / Event Payload）
 * 封装单次小客车指标摇号的完整业务结果数据，包含用户唯一ID、摇号中签文案以及结果生成时间戳。
 * 在观察者模式中，该对象作为事件被触发时广播给所有观察者（EventListener）的核心上下文参数。
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class LotteryResult {

    /** 用户唯一编号（身份证/申请编码） */
    private String uId;

    /** 摇号详细反馈信息（中签通知或未中签提示） */
    private String msg;

    /** 摇号业务发生的时间戳 */
    private Date dateTime;

    public LotteryResult() {}

    public LotteryResult(String uId, String msg, Date dateTime) {
        this.uId = uId;
        this.msg = msg;
        this.dateTime = dateTime;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

}