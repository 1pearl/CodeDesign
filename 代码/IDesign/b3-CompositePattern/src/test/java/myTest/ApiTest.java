package myTest;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
import com.ivanzhao.IDesign.My.myService.engine.MyTreeEngine;
import com.ivanzhao.IDesign.My.mymodel.MyTreeNode;
import com.ivanzhao.IDesign.My.mymodel.MyTreeNodeLink;
import com.ivanzhao.IDesign.My.mymodel.MyTreeRich;
import com.ivanzhao.IDesign.My.mymodel.MyTreeRoot;

import java.util.HashMap;
import java.util.Map;

public class ApiTest {

    public static void main(String[] args) {

        /*
         * 构造一棵这样的规则树：
         *
         *                  年龄？
         *                 /     \
         *              >18      <=18
         *               ↓          ↓
         *             性别？      未成年
         *            /     \
         *          男       女
         *          ↓         ↓
         *        商品A      商品B
         */

        // =========================
        // 1. 创建结果节点
        // =========================
        MyTreeNode childNode = new MyTreeNode(
                3L,
                2,
                "未成年",
                null,
                null
        );
        MyTreeNode maleNode = new MyTreeNode(
                4L,
                2,
                "商品A",
                null,
                null
        );
        MyTreeNode femaleNode = new MyTreeNode(
                5L,
                2,
                "商品B",
                null,
                null
        );
        // =========================
        // 2. 创建性别判断节点
        // =========================

        MyTreeNode genderNode = new MyTreeNode(
                2L,
                1,
                null,
                "userGender",
                null
        );
        MyTreeNodeLink maleLink = new MyTreeNodeLink(
                2L,
                4L,
                1,
                "男"
        );
        MyTreeNodeLink femaleLink = new MyTreeNodeLink(
                2L,
                5L,
                1,
                "女"
        );
        genderNode.setTreeNodeLinks(
                java.util.List.of(maleLink, femaleLink)
        );
        // =========================
        // 3. 创建年龄判断节点
        // =========================
        MyTreeNode ageNode = new MyTreeNode(
                1L,
                1,
                null,
                "userAge",
                null
        );
        MyTreeNodeLink adultLink = new MyTreeNodeLink(
                1L,
                2L,
                2,
                "18"
        );
        MyTreeNodeLink childLink = new MyTreeNodeLink(
                1L,
                3L,
                4,
                "18"
        );
        ageNode.setTreeNodeLinks(
                java.util.List.of(adultLink, childLink)
        );

        // =========================
        // 4. 创建 TreeRoot
        // =========================
        MyTreeRoot root = new MyTreeRoot(
                "用户商品推荐规则树",
                100L,
                1L
        );
        // =========================
        // 5. 创建 TreeNodeMap
        // =========================
        Map<Long, MyTreeNode> treeNodeMap = new HashMap<>();
        treeNodeMap.put(1L, ageNode);
        treeNodeMap.put(2L, genderNode);
        treeNodeMap.put(3L, childNode);
        treeNodeMap.put(4L, maleNode);
        treeNodeMap.put(5L, femaleNode);
        // =========================
        // 6. 创建完整的 TreeRich
        // =========================
        MyTreeRich treeRich = new MyTreeRich(
                root,
                treeNodeMap
        );
        // =========================
        // 7. 创建用户决策数据
        // =========================
        Map<String, String> decisionMatter = new HashMap<>();
        decisionMatter.put("age", "20");
        decisionMatter.put("gender", "男");

        // =========================
        // 8. 执行规则引擎
        // =========================
        MyTreeEngine engine = new MyTreeEngine();
        String result = engine.process(
                treeRich,
                decisionMatter
        );

        // =========================
        // 9. 输出结果
        // =========================
        System.out.println("最终决策结果：" + result);
    }
}
