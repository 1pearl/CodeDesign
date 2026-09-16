package test.state;

import com.alibaba.fastjson.JSON;
import com.ivanzhao.IDesign.common.ActivityExecStatusController;
import com.ivanzhao.IDesign.common.Result;
import com.ivanzhao.IDesign.infra.ActivityService;
import com.ivanzhao.IDesign.infra.Status;
import com.ivanzhao.IDesign.state.StateHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 状态模式单元测试类
 * <p>
 * 验证营销活动在不同生命周期状态下的行为流转：
 * 1. {@link #test_Editing2Arraignment()}: 验证合法状态迁移（编辑中 -> 提审）；
 * 2. {@link #test_Editing2Open()}: 验证非法状态拦截（编辑中 -> 直接开启，被状态机拦截）；
 * 3. {@link #test_FullLifecycle()}: 验证活动完整正向生命周期全链路流转；
 * 4. {@link #test_CommonVsState()}: 传统 if-else 方案与状态模式解耦方案的对照测试。
 *
 * @author ivanzhao
 */
public class ApiTest {

    private final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    /**
     * 测试一：合法状态流转（编辑中 -> 提审成功 -> 状态变更为待审核）
     */
    @Test
    public void test_Editing2Arraignment() {
        logger.info("\n==================== 【测试一：合法状态流转测试】 ====================");
        String activityId = "100001";
        ActivityService.init(activityId, Status.Editing);

        StateHandler stateHandler = new StateHandler();
        Result result = stateHandler.arraignment(activityId, Status.Editing);

        logger.info("测试结果(编辑中To提审活动)：{}", JSON.toJSONString(result));
        logger.info("活动信息：{} 状态：{}", JSON.toJSONString(ActivityService.queryActivityInfo(activityId)), JSON.toJSONString(ActivityService.queryActivityInfo(activityId).getStatus()));
    }

    /**
     * 测试二：非法状态流转拦截（编辑中 -> 企图直接开启活动 -> 状态机防御驳回）
     */
    @Test
    public void test_Editing2Open() {
        logger.info("\n==================== 【测试二：非法状态拦截测试】 ====================");
        String activityId = "100001";
        ActivityService.init(activityId, Status.Editing);

        StateHandler stateHandler = new StateHandler();
        Result result = stateHandler.open(activityId, Status.Editing);

        logger.info("测试结果(编辑中To开启活动)：{}", JSON.toJSONString(result));
        logger.info("活动信息：{} 状态：{}", JSON.toJSONString(ActivityService.queryActivityInfo(activityId)), JSON.toJSONString(ActivityService.queryActivityInfo(activityId).getStatus()));
    }

    /**
     * 测试三：活动完整全生命周期流转测试（正向链路）
     * 链路：编辑中(Editing) -> 提审(Check) -> 审核通过(Pass) -> 启动活动(Doing) -> 关闭活动(Close) -> 重新开启(Open)
     */
    @Test
    public void test_FullLifecycle() {
        logger.info("\n==================== 【测试三：活动完整生命周期全流程流转】 ====================");
        String activityId = "100002";
        // 1. 初始化活动为编辑中
        ActivityService.init(activityId, Status.Editing);
        StateHandler stateHandler = new StateHandler();
        logger.info("1. [初始状态] 当前状态: {}", ActivityService.queryActivityStatus(activityId));

        // 2. 提审：Editing -> Check
        Result r1 = stateHandler.arraignment(activityId, ActivityService.queryActivityStatus(activityId));
        logger.info("2. [提审操作] 结果: {} -> 新状态: {}", r1.getInfo(), ActivityService.queryActivityStatus(activityId));

        // 3. 审核通过：Check -> Pass
        Result r2 = stateHandler.checkPass(activityId, ActivityService.queryActivityStatus(activityId));
        logger.info("3. [审核通过] 结果: {} -> 新状态: {}", r2.getInfo(), ActivityService.queryActivityStatus(activityId));

        // 4. 执行活动：Pass -> Doing
        Result r3 = stateHandler.doing(activityId, ActivityService.queryActivityStatus(activityId));
        logger.info("4. [启动活动] 结果: {} -> 新状态: {}", r3.getInfo(), ActivityService.queryActivityStatus(activityId));

        // 5. 关闭活动：Doing -> Close
        Result r4 = stateHandler.close(activityId, ActivityService.queryActivityStatus(activityId));
        logger.info("5. [关闭活动] 结果: {} -> 新状态: {}", r4.getInfo(), ActivityService.queryActivityStatus(activityId));

        // 6. 重新开启：Close -> Open
        Result r5 = stateHandler.open(activityId, ActivityService.queryActivityStatus(activityId));
        logger.info("6. [重新开启] 结果: {} -> 新状态: {}", r5.getInfo(), ActivityService.queryActivityStatus(activityId));
    }

    /**
     * 测试四：传统 if-else 控制器 vs 状态模式控制器对照
     */
    @Test
    public void test_CommonVsState() {
        logger.info("\n==================== 【测试四：传统 if-else vs 状态模式对照】 ====================");
        String actId1 = "200001";
        String actId2 = "200002";
        ActivityService.init(actId1, Status.Editing);
        ActivityService.init(actId2, Status.Editing);

        // 传统方式
        ActivityExecStatusController controller = new ActivityExecStatusController();
        Result commonRes = controller.execStatus(actId1, Status.Editing, Status.Check);
        logger.info("传统 if-else 控制器执行结果: {}", JSON.toJSONString(commonRes));

        // 状态模式方式
        StateHandler stateHandler = new StateHandler();
        Result stateRes = stateHandler.arraignment(actId2, Status.Editing);
        logger.info("状态模式 StateHandler 执行结果: {}", JSON.toJSONString(stateRes));
    }

}