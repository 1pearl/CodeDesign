package com.ivanzhao.Design.factory.store.Impl;

import com.alibaba.fastjson.JSON;
import com.ivanzhao.Design.BaseInfrastructure.coupon.CouponResult;
import com.ivanzhao.Design.BaseInfrastructure.coupon.CouponService;
import com.ivanzhao.Design.factory.ICommodity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class CouponCommodityService implements ICommodity {

    Logger logger = LoggerFactory.getLogger(CouponCommodityService.class);

    //模拟注入
    CouponService couponService = new CouponService();

    @Override
    public void sendCommodity(String uid, String commodityId, String bizId, Map<String, String> extMap) throws Exception {
        CouponResult couponResult = couponService.sendCoupon(uid, commodityId, bizId);
        logger.info("请求参数[优惠券] => uId：{} commodityId：{} bizId：{} extMap：{}", uid, commodityId, bizId, JSON.toJSON(extMap));
        logger.info("测试结果[优惠券]：{}", JSON.toJSON(couponResult));
        if(!"0000".equals(couponResult.getCode())) throw new RuntimeException(couponResult.getInfo());
    }
}
