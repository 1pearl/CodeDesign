package test.command;

import com.ivanzhao.Idesign.command.XiaoEr;
import com.ivanzhao.Idesign.command.cook.impl.GuangDongCook;
import com.ivanzhao.Idesign.command.cook.impl.JiangSuCook;
import com.ivanzhao.Idesign.command.cook.impl.ShanDongCook;
import com.ivanzhao.Idesign.command.cook.impl.SiChuanCook;
import com.ivanzhao.Idesign.command.cuisine.ICuisine;
import com.ivanzhao.Idesign.command.cuisine.impl.GuangDoneCuisine;
import com.ivanzhao.Idesign.command.cuisine.impl.JiangSuCuisine;
import com.ivanzhao.Idesign.command.cuisine.impl.ShanDongCuisine;
import com.ivanzhao.Idesign.command.cuisine.impl.SiChuanCuisine;
import org.junit.Test;

/**
 * 命令模式测试用例
 * 
 * ==================== 【命令模式角色：客户端 (Client)】 ====================
 * 
 * 客户端核心职责与流转流程：
 * 1. 实例化真正的接收者对象（Receiver）：四位具备不同烹饪技能的专业厨师；
 * 2. 实例化具体的命令对象（ConcreteCommand）：创建具体的菜品命令，并将对应的厨师装配注入命令中；
 * 3. 实例化调用者（Invoker）：创建店小二实例，由小二负责接收并统一排队管理命令；
 * 4. 客户端向小二点单：调用 xiaoEr.order(cuisine)，小二将命令压入待执行队列；
 * 5. 触发批量执行：调用 xiaoEr.placeOrder()，小二统一发出通知，内部自动驱动各命令执行，
 *    最终各厨师根据自己的专业技能烹饪对应菜品。
 * 
 * 整个过程实现了：
 * “点单的客人”与“炒菜的厨师”完全不直接接触，
 * “传单的小二”与“具体的厨师”完全不直接依赖，
 * 一切依赖于抽象的“菜品命令（ICuisine）”！
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class ApiTest {

    @Test
    public void test() {
        // -------------------------------------------------------------
        // 步骤 1：创建具体命令，并将对应的接收者（厨师）绑定到命令中
        // -------------------------------------------------------------
        // 粤菜命令 -> 绑定广东厨师
        ICuisine guangDoneCuisine = new GuangDoneCuisine(new GuangDongCook());
        // 苏菜命令 -> 绑定江苏厨师
        ICuisine jiangSuCuisine = new JiangSuCuisine(new JiangSuCook());
        // 鲁菜命令 -> 绑定山东厨师
        ICuisine shanDongCuisine = new ShanDongCuisine(new ShanDongCook());
        // 川菜命令 -> 绑定四川厨师
        ICuisine siChuanCuisine = new SiChuanCuisine(new SiChuanCook());

        // -------------------------------------------------------------
        // 步骤 2：创建调用者（店小二）
        // -------------------------------------------------------------
        XiaoEr xiaoEr = new XiaoEr();

        // -------------------------------------------------------------
        // 步骤 3：点单（小二收集命令，加入待执行队列排队）
        // -------------------------------------------------------------
        xiaoEr.order(guangDoneCuisine);
        xiaoEr.order(jiangSuCuisine);
        xiaoEr.order(siChuanCuisine);
        xiaoEr.order(shanDongCuisine);

        // -------------------------------------------------------------
        // 步骤 4：下单并批量执行（小二统一通知后厨各个厨师执行烹饪）
        // -------------------------------------------------------------
        xiaoEr.placeOrder();
    }
}
