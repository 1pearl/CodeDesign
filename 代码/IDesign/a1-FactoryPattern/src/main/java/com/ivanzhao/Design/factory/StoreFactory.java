package com.ivanzhao.Design.factory;

import com.ivanzhao.Design.factory.store.Impl.CardCommodityService;
import com.ivanzhao.Design.factory.store.Impl.CouponCommodityService;
import com.ivanzhao.Design.factory.store.Impl.GoodsCommodityService;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class StoreFactory {

    public ICommodity getCommodityService(Integer commodityType) {
        if(null == commodityType) return null;
        if (1 == commodityType) return new CouponCommodityService();
        if (2 == commodityType) return new GoodsCommodityService();
        if (3 == commodityType) return new CardCommodityService();
        throw new RuntimeException("不存在的奖品服务");
    }

    /**
     * 奖品信息实例化
     * @param clazz 奖品类
     * @return      实例化对象
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public ICommodity getCommodityService(Class<? extends ICommodity> clazz) throws IllegalAccessException,InstantiationException {
        if (null == clazz) return null;
        return clazz.newInstance();
    }
}
