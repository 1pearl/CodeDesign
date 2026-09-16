package com.ivanzhao.IDesign.Infra;

/**
 * 活动库存实体类（享元模式中的“非共享外部状态”载体）
 * 
 * 核心特征：
 * - 记录秒杀商品的总量与已售出使用量；
 * - 数据随外界时间流逝和用户下单并发扣减而每毫秒都在变化；
 * - 绝不能被静态放进享元池常驻共享，必须每次请求从外部数据源（如 Redis 分布式缓存）实时读取装配。
 */
public class Stock {

    /** 库存总量（例如此批次总库存 1000 件） */
    private int total;
    
    /** 库存已用（实时已抢购锁定的数量） */
    private int used;

    /**
     * 全参构造方法
     * @param total 库存总量
     * @param used  库存已消耗数量
     */
    public Stock(int total, int used) {
        this.total = total;
        this.used = used;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}