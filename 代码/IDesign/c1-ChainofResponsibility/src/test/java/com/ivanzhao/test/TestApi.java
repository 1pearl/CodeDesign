package com.ivanzhao.test;

import com.alibaba.fastjson.JSON;
import com.ivanzhao.IDesign.CoR.AuthLink;
import com.ivanzhao.IDesign.CoR.impl.Level1AuthLink;
import com.ivanzhao.IDesign.CoR.impl.Level2AuthLink;
import com.ivanzhao.IDesign.CoR.impl.Level3AuthLink;
import com.ivanzhao.IDesign.Infra.AuthService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 责任链模式测试用例
 * 
 * ==================== 【测试目的与流转机制验证】 ====================
 * 1. 验证链式结构组装：Level3 (王工) -> Level2 (张经理) -> Level1 (段总)；
 * 2. 验证审批流逐步推进：
 *    - 初始状态：没有任何人审批，责任链停留在 Level3，等待王工审批；
 *    - 王工审批后：重新执行责任链，Level3 放行，自动流转至 Level2，等待张经理审批；
 *    - 张经理审批后：Level2 放行，自动流转至 Level1，等待段总审批；
 *    - 段总审批后：整条链条畅通，最终返回“审批完成”。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class TestApi {

    private Logger logger = LoggerFactory.getLogger(TestApi.class);

    @Test
    public void test_AuthLink() throws ParseException {
        // -------------------------------------------------------------
        // 步骤 1：构建责任链（组装审批流程链条）
        // 顺序：Level3（三级审批/王工） -> Level2（二级审批/张经理） -> Level1（一级审批/段总）
        // -------------------------------------------------------------
        AuthLink authLink = new Level3AuthLink("1000013", "王工")
                .appendNext(new Level2AuthLink("1000012", "张经理")
                .appendNext(new Level1AuthLink("1000011", "段总")));

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = f.parse("2020-06-18 23:49:32");
        String orderId = "1000998004813441";
        String uId = "1000001";

        // -------------------------------------------------------------
        // 轮次 1：尚未进行任何审批
        // 期望结果：待三级审批负责人 王工
        // -------------------------------------------------------------
        logger.info("测试结果（初始提交）：{}", JSON.toJSONString(authLink.doAuth(uId, orderId, currentDate)));

        // -------------------------------------------------------------
        // 轮次 2：模拟三级负责人【王工】完成审批
        // 期望结果：待二级审批负责人 张经理
        // -------------------------------------------------------------
        AuthService.auth("1000013", orderId);
        logger.info("测试结果（王工审批后）：{}", JSON.toJSONString(authLink.doAuth(uId, orderId, currentDate)));

        // -------------------------------------------------------------
        // 轮次 3：模拟二级负责人【张经理】完成审批
        // 期望结果：待一级审批负责人 段总
        // -------------------------------------------------------------
        AuthService.auth("1000012", orderId);
        logger.info("测试结果（张经理审批后）：{}", JSON.toJSONString(authLink.doAuth(uId, orderId, currentDate)));

        // -------------------------------------------------------------
        // 轮次 4：模拟一级负责人【段总】完成审批
        // 期望结果：单号：1000998004813441 状态：审批完成
        // -------------------------------------------------------------
        AuthService.auth("1000011", orderId);
        logger.info("测试结果（段总审批后）：{}", JSON.toJSONString(authLink.doAuth(uId, orderId, currentDate)));
    }

}
