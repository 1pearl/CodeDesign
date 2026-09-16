package com.ivanzhao.Idesign.strategy.impl;

import com.ivanzhao.Idesign.strategy.ICouponDiscount;

import java.math.BigDecimal;

/**
 * n元购买
 */
public class NYGCouponDiscount implements ICouponDiscount<Double> {

    /**
     * n元购购买
     * 1. 无论原价多少钱都固定金额购买
     */
    public BigDecimal discountAmount(Double couponInfo, BigDecimal skuPrice) {
        return new BigDecimal(couponInfo);
    }

}