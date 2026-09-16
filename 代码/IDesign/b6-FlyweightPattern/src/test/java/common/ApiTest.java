package common;

import com.alibaba.fastjson.JSON;
import com.ivanzhao.IDesign.Infra.Activity;
import com.ivanzhao.IDesign.common.ActivityController;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 传统开发模式单元测试（对照组）
 * 
 * 测试目的：
 * 验证传统开发方式下的接口调用行为。
 * 在这种模式下，每次调用 queryActivityInfo 都会在 JVM 堆内存中创建一个全新的 Activity 对象。
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    // 传统控制器实例
    private ActivityController activityController = new ActivityController();

    @Test
    public void test_queryActivityInfo() {
        Long req = 10001L;
        // 查询活动信息（内部 new Activity）
        Activity activity = activityController.queryActivityInfo(req);
        
        // 打印查询结果与当前对象的内存地址哈希码
        logger.info("测试结果：活动ID: {} | 对象物理Hash: 0x{} | 数据: {}", 
                req, 
                Integer.toHexString(System.identityHashCode(activity)), 
                JSON.toJSONString(activity));
    }

}