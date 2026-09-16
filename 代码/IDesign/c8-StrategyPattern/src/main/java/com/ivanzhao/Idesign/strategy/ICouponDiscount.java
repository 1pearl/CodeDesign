package com.ivanzhao.Idesign.strategy;

import java.math.BigDecimal;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 * 折扣优惠券处理接口
 */
public interface ICouponDiscount<T> {

    //StockKeepingUnit(SKU):当前这个具体 SKU 的商品价格
    BigDecimal discountAmount(T couponInfo,BigDecimal skuPrice);

}
