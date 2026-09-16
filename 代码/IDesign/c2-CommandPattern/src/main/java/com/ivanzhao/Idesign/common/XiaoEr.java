package com.ivanzhao.Idesign.common;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 传统模式下的店小二（普通代码编写 - 反面教材）
 * 
 * ==================== 【未采用命令模式的设计痛点】 ====================
 * 1. 紧耦合（Tight Coupling）：
 *    - 小二类（调用者）内部直接硬编码了所有菜系与厨师的业务文案和编号映射；
 *    - 点菜的请求逻辑与具体的菜品烹饪逻辑混杂在一起。
 * 
 * 2. 违反开闭原则（OCP）：
 *    - 如果餐馆需要新增菜系（如：湘菜、闽菜、徽菜、浙菜），必须修改 order() 方法中的 if-else 分支；
 *    - 如果厨师调换或菜品烹饪细节调整，也必须修改小二类代码，侵入性极强。
 * 
 * 3. 缺乏行为对象化封装：
 *    - 仅使用数字编号（int cuisine）代表请求，无法对“点菜请求”进行排队、撤销、重做、日志持久化或异步调度等高级操作。
 */
public class XiaoEr {

    private Logger logger = LoggerFactory.getLogger(XiaoEr.class);

    /**
     * 存放点菜单的映射表（Key: 菜品编号, Value: 描述信息）
     */
    private Map<Integer, String> cuisineMap = new ConcurrentHashMap<Integer, String>();

    /**
     * 点单方法（传统硬编码 if-else 方式）
     * 
     * @param cuisine 菜品分类编号：1-粤菜, 2-苏菜, 3-鲁菜, 4-川菜
     */
    public void order(int cuisine) {
        // -------------------------------------------------------------
        // 【痛点】：分支判断写死，调用者直接依赖具体的业务分支
        // -------------------------------------------------------------
        // 1: 广东（粤菜）
        if (1 == cuisine) {
            cuisineMap.put(1, "广东厨师，烹饪粤菜，中国八大菜系之一，选料广博，注重清鲜脆嫩。");
        }

        // 2: 江苏（苏菜）
        if (2 == cuisine) {
            cuisineMap.put(2, "江苏厨师，烹饪苏菜，宫廷第二大菜系，古今国宴上最受人欢迎的菜系。");
        }

        // 3: 山东（鲁菜）
        if (3 == cuisine) {
            cuisineMap.put(3, "山东厨师，烹饪鲁菜，宫廷最大菜系，以孔府风味为龙头。");
        }

        // 4: 四川（川菜）
        if (4 == cuisine) {
            cuisineMap.put(4, "四川厨师，烹饪川菜，中国最有特色的菜系，也是民间最大菜系。");
        }
    }

    /**
     * 下单出单：批量打印当前点菜单
     */
    public void placeOrder() {
        logger.info("菜单：{}", JSON.toJSONString(cuisineMap));
    }

}