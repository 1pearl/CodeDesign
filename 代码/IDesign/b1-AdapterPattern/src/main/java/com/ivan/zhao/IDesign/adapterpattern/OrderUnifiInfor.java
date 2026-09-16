package com.ivan.zhao.IDesign.adapterpattern;

import java.sql.Date;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
//定义了通⽤的MQ消息体
public class OrderUnifiInfor {
    private String userId;
    private String bizId;
    private String bizTime;
    private String desc;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizTime() {
        return bizTime;
    }

    public void setBizTime(String bizTime) {
        this.bizTime = bizTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "OrderUnifiInfor{" +
                "userId='" + userId + '\'' +
                ", bizId='" + bizId + '\'' +
                ", bizTime=" + bizTime +
                ", desc='" + desc + '\'' +
                '}';
    }
}
