package com.ivanzhao.IDesign.CoR.Myimpl;

import com.ivanzhao.IDesign.CoR.AuthLink;
import com.ivanzhao.IDesign.Infra.AuthInfo;
import com.ivanzhao.IDesign.Infra.AuthService;

import java.util.Date;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class Level3Link extends AuthLink {

    /**
     * 构造函数：初始化当前节点的审批人信息
     * @param levelUserId   审批人ID
     * @param levelUserName 审批人姓名
     */
    public Level3Link(String levelUserId, String levelUserName) {
        super(levelUserId, levelUserName);
    }

    @Override
    public AuthInfo doAuth(String uId, String orderId, Date authDate) {
        Date date = AuthService.queryAuthInfo(levelUserId, orderId);
        if(date == null) {
            return new AuthInfo("0001", "单号：", orderId, " 状态：待三级审批负责人 ", levelUserName);
        }
        AuthLink next = super.next();
        if(next == null) {
            return new AuthInfo("0000", "单号：", orderId, " 状态：审批完成");
        }
        return next.doAuth(uId, orderId, authDate);
    }
}
