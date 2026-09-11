package com.ivanzhao.Design.factory.store.Impl;

import com.ivanzhao.Design.BaseInfrastructure.goods.DeliverReq;
import com.ivanzhao.Design.BaseInfrastructure.goods.GoodsService;
import com.ivanzhao.Design.factory.ICommodity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class GoodsCommodityService implements ICommodity {

    Logger logger = LoggerFactory.getLogger(GoodsCommodityService.class);

    //模拟注入
    GoodsService goodsService = new GoodsService();

    @Override
    public void sendCommodity(String uid, String commodityId, String bizId, Map<String, String> extMap) throws Exception {
        DeliverReq deliverReq = new DeliverReq();
        deliverReq.setUserName(queryUserName(uid));
        deliverReq.setUserPhone(queryUserPhoneNumber(uid));
        deliverReq.setSku(commodityId);
        deliverReq.setOrderId(bizId);
        deliverReq.setConsigneeUserName(extMap.get("consigneeUserName"));
        deliverReq.setConsigneeUserPhone(extMap.get("consigneeUserPhone"));
        deliverReq.setConsigneeUserAddress(extMap.get("consigneeUserAddress"));

        Boolean isSuccess = goodsService.deliverGoods(deliverReq);

        if (!isSuccess) throw new RuntimeException("实物商品发放失败");
    }

    private String queryUserPhoneNumber(String uid) {
        return "11122212345";
    }

    private String queryUserName(String uid) {
        return "mia";
    }


}
