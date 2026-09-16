package com.ivanzhao.IDesign.CoR;

import com.ivanzhao.IDesign.Infra.AuthInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 责任链抽象父类（Handler）
 * 
 * ==================== 【责任链模式：抽象处理者角色】 ====================
 * 1. 角色定义：定义了处理审批请求的接口，并维护一个指向下一个处理节点的引用（next）；
 * 2. 核心职责：
 *    - 封装通用的审批人信息（levelUserId、levelUserName）；
 *    - 提供链式节点挂载方法 appendNext()，实现责任链路的动态编排；
 *    - 声明抽象审批方法 doAuth()，由具体的子类节点去实现各自独特的审批业务逻辑。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public abstract class AuthLink {

    protected Logger logger = LoggerFactory.getLogger(AuthLink.class);

    /** 时间格式化工具，用于判定当前审批时间是否处于对应负责人的审核周期区间内 */
    protected SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /** 审批级别责任人ID */
    protected String levelUserId;
    
    /** 审批级别责任人姓名 */
    protected String levelUserName;

    /** 责任链中的下一个审批节点（指向下一个 Handler） */
    protected AuthLink next;

    /**
     * 构造函数：初始化当前节点的审批人信息
     * @param levelUserId   审批人ID
     * @param levelUserName 审批人姓名
     */
    public AuthLink(String levelUserId, String levelUserName) {
        this.levelUserId = levelUserId;
        this.levelUserName = levelUserName;
    }

    /**
     * 获取下一个审批节点
     * @return 下一个 AuthLink 节点
     */
    public AuthLink next() {
        return next;
    }

    /**
     * 获取下一个审批节点（Getter）
     */
    public AuthLink getNext() {
        return next;
    }

    /**
     * 链式挂载下一个处理节点
     * @param next 下一个审批节点
     * @return 当前节点实例，便于使用链式调用组装责任链
     */
    public AuthLink appendNext(AuthLink next) {
        this.next = next;
        return this;
    }

    /**
     * 抽象审批方法（核心执行方法）
     * 业务流转逻辑：
     * 1. 检查当前节点对应责任人是否已审批；
     * 2. 若未审批且当前时间处于其管辖范围，则阻断并返回“待当前责任人审批”；
     * 3. 若已审批或无须当前责任人处理，则流转给下一个节点（next.doAuth）；
     * 4. 若链条末尾均已通过，则返回“审批完成”。
     * @param uId      申请人用户ID
     * @param orderId  业务单号
     * @param authDate 申请/审核时间
     * @return 审批流转状态结果
     */
    public abstract AuthInfo doAuth(String uId, String orderId, Date authDate);

}
