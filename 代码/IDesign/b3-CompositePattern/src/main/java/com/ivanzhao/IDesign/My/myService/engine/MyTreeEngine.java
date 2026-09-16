package com.ivanzhao.IDesign.My.myService.engine;

import com.ivanzhao.IDesign.My.myService.logic.MyLogicFilter;
import com.ivanzhao.IDesign.My.myService.logic.impl.MyUserAgeFilter;
import com.ivanzhao.IDesign.My.myService.logic.impl.MyUserGenderFilter;
import com.ivanzhao.IDesign.My.mymodel.MyTreeNode;
import com.ivanzhao.IDesign.My.mymodel.MyTreeRich;
import com.ivanzhao.IDesign.My.mymodel.MyTreeRoot;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class MyTreeEngine {

    private Map<String, MyLogicFilter> logicFilterMap = new HashMap<>();

    public MyTreeEngine() {
        logicFilterMap.put(
                "userAge",
                new MyUserAgeFilter()
        );
        logicFilterMap.put(
                "userGender",
                new MyUserGenderFilter()
        );
    }

    public String process(MyTreeRich tree,
                          Map<String,String> decisionMatter) {
        //1.获取根节点对象
        MyTreeRoot root = tree.getRoot();
        //2.根据rootId找到根节点实体
        MyTreeNode currentNode = tree.getTreeNodeMap().get(root.getTreeNodeId());
        //3.循环向下寻找
        //1 = 决策节点
        //2 = 结果节点
        while (currentNode.getTreeNodeType() != 2) {
            String treeNodeRule = currentNode.getTreeNodeRule();
            MyLogicFilter logicFilter = logicFilterMap.get(treeNodeRule);
            if (logicFilter == null) {
                throw new IllegalStateException("没有找到对应的规则处理器：" + treeNodeRule);
            }
            //4.判读当前节点，寻找下一个节点
            Long nextNodeId = logicFilter.filter(
                    currentNode,
                    decisionMatter
            );
            //5.根据ID寻找下一个节点
            currentNode = tree.getTreeNodeMap().get(nextNodeId);
        }
        //到达结果节点
        return currentNode.getTreeNodeValue();
    }

}
