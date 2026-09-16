package common;

import com.alibaba.fastjson.JSON;
import com.ivanzhao.Idesign.Infra.domain.UserInfo;
import com.ivanzhao.Idesign.common.HelloWorldController;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_HelloWorldController() {
        HelloWorldController controller = new HelloWorldController();

        // 1. 测试白名单用户
        UserInfo userInfoWhite = controller.queryUserInfo("1001");
        logger.info("传统方式-白名单用户访问结果：{}", JSON.toJSONString(userInfoWhite));

        // 2. 测试非白名单用户
        UserInfo userInfoBlocked = controller.queryUserInfo("小团团");
        logger.info("传统方式-非白名单用户访问结果：{}", JSON.toJSONString(userInfoBlocked));
    }

}
