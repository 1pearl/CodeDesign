package com.ivanzhao.IDesign.CoR.Myimpl;

import com.ivanzhao.IDesign.CoR.AuthLink;
import com.ivanzhao.IDesign.Infra.AuthInfo;
import com.ivanzhao.IDesign.Infra.AuthService;

import java.text.ParseException;
import java.util.Date;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class Level2Link extends AuthLink {

    private Date beginDate;
    private Date endDate;


    /**
     * 构造函数：初始化当前节点的审批人信息
     *
     * @param levelUserId   审批人ID
     * @param levelUserName 审批人姓名
     */
    public Level2Link(String levelUserId, String levelUserName) throws ParseException {
        super(levelUserId, levelUserName);
        beginDate = f.parse("2020-06-01 00:00:00");
        endDate = f.parse("2020-06-25 23:59:59");
    }

    @Override
    public AuthInfo doAuth(String uId, String orderId, Date authDate) {
        Date date = AuthService.queryAuthInfo(uId, orderId);
        if(date == null) {
            if(date.after(beginDate) && date.before(endDate)) {
                return new AuthInfo("0001", "单号：", orderId, " 状态：待二级审批负责人 ", levelUserName);
            }
        }
        AuthLink next = super.next();
        if(next == null) {
            return new AuthInfo("0000", "单号：", orderId, " 状态：审批完成");
        }

        return next.doAuth(uId, orderId, authDate);
    }
}
