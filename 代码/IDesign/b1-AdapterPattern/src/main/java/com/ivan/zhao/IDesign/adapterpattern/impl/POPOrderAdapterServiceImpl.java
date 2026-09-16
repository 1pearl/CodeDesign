package com.ivan.zhao.IDesign.adapterpattern.impl;

import com.ivan.zhao.IDesign.adapterpattern.OrderAdapterService;
import com.ivan.zhao.IDesign.infra.services.POPOrderService;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class POPOrderAdapterServiceImpl implements OrderAdapterService {

    private POPOrderService popOrderService = new POPOrderService();

    @Override
    public boolean isFirst(String uId) {
        return popOrderService.isFirstOrder(uId);
    }
}

