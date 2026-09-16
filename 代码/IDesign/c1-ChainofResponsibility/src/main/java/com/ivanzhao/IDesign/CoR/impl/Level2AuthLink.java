package com.ivanzhao.IDesign.CoR.impl;

import com.ivanzhao.IDesign.CoR.AuthLink;
import com.ivanzhao.IDesign.Infra.AuthInfo;
import com.ivanzhao.IDesign.Infra.AuthService;

import java.text.ParseException;
import java.util.Date;

/**
 * 二级审批节点（ConcreteHandler：具体处理者）
 * 
 * ==================== 【责任链模式：二级审批负责人处理节点】 ====================
 * 
 * 业务规则：
 * 1. 条件准入规则：仅当单据申请时间处于 [2020-06-01 00:00:00, 2020-06-25 23:59:59] 期间时，才需要二级负责人审批；
 * 2. 状态判定：
 *    - 若处于审核时间窗口，且二级负责人尚未审批（queryAuthInfo 为空），阻断并提示“待二级审批负责人审核”；
 *    - 若未处于该时间窗口（无需二级审批），或二级负责人已审批通过，则自动将控制权传递给下一个节点（next.doAuth）；
 *    - 若无下一个节点，则标志整个责任链审批完成。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class Level2AuthLink extends AuthLink {

    /** 二级审批生效起始时间：2020-06-01 00:00:00 */
    private Date startDate;
    
    /** 二级审批生效截止时间：2020-06-25 23:59:59 */
    private Date endDate;

    /**
     * 构造函数：初始化二级审批人信息与审批生效时间区间
     * 
     * @param levelUserId   二级审批人ID（例如：1000012）
     * @param levelUserName 二级审批人姓名（例如：张经理）
     * @throws ParseException 时间解析异常
     */
    public Level2AuthLink(String levelUserId, String levelUserName) throws ParseException {
        super(levelUserId, levelUserName);
        this.startDate = f.parse("2020-06-01 00:00:00");
        this.endDate = f.parse("2020-06-25 23:59:59");
    }

    /**
     * 二级审批业务执行与责任链流转
     */
    @Override
    public AuthInfo doAuth(String uId, String orderId, Date authDate) {
        // 步骤 1：查询二级审批负责人是否已经审批
        Date date = AuthService.queryAuthInfo(levelUserId, orderId);

        // 步骤 2：若尚未审批，判定当前时间是否落在二级审批的管辖时间区间内
        if (null == date) {
            // 处于时间窗口内，必须二级负责人审批
            if (authDate.after(startDate) && authDate.before(endDate)) {
                return new AuthInfo("0001", "单号：", orderId, " 状态：待二级审批负责人 ", levelUserName);
            }
        }

        // 步骤 3：已审批或无需二级审批，尝试流转给下一级节点
        AuthLink next = super.next();

        // 步骤 4：若无后续更高审批节点，审批全流程结束
        if (null == next) {
            return new AuthInfo("0000", "单号：", orderId, " 状态：审批完成");
        }

        // 步骤 5：移交下一个节点（如一级审批段总）继续判定
        return next.doAuth(uId, orderId, authDate);
    }
}
