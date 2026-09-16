package com.ivanzhao.IDesign.CoR.impl;

import com.ivanzhao.IDesign.CoR.AuthLink;
import com.ivanzhao.IDesign.Infra.AuthInfo;
import com.ivanzhao.IDesign.Infra.AuthService;

import java.util.Date;

/**
 * 三级审批节点（ConcreteHandler：具体处理者）
 * 
 * ==================== 【责任链模式：三级审批责任人处理节点】 ====================
 * 
 * 业务规则：
 * 1. 基础必须审批层级：无论单据发生在哪一天，都必须先由三级审批负责人（如王工 1000013）审核；
 * 2. 状态判定：
 *    - 若三级负责人尚未审批（AuthService.queryAuthInfo 为空），阻断请求并返回“待三级审批负责人审核”；
 *    - 若已审批，则检查是否存在下一个更高层级的审批节点（next）：
 *      - 若存在下一个节点，责任向后传递：next.doAuth(...)；
 *      - 若不存在下一个节点，整条责任链执行完毕，返回“审批完成”。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class Level3AuthLink extends AuthLink {

    /**
     * 构造函数
     * 
     * @param levelUserId   三级审批人ID（例如：1000013）
     * @param levelUserName 三级审批人姓名（例如：王工）
     */
    public Level3AuthLink(String levelUserId, String levelUserName) {
        super(levelUserId, levelUserName);
    }

    /**
     * 三级审批业务执行
     * 
     * @param uId      申请人ID
     * @param orderId  待审批单号
     * @param authDate 审批申请时间
     * @return 审批流转信息
     */
    @Override
    public AuthInfo doAuth(String uId, String orderId, Date authDate) {
        // 步骤 1：查询三级审批负责人是否已经对该单号执行过审批
        Date date = AuthService.queryAuthInfo(levelUserId, orderId);

        // 步骤 2：如果尚未审批，则阻断流程，当前节点拦截并返回待办提示
        if (null == date) {
            return new AuthInfo("0001", "单号：", orderId, " 状态：待三级审批负责人 ", levelUserName);
        }

        // 步骤 3：如果三级已审批通过，获取责任链中的下一个审批节点
        AuthLink next = super.next();

        // 步骤 4：若无后续节点，说明该审批链在此终结，全流程完成
        if (null == next) {
            return new AuthInfo("0000", "单号：", orderId, " 状态：审批完成");
        }

        // 步骤 5：责任流转——移交给链条上的下一个审批节点继续判定
        return next.doAuth(uId, orderId, authDate);
    }
}
