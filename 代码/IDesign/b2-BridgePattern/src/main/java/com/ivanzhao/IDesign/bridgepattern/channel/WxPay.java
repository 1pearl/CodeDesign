package com.ivanzhao.IDesign.bridgepattern.channel;

import com.ivanzhao.IDesign.bridgepattern.mode.IPayMode;

import java.math.BigDecimal;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class WxPay extends Pay {

    public WxPay(IPayMode payMode) {
        super(payMode);
    }

    @Override
    public String transfer(String uid, String tradeId, BigDecimal amount) {
        logger.info("模拟微信渠道支付划账开始。uId：{} tradeId：{} amount：{}", uid, tradeId, amount);
        boolean security = payMode.security(uid);
        logger.info("模拟微信渠道支付风控校验。uId：{} tradeId：{} security：{}", uid, tradeId, security);
        if (!security) {
            logger.info("模拟微信渠道支付划账拦截。uId：{} tradeId：{} amount：{}", uid, tradeId, amount);
            return "0001";
        }
        logger.info("模拟微信渠道支付划账成功。uId：{} tradeId：{} amount：{}", uid, tradeId, amount);
        return "0000";
    }
}
