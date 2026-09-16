package com.ivanzhao.IDesign.My.myService.logic.impl;

import com.ivanzhao.IDesign.My.myService.logic.MyLogicFilter;
import com.ivanzhao.IDesign.My.mymodel.MyTreeNode;
import com.ivanzhao.IDesign.My.mymodel.MyTreeNodeLink;

import java.util.Map;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class MyUserGenderFilter implements MyLogicFilter {


    @Override
    public Long filter(MyTreeNode node, Map<String, String> decisionMatter) {
        String gender = decisionMatter.get("gender");
        for(MyTreeNodeLink link : node.getTreeNodeLinks()) {
            //男：1，女：2
            // ruleType = 1 表示 ==
            if(link.getRuleType() == 1 &&
               link.getRuleValue().equals(gender)
            ) {
                return link.getToTreeId();
            }
        }

        throw new IllegalStateException("没有匹配的规则");
    }
}
