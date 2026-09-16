package com.ivanzhao.IDesign.bridgepattern.mode.impl;

import com.ivanzhao.IDesign.bridgepattern.mode.IPayMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PayFingerprintMode implements IPayMode {

    protected Logger logger = LoggerFactory.getLogger(PayCypher.class);

    public boolean security(String uId) {
        logger.info("指纹支付，风控校验指纹信息");
        return true;
    }

}