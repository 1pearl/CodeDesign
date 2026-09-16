package com.ivanzhao.IDesign.common;

import com.ivanzhao.IDesign.Infra.Activity;
import com.ivanzhao.IDesign.Infra.Stock;

import java.util.Date;

/**
 * 传统模式活动控制器（未采用享元模式的反面教材）
 * 性能痛点分析：
 * 1. 每次接口收到调用请求，都会在 JVM 堆内存（Eden区）中无条件执行 new Activity()，产生全新的对象实例；
 * 2. 活动的名称、描述、起止时间等完全相同的字符串常量与对象被重复分配，内存占用随 QPS 线性激增；
 * 3. 在大促秒杀高并发场景下，产生大量的短命大对象，迅速触发频繁的 Minor GC，甚至导致 Stop-The-World (STW) 停顿。
 */
public class ActivityController {

    /**
     * 查询活动详情（传统方式）
     * 
     * @param id 活动ID
     * @return 每次均全新 new 出来的活动对象
     */
    public Activity queryActivityInfo(Long id) {
        // -------------------------------------------------------------
        // 【痛点 1】：每次调用都在堆上开辟新内存，没有进行对象实例复用
        // -------------------------------------------------------------
        Activity activity = new Activity();
        
        // -------------------------------------------------------------
        // 【痛点 2】：静态不变的内部元数据被反复初始化赋值
        // -------------------------------------------------------------
        activity.setId(10001L);
        activity.setName("图书嗨乐");
        activity.setDesc("图书优惠券分享激励分享活动第二期");
        activity.setStartTime(new Date());
        activity.setStopTime(new Date());
        
        // -------------------------------------------------------------
        // 【痛点 3】：库存写死或者同样每次重新 new，无法反映实时高并发扣减动态
        // -------------------------------------------------------------
        activity.setStock(new Stock(1000, 1));
        
        return activity;
    }

}