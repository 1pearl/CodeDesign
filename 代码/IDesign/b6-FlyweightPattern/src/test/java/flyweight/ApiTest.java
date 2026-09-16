package flyweight;

import com.alibaba.fastjson.JSON;
import com.ivanzhao.IDesign.Infra.Activity;
import com.ivanzhao.IDesign.flyweight.ActivityController;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 享元模式升级版测试用例
 * 
 * 验证核心：
 * 1. 验证对象复用：无论循环调用多少次，获取到的 Activity 对象内存哈希码（identityHashCode）恒定不变，证明没有重复 new 对象；
 * 2. 验证外部状态联动：配合 RedisUtils 每 100ms 扣减 1 件库存，循环中每 sleep 1200ms，库存消耗量 used 稳定递增 12 件。
 */
public class ApiTest {

    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    // 【核心修复】：引入享元包下的 ActivityController，不再调用 common 下的传统控制器
    private ActivityController activityController = new ActivityController();

    @Test
    public void test_queryActivityInfo() throws InterruptedException {
        // 模拟连续进行 10 轮高频查询
        for (int idx = 0; idx < 10; idx++) {
            Long req = 10001L;
            
            // 调用享元控制器查询活动详情
            Activity activity = activityController.queryActivityInfo(req);

            // -------------------------------------------------------------
            // 【核心观测点】：
            // 1. System.identityHashCode(activity)：打印当前对象在 JVM 中的物理内存标识
            //    - 若为享元模式，10 次循环打印出的 HashCode 必然 100% 完全相同！
            // 2. activity.getStock().getUsed()：动态库存量
            //    - 每一轮休眠 1.2 秒，后台 Redis 恰好扣减 12 件，库存消耗呈规律递增！
            // -------------------------------------------------------------
            int objectMemoryHash = System.identityHashCode(activity);
            
            logger.info("测试结果：第 {} 轮 | 对象内存Hash: 0x{} | 活动ID: {} | 详情: {}",
                    idx + 1,
                    Integer.toHexString(objectMemoryHash),
                    req,
                    JSON.toJSONString(activity));

            // 每次查询间隔 1.2 秒（1200 毫秒）
            Thread.sleep(1200);
        }
    }

}