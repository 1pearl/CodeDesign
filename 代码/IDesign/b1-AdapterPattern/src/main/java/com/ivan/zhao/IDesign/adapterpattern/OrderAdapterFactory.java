package com.ivan.zhao.IDesign.adapterpattern;

import com.ivan.zhao.IDesign.adapterpattern.impl.InsideOrderAdapterServiceImpl;
import com.ivan.zhao.IDesign.adapterpattern.impl.POPOrderAdapterServiceImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 订单适配器工厂：结合工厂模式统一管理和分发异构订单渠道适配器
 *
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class OrderAdapterFactory {

    /**
     * 适配器注册表（使用线程安全的 ConcurrentHashMap）
     */
    private static final Map<String, OrderAdapterService> adapterMap = new ConcurrentHashMap<>();

    static {
        // 预注册系统默认支持的订单渠道适配器
        adapterMap.put("INSIDE", new InsideOrderAdapterServiceImpl());
        adapterMap.put("POP", new POPOrderAdapterServiceImpl());
    }

    /**
     * 根据订单渠道类型获取对应的适配器实例
     *
     * @param orderType 渠道类型标识（如 "INSIDE", "POP"）
     * @return 对应的 OrderAdapterService 适配器实现
     */
    public static OrderAdapterService getAdapter(String orderType) {
        //trim():去掉字符串开头和结尾的空白字符。比如:| hello world |去掉后为|hello world|
        if (orderType == null || orderType.trim().isEmpty()) {
            throw new IllegalArgumentException("订单渠道类型不能为空！");
        }
        OrderAdapterService adapterService = adapterMap.get(orderType.toUpperCase());
        if (adapterService == null) {
            throw new IllegalArgumentException("未找到匹配的订单适配器类型: " + orderType);
        }
        return adapterService;
    }

    /**
     * 动态注册新的适配器（对外提供可扩展能力，严格符合开闭原则）
     *
     * @param orderType 渠道类型标识
     * @param adapterService 适配器实现类
     */
    public static void register(String orderType, OrderAdapterService adapterService) {
        if (orderType == null || adapterService == null) {
            throw new IllegalArgumentException("注册参数不能为空！");
        }
        adapterMap.put(orderType.toUpperCase(), adapterService);
    }

    public static void main(String[] args) {
        OrderAdapterService insideAdapter = OrderAdapterFactory.getAdapter("INSIDE");
        System.out.println("自营订单适配器首单校验测试(用户100001): " + insideAdapter.isFirst("100001"));

        OrderAdapterService popAdapter = OrderAdapterFactory.getAdapter("POP");
        System.out.println("POP订单适配器首单校验测试(用户100001): " + popAdapter.isFirst("100001"));
    }
}
