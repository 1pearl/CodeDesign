package com.ivanzhao.IDesign.My.myService.logic;

import com.ivanzhao.IDesign.My.mymodel.MyTreeNode;

import java.util.Map;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public interface MyLogicFilter {

    Long filter(MyTreeNode node, Map<String,String> decisionMatter);

}
