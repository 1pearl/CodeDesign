package com.ivanzhao.Design.factory.store.Impl;

import com.alibaba.fastjson.JSON;
import com.ivanzhao.Design.BaseInfrastructure.card.IQiYiCardService;
import com.ivanzhao.Design.factory.ICommodity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class CardCommodityService implements ICommodity {

    private Logger logger = LoggerFactory.getLogger(CardCommodityService.class);

    //模拟注入
    private IQiYiCardService iqiYiCardService = new IQiYiCardService();

    @Override
    public void sendCommodity(String uid, String commodityId, String bizId, Map<String, String> extMap) throws Exception {
        String mobile = queryUserMobile(uid);
        iqiYiCardService.grantToken(mobile, bizId);
        logger.info("请求参数[爱奇艺兑换卡] => uId：{} commodityId：{} bizId：{} extMap：{}", uid, commodityId, bizId, JSON.toJSON(extMap));
        logger.info("测试结果[爱奇艺兑换卡]：success");
    }

    private String queryUserMobile(String uid) {
        return "11122233333";
    }
}
