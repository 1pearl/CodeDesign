package com.ivan.zhao.IDesign.adapterpattern.impl;

import com.ivan.zhao.IDesign.adapterpattern.OrderAdapterService;
import com.ivan.zhao.IDesign.infra.services.OrderService;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class InsideOrderAdapterServiceImpl implements OrderAdapterService {

    private OrderService orderService = new OrderService();

    @Override
    public boolean isFirst(String uId) {
        return orderService.queryUserOrderCount(uId) <= 1;
    }
}
