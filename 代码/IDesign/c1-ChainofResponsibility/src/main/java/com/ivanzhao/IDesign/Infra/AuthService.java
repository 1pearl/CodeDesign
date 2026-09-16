package com.ivanzhao.IDesign.Infra;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模拟底层持久化服务：记录与查询审批流水
 * 
 * 核心功能：
 * 1. 提供模拟数据库/缓存存储已完成审批的历史记录（uId + orderId -> authDate）；
 * 2. queryAuthInfo：查询某位审批责任人是否已审批该单据；
 * 3. auth：模拟执行审批操作，将审批人ID与单据号绑定记录至数据库。
 */
public class AuthService {

    /** 模拟数据库审批流水记录表：Key = 审批人ID + 单号，Value = 审批通过时间 */
    private static Map<String, Date> authMap = new ConcurrentHashMap<>();

    /**
     * 查询指定人员对特定单号的审批时间记录
     * 
     * @param uId     审批负责人ID
     * @param orderId 待审单号
     * @return 审批时间（若未审批则返回 null）
     */
    public static Date queryAuthInfo(String uId, String orderId) {
        return authMap.get(uId.concat(orderId));
    }

    /**
     * 模拟审批人签署同意操作
     * 
     * @param uId     审批负责人ID
     * @param orderId 单号
     */
    public static void auth(String uId, String orderId) {
        authMap.put(uId.concat(orderId), new Date());
    }
}