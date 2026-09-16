package com.ivanzhao.IDesign.compositepattern.model.aggregates;

import com.ivanzhao.IDesign.compositepattern.model.vo.TreeRoot;
import com.ivanzhao.IDesign.compositepattern.model.vo.TreeNode;
import java.util.Map;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class TreeRich {
    private TreeRoot treeRoot;                      //树根信息
    private Map<Long, TreeNode> treeNodeMap;        //树节点ID -> 子节点

    public TreeRich(TreeRoot treeRoot, Map<Long, TreeNode> treeNodeMap) {
        this.treeRoot = treeRoot;
        this.treeNodeMap = treeNodeMap;
    }

    public TreeRoot getTreeRoot() {
        return treeRoot;
    }

    public void setTreeRoot(TreeRoot treeRoot) {
        this.treeRoot = treeRoot;
    }

    public Map<Long, TreeNode> getTreeNodeMap() {
        return treeNodeMap;
    }

    public void setTreeNodeMap(Map<Long, TreeNode> treeNodeMap) {
        this.treeNodeMap = treeNodeMap;
    }
}
