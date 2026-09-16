package com.ivanzhao.IDesign.CoR.impl;

import com.ivanzhao.IDesign.CoR.AuthLink;
import com.ivanzhao.IDesign.Infra.AuthInfo;
import com.ivanzhao.IDesign.Infra.AuthService;

import java.text.ParseException;
import java.util.Date;

/**
 * 一级审批节点（ConcreteHandler：具体处理者）
 * 
 * ==================== 【责任链模式：一级审批负责人（最高层）处理节点】 ====================
 * 
 * 业务规则：
 * 1. 条件准入规则：仅当单据申请时间处于特批核心窗口 [2020-06-11 00:00:00, 2020-06-20 23:59:59] 期间时，才需一级负责人（段总）审批；
 * 2. 状态判定：
 *    - 若处于审核时间窗口，且一级负责人尚未审批，阻断并提示“待一级审批负责人审核”；
 *    - 若未处于该时间窗口（无需一级审批），或一级负责人已审批通过，则尝试流转下一个节点；
 *    - 若无下一个节点，则标志整个责任链审批完成。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class Level1AuthLink extends AuthLink {

    /** 一级审批生效起始时间：2020-06-11 00:00:00 */
    private Date startDate;
    
    /** 一级审批生效截止时间：2020-06-20 23:59:59 */
    private Date endDate;

    /**
     * 构造函数：初始化一级审批人信息与审批生效时间区间
     * 
     * @param levelUserId   一级审批人ID（例如：1000011）
     * @param levelUserName 一级审批人姓名（例如：段总）
     * @throws ParseException 时间解析异常
     */
    public Level1AuthLink(String levelUserId, String levelUserName) throws ParseException {
        super(levelUserId, levelUserName);
        this.startDate = f.parse("2020-06-11 00:00:00");
        this.endDate = f.parse("2020-06-20 23:59:59");
    }

    /**
     * 一级审批业务执行与责任链流转
     */
    @Override
    public AuthInfo doAuth(String uId, String orderId, Date authDate) {
        // 步骤 1：查询一级审批负责人是否已经审批
        Date date = AuthService.queryAuthInfo(levelUserId, orderId);

        // 步骤 2：若尚未审批，判定当前时间是否落在一级审批的管辖时间区间内
        if (null == date) {
            // 处于特批时间窗口内，必须一级负责人审批
            if (authDate.after(startDate) && authDate.before(endDate)) {
                return new AuthInfo("0001", "单号：", orderId, " 状态：待一级审批负责人 ", levelUserName);
            }
        }

        // 步骤 3：已审批或无需一级审批，尝试流转给下一级节点
        //从父类 AuthLink 提供的 next() 方法中，拿到当前审批节点后面连接的那个审批节点，并把它保存到局部变量 next 中
        AuthLink next = super.next();

        // 步骤 4：若无后续节点，审批全流程结束
        if (null == next) {
            return new AuthInfo("0000", "单号：", orderId, " 状态：审批完成");
        }

        // 步骤 5：移交下一个节点继续判定
        return next.doAuth(uId, orderId, authDate);
    }
}
