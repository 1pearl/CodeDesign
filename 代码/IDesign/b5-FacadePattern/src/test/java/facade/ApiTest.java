package facade;

import com.alibaba.fastjson.JSON;
import com.ivanzhao.Idesign.Infra.HelloWorldApplication;
import com.ivanzhao.Idesign.Infra.domain.UserInfo;
import com.ivanzhao.Idesign.Infra.web.HelloWorldController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Autowired
    private HelloWorldController helloWorldController;

    /**
     * 测试白名单用户访问：允许放行，返回真实用户信息
     */
    @Test
    public void test_queryUserInfo_whiteList() {
        // 白名单用户ID：1001 (配置在 application.yml: itstack.door.userStr)
        UserInfo userInfo = helloWorldController.queryUserInfo("1001");
        logger.info("白名单用户放行结果：{}", JSON.toJSONString(userInfo));
    }

    /**
     * 测试非白名单用户访问：触发拦截，返回预设的拦截JSON对象
     */
    @Test
    public void test_queryUserInfo_intercept() {
        // 非白名单用户ID：小团团
        UserInfo userInfo = helloWorldController.queryUserInfo("小团团");
        logger.info("非白名单用户拦截结果：{}", JSON.toJSONString(userInfo));
    }

}
