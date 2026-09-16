package com.ivanzhao.IDesign.Infra;

import java.util.Date;

/**
 * 活动实体类（在享元模式中作为具体享元对象承载体）
 * 
 * 享元属性划分：
 * 1. 内部状态（Intrinsic State）：
 *    - id、name、desc、startTime、stopTime
 *    - 特征：在整个秒杀活动周期内是固定不变、只读的，可以被多个线程/请求安全共享复用。
 * 
 * 2. 外部状态（Extrinsic State）：
 *    - stock（库存总量与已用量）
 *    - 特征：随用户抢购支付而高频动态变动，不能被静态保存在共享单例中，必须由外部数据源（如Redis）动态注入。
 */
public class Activity {

    // ==================== 【内部状态（不可变、可共享）】 ====================
    /** 活动ID（唯一标识） */
    private Long id;
    /** 活动名称 */
    private String name;
    /** 活动描述详情 */
    private String desc;
    /** 活动生效开始时间 */
    private Date startTime;
    /** 活动失效结束时间 */
    private Date stopTime;

    // ==================== 【外部状态（高频变动、非共享）】 ====================
    /** 活动实时库存信息 */
    private Stock stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}