package com.ivanzhao.IDesign.common;

import com.ivanzhao.IDesign.Infra.AuthInfo;
import com.ivanzhao.IDesign.Infra.AuthService;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 传统开发模式下的审批控制器（未采用责任链模式的反面教材）
 * 
 * 痛点分析：
 * 1. 严重违反开闭原则（OCP）：所有的审批层级（三级、二级、一级）以及条件判断（时间范围）全部强耦合在单个方法中；
 * 2. 难以维护与扩展：一旦业务新增四级审批（如财务总监）或者调整某级负责人的审核时间区间，必须深入修改核心业务方法中的 if-else 块；
 * 3. 缺乏灵活性：审批顺序完全被静态代码锁死，无法针对不同部门、不同类型的单据动态组合或插拔审批节点。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class AuthController {
    
    private SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 传统硬编码实现的审批流程
     * 
     * @param uId      申请人ID
     * @param orderId  单号
     * @param authDate 审批申请时间
     * @return 审批状态
     * @throws ParseException 时间解析异常
     */
    public AuthInfo doAuth(String uId, String orderId, Date authDate) throws ParseException {
        // -------------------------------------------------------------
        // 【硬编码 1】：三级审批（王工）强行写在代码首部
        // -------------------------------------------------------------
        Date date = AuthService.queryAuthInfo("1000013", orderId);
        if (null == date) return new AuthInfo("0001", "单号：", orderId, " 状态：待三级审批负责人 ", "王工");

        // -------------------------------------------------------------
        // 【硬编码 2】：二级审批（张经理）通过复杂嵌套 if-else 判断时间范围
        // -------------------------------------------------------------
        if (authDate.after(f.parse("2020-06-01 00:00:00")) && authDate.before(f.parse("2020-06-25 23:59:59"))) {
            date = AuthService.queryAuthInfo("1000012", orderId);
            if (null == date) return new AuthInfo("0001", "单号：", orderId, " 状态：待二级审批负责人 ", "张经理");
        }

        // -------------------------------------------------------------
        // 【硬编码 3】：一级审批（段总）再次嵌套 if-else 判定
        // -------------------------------------------------------------
        if (authDate.after(f.parse("2020-06-11 00:00:00")) && authDate.before(f.parse("2020-06-20 23:59:59"))) {
            date = AuthService.queryAuthInfo("1000011", orderId);
            if (null == date) return new AuthInfo("0001", "单号：", orderId, " 状态：待一级审批负责人 ", "段总");
        }

        // 全部审核通过
        return new AuthInfo("0001", "单号：", orderId, " 状态：审批完成");
    }

}
