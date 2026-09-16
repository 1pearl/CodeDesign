package com.ivanzhao.IDesign.flyweight;

import com.ivanzhao.IDesign.Infra.Activity;
import com.ivanzhao.IDesign.Infra.Stock;
import com.ivanzhao.IDesign.Infra.util.RedisUtils;

/**
 * 享元模式升级版业务控制器
 * 核心设计思想：
 * 1. 内部状态复用：调用 ActiveFactory.getActivity(id) 获取不可变的基础活动信息（全局共享同一个对象）；
 * 2. 外部状态注入：调用 RedisUtils.getStockUsed() 实时获取正在被并发抢购消耗的库存数据，动态注入到 Stock 中；
 * 3. 达到“基础大对象零重复创建、实时动态数据毫秒级精准”的双赢效果。
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class ActivityController {

    /**
     * 模拟 Redis 分布式缓存服务（外部状态数据源）
     */
    private RedisUtils redisUtils = new RedisUtils();

    /**
     * 查询秒杀活动详情（享元模式业务入口）
     * @param id 活动ID
     * @return 包含共享元数据与实时动态库存的活动对象
     */
    public Activity queryActivityInfo(Long id) {
        // -------------------------------------------------------------
        // 【核心步骤 1：内部状态共享】
        // 从享元工厂获取共享对象。无论并发调用多少次，同一ID返回的都是同一物理内存地址的对象！
        // -------------------------------------------------------------
        Activity activity = ActiveFactory.getActivity(id);

        // -------------------------------------------------------------
        // 【核心步骤 2：外部状态动态提取】
        // 库存属于随用户抢购而时刻变化的外部状态，必须实时从 Redis 读取
        // -------------------------------------------------------------
        int stockUsed = redisUtils.getStockUsed();
        Stock stock = new Stock(1000, stockUsed);

        // -------------------------------------------------------------
        // 【核心步骤 3：将外部状态组装到活动对象中返回】
        // -------------------------------------------------------------
        activity.setStock(stock);

        return activity;
    }

}
