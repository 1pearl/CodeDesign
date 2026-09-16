package com.ivanzhao.IDesign.My.myService.logic.impl;

import com.ivanzhao.IDesign.My.myService.logic.MyLogicFilter;
import com.ivanzhao.IDesign.My.mymodel.MyTreeNode;
import com.ivanzhao.IDesign.My.mymodel.MyTreeNodeLink;
import com.ivanzhao.IDesign.compositepattern.model.vo.TreeNodeLink;

import java.util.Map;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class MyUserAgeFilter implements MyLogicFilter {

    @Override
    public Long filter(MyTreeNode node, Map<String, String> decisionMatter) {
        //为什么要进行for循环遍历呢？不是一次只进行一个节点的判断吗？即便for了
        //第一轮也会得到if内的结果，然后直接返回了，也就是一轮，直接if不行行了？
        //为啥两个if后返回的内容是一致的，进行了相同的处理，我依旧感觉如果都要返回link.getToTreeId();
        //那么for和if都没必要，直接返回link.getToTreeId();就行了？
        String age = decisionMatter.get("age");
        //1 = ==
        //2 = >
        //3 = <
        //4 = <=
        //5 = >=
        for (MyTreeNodeLink link : node.getTreeNodeLinks()) {
            //age = 15
            //如果第一个link是 >(2) 18
            //那么第一个if(>(2)成立 && 15 > 18(不成立)) false
            //    第二个if(>(2) != <=(4)（不成立） && 15 <= 18) false
            if (link.getRuleType() == 2
                    && Double.parseDouble(age) > Double.parseDouble(link.getRuleValue())) {
                return link.getToTreeId();
            }// > 18
            if (link.getRuleType() == 4
                    && Double.parseDouble(age) <= Double.parseDouble(link.getRuleValue())) {
                return link.getToTreeId();
            }// <= 18
        }
        throw new IllegalStateException("没有匹配的规则");
    }
}
