package com.ivanzhao.IDesign.My.mymodel;

import java.util.List;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class MyTreeNode {
    private Long treeNodeId;      //节点的Id
    private Integer treeNodeType; //节点的类型（判断/成果）
    private String treeNodeValue; //节点的值
    private String treeNodeRule;  //节点的规则
    private List<MyTreeNodeLink> treeNodeLinks; //节点的连接方向

    public MyTreeNode(Long treeNodeId, Integer treeNodeType, String treeNodeValue, String treeNodeRule, List<MyTreeNodeLink> treeNodeLinks) {
        this.treeNodeId = treeNodeId;
        this.treeNodeType = treeNodeType;
        this.treeNodeValue = treeNodeValue;
        this.treeNodeRule = treeNodeRule;
        this.treeNodeLinks = treeNodeLinks;
    }

    public Long getTreeNodeId() {
        return treeNodeId;
    }

    public void setTreeNodeId(Long treeNodeId) {
        this.treeNodeId = treeNodeId;
    }

    public Integer getTreeNodeType() {
        return treeNodeType;
    }

    public void setTreeNodeType(Integer treeNodeType) {
        this.treeNodeType = treeNodeType;
    }

    public String getTreeNodeValue() {
        return treeNodeValue;
    }

    public void setTreeNodeValue(String treeNodeValue) {
        this.treeNodeValue = treeNodeValue;
    }

    public String getTreeNodeRule() {
        return treeNodeRule;
    }

    public void setTreeNodeRule(String treeNodeRule) {
        this.treeNodeRule = treeNodeRule;
    }

    public List<MyTreeNodeLink> getTreeNodeLinks() {
        return treeNodeLinks;
    }

    public void setTreeNodeLinks(List<MyTreeNodeLink> treeNodeLinks) {
        this.treeNodeLinks = treeNodeLinks;
    }
}
