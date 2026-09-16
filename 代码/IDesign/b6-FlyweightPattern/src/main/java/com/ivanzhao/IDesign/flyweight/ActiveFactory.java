package com.ivanzhao.IDesign.flyweight;

import com.ivanzhao.IDesign.Infra.Activity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 享元工厂类（Flyweight Factory）
 * 核心职责：管理与维护享元对象池，负责创建并复用活动元数据（内部状态）
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class ActiveFactory {

    /**
     * 享元池容器：以活动ID（Long）为Key，缓存不可变的活动基础信息对象
     * 注意：生产环境中若面对高并发读写，推荐使用 ConcurrentHashMap 保障线程安全性
     */
    static Map<Long, Activity> activities = new HashMap<Long, Activity>();

    /**
     * 获取享元对象（核心工厂方法）
     * 业务逻辑：
     * 1. 优先从内存池中查找，如果已存在直接复用（避免重复 new 对象造成内存浪费与 GC）；
     * 2. 如果不存在，则模拟从底层数据库查询活动不可变的基础元数据，并存入享元池；
     * 3. 注意：此处坚决不装配 Stock（库存），因为库存属于频繁变动的“外部状态”，由外部服务动态注入。
     * @param id 活动ID
     * @return 包含内部状态（只读元数据）的 Activity 共享对象
     */
    public static Activity getActivity(Long id) {
        // 步骤 1：从享元池中检索目标活动对象
        Activity activity = activities.get(id);

        // 步骤 2：未命中缓存，执行懒加载构建并入池
        if (activity == null) {
            activity = new Activity();
            activity.setId(id); // 使用传入的实际活动ID
            activity.setName("图书可乐");
            activity.setDesc("图书优惠分享");
            activity.setStartTime(new Date());
            activity.setStopTime(new Date());

            // 【核心修复】：将初始化好的内部状态对象放入享元池，后续请求即可直接复用！
            activities.put(id, activity);
        }

        // 步骤 3：返回共享实例
        return activity;
    }
}
