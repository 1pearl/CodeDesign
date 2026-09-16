package com.ivanzhao.IDesign.My.mymodel;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class MyTreeRoot {
    private String treeRootName; //树名称
    private Long treeRootId;     //根的id
    private Long treeNodeId;     //对应的节点id

    public MyTreeRoot(String treeRootName, Long treeRootId, Long treeNodeId) {
        this.treeRootName = treeRootName;
        this.treeRootId = treeRootId;
        this.treeNodeId = treeNodeId;
    }

    public String getTreeRootName() {
        return treeRootName;
    }

    public void setTreeRootName(String treeRootName) {
        this.treeRootName = treeRootName;
    }

    public Long getTreeRootId() {
        return treeRootId;
    }

    public void setTreeRootId(Long treeRootId) {
        this.treeRootId = treeRootId;
    }

    public Long getTreeNodeId() {
        return treeNodeId;
    }

    public void setTreeNodeId(Long treeNodeId) {
        this.treeNodeId = treeNodeId;
    }
}
