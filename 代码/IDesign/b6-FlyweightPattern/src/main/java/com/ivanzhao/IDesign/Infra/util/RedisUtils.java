package com.ivanzhao.IDesign.Infra.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟分布式缓存 Redis 的库存实时扣减工具
 * 
 * 机制解析：
 * 1. 启动一个单线程调度线程池（ScheduledExecutorService）；
 * 2. 模拟高并发抢购环境下，用户不断下单支付扣减库存，固定每隔 100,000 微秒（100毫秒）使已用库存 stock + 1；
 * 3. 供业务层实时获取当前的消耗数据，充当享元模式中的“外部状态数据源”。
 */
public class RedisUtils {

    /**
     * 定时任务调度器：后台线程持续模拟外界用户抢购
     */
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    /**
     * 原子计数器：多线程并发自增，保证内存可见性与操作原子性，初始消耗库存为 0
     */
    private AtomicInteger stock = new AtomicInteger(0);

    public RedisUtils() {
        // 调度规则：初始延迟 0 毫秒，每隔 100 毫秒（100,000 微秒）执行一次库存扣减
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // 模拟高并发下库存被抢购消耗：已用库存 + 1
            stock.addAndGet(1);
        }, 0, 100000, TimeUnit.MICROSECONDS);
    }

    /**
     * 获取当前 Redis 缓存中已被消耗锁定的实时库存量
     * @return 实时已消耗库存数
     */
    public int getStockUsed() {
        return stock.get();
    }

}