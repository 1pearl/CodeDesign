package com.ivanzhao.Design.factory;

import java.util.Map;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 * 整合一下要用到的字段，规整一下接口，方便工程方法调用
 */
public interface ICommodity {

    /**
     * 发送商品的整合接口
     * @param uid 用户Id
     * @param commodityId 货品Id
     * @param bizId 业务Id
     * @param extMap 额外信息
     * @throws Exception
     */
    void sendCommodity(String uid,
                       String commodityId,
                       String bizId,
                       Map<String, String> extMap) throws Exception;

}
