package com.ivanzhao.IDesign.bridgepattern.channel;

import com.ivanzhao.IDesign.bridgepattern.mode.IPayMode;

import java.math.BigDecimal;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class AliPay extends Pay {

    //new AliPay(xxx)
    //      ↓
    //进入 AliPay 构造方法
    //      ↓
    //public AliPay(IPayMode payMode)
    //      ↓
    //super(payMode)
    //      ↓
    //调用 Pay(payMode)
    //      ↓
    //this.payMode = payMode
    //      ↓
    //Pay 中的 payMode 被赋值
    //      ↓
    //AliPay 创建完成

    public AliPay(IPayMode payMode) {
        super(payMode);
    }

    public String transfer(String uId, String tradeId, BigDecimal amount) {
        logger.info("模拟支付宝渠道支付划账开始。uId：{} tradeId：{} amount：{}", uId, tradeId, amount);
        //继承了父类的protected IPayMode payMode字段
        boolean security = payMode.security(uId);
        logger.info("模拟支付宝渠道支付风控校验。uId：{} tradeId：{} security：{}", uId, tradeId, security);
        if (!security) {
            logger.info("模拟支付宝渠道支付划账拦截。uId：{} tradeId：{} amount：{}", uId, tradeId, amount);
            return "0001";
        }
        logger.info("模拟支付宝渠道支付划账成功。uId：{} tradeId：{} amount：{}", uId, tradeId, amount);
        return "0000";
    }

}
