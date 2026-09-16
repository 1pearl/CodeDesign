package com.ivanzhao.IDesign.My.mymodel;

import java.util.Map;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class MyTreeRich {
    private MyTreeRoot root;//树的根节点
    public Map<Long, MyTreeNode> treeNodeMap;//节点id与节点对应关系

    public MyTreeRich(MyTreeRoot root, Map<Long, MyTreeNode> treeNodeMap) {
        this.root = root;
        this.treeNodeMap = treeNodeMap;
    }

    public MyTreeRoot getRoot() {
        return root;
    }

    public void setRoot(MyTreeRoot root) {
        this.root = root;
    }

    public Map<Long, MyTreeNode> getTreeNodeMap() {
        return treeNodeMap;
    }

    public void setTreeNodeMap(Map<Long, MyTreeNode> treeNodeMap) {
        this.treeNodeMap = treeNodeMap;
    }
}
