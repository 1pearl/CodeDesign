package test.common;

import com.ivanzhao.Idesign.common.XiaoEr;
import org.junit.Test;

/**
 * 传统开发模式单元测试（对照组）
 * 
 * 验证目标：
 * 验证在传统模式下，客户端直接向小二传递数字编号（1, 2, 3, 4）进行点单。
 * 小二内部通过 if-else 分支写死文案并存入 Map，最后打印出来。
 */
public class ApiTest {

    @Test
    public void test() {
        // 传统方式：小二内部强耦合了数字与菜品的映射关系
        XiaoEr xiaoEr = new XiaoEr();
        
        // 传入数字代号点菜：1-粤菜、2-苏菜、3-鲁菜、4-川菜
        xiaoEr.order(1);
        xiaoEr.order(2);
        xiaoEr.order(3);
        xiaoEr.order(4);

        // 下单打印当前菜单
        xiaoEr.placeOrder();
    }

}