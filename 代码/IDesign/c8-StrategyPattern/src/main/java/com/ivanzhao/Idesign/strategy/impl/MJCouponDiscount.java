package com.ivanzhao.Idesign.strategy.impl;

import com.ivanzhao.Idesign.strategy.ICouponDiscount;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class MJCouponDiscount implements ICouponDiscount<Map<String,String>> {


    /**
     * 满减计算，最低支付1元，满x减去n
     * @param couponInfo
     * @param skuPrice
     * @return
     */
    @Override
    public BigDecimal discountAmount(Map<String, String> couponInfo, BigDecimal skuPrice) {
        String x = couponInfo.get("x");
        String n = couponInfo.get("n");

        //skuPrice < x,小于商品的全额条件，直接返回skuPrice
        if(skuPrice.compareTo(new BigDecimal(x)) < 0) return skuPrice;
        //减去优惠金判断
        BigDecimal discountAmount = skuPrice.subtract(new BigDecimal(n));
        // 满减后金额小于等于0，保底支付1元；否则返回正常满减后金额
        if (discountAmount.compareTo(BigDecimal.ZERO) < 1) {
            return BigDecimal.ONE;
        }
        return discountAmount;
    }
}
