package com.ivanzhao.IDesign.bridgepattern.channel;

import com.ivanzhao.IDesign.bridgepattern.mode.IPayMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public abstract class Pay {


    protected Logger logger = LoggerFactory.getLogger(Pay.class);

    protected IPayMode payMode;

    public Pay(IPayMode payMode) {
        this.payMode = payMode;
    }

    public abstract String transfer(String uid, String tradeId, BigDecimal amount);
}
