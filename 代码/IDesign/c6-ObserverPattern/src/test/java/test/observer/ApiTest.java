package test.observer;

import com.alibaba.fastjson.JSON;
import com.ivanzhao.IDesign.common.LotteryResult;
import com.ivanzhao.IDesign.observer.LotteryService;
import com.ivanzhao.IDesign.observer.LotteryServiceImpl;
import com.ivanzhao.IDesign.observer.evnet.EventManager;
import com.ivanzhao.IDesign.observer.evnet.listener.MessageEventListener;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 观察者模式测试验证类
 * <p>
 * 本测试类提供对比测试：
 * 1. {@link #test_draw_common()}: 验证传统臃肿过程式设计（业务与通知强耦合）；
 * 2. {@link #test_draw_observer()}: 验证基于观察者模式解耦后的业务编排与事件分发；
 * 3. {@link #test_draw_observer_dynamic()}: 验证动态订阅与取消订阅机制，展示开闭原则（OCP）的高扩展性。
 *
 * @author ivanzhao
 */
public class ApiTest {
    private final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    /**
     * 测试一：传统方式（未解耦）
     * <p>
     * 业务流程：所有外部短信、MQ消息硬编码在 LotteryServiceImpl 内部。
     * 痛点：新增邮件通知或变更第三方服务时，必须修改核心业务逻辑。
     */
    @Test
    public void test_draw_common() {
        logger.info("\n==================== 【测试一：传统紧耦合模式】 ====================");
        LotteryService lotteryService = new LotteryServiceImpl();
        LotteryResult result = lotteryService.doDraw("1000000101010019");
        logger.info("传统方式摇号执行结果: {}", JSON.toJSONString(result));
    }

    /**
     * 测试二：观察者模式（基于事件发布-订阅机制完全解耦）
     * <p>
     * 核心设计：
     * 1. 抽奖主流程由抽象模板类 {@link LotteryService#draw(String)} 统一调度；
     * 2. 具体摇号算法由 {@link LotteryServiceImpl#doDraw(String)} 实现；
     * 3. 摇号完成后，通过 {@link EventManager} 广播通知，已注册的观察者（短信、MQ）自动触发。
     */
    @Test
    public void test_draw_observer() {
        logger.info("\n==================== 【测试二：观察者模式解耦测试】 ====================");
        LotteryService lotteryService = new LotteryServiceImpl();
        LotteryResult result = lotteryService.draw("1000000101010019");
        logger.info("观察者模式摇号执行结果: {}", JSON.toJSONString(result));
    }

    /**
     * 测试三：动态订阅与取消订阅测试
     * <p>
     * 演示观察者模式的核心优势：
     * 客户端可以在运行时动态装配、新增或剔除具体的监听器，无需修改任何摇号业务类。
     */
    @Test
    public void test_draw_observer_dynamic() {
        logger.info("\n==================== 【测试三：动态订阅/取消订阅扩展性测试】 ====================");
        LotteryService lotteryService = new LotteryServiceImpl();

        logger.info("--- 1. 动态取消短信监听器订阅 ---");
        lotteryService.eventManager.unsubscribe(EventManager.EventType.Message, new MessageEventListener());
        logger.info("--- 2. 仅保留 MQ 监听器触发摇号 ---");
        LotteryResult resultOnlyMQ = lotteryService.draw("2765789108761234");
        logger.info("仅MQ监听器结果: {}", JSON.toJSONString(resultOnlyMQ));
        logger.info("--- 3. 重新装配短信监听器并再次摇号 ---");
        lotteryService.eventManager.subscribe(EventManager.EventType.Message, new MessageEventListener());
        LotteryResult resultAll = lotteryService.draw("9876543210987654");
        logger.info("恢复双监听器结果: {}", JSON.toJSONString(resultAll));
    }
}