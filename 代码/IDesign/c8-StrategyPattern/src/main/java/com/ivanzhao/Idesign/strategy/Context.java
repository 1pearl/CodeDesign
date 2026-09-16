package com.ivanzhao.Idesign.strategy;

import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 * 调用管理类
 */
public class Context<T> {

    private ICouponDiscount<T> couponDiscount;

    public Context(ICouponDiscount<T> couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public BigDecimal discountAmount(T couponInfo, BigDecimal skuPrice) {
        return couponDiscount.discountAmount(couponInfo, skuPrice);
    }

}
