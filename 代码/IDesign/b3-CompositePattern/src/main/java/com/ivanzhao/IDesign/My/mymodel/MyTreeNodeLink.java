package com.ivanzhao.IDesign.My.mymodel;

/**
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class MyTreeNodeLink {
    private Long fromTreeId; //来自哪个节点
    private Long toTreeId;   //要进入哪个节点
    private Integer ruleType; //判断规则
    private String ruleValue; //判断值

    public MyTreeNodeLink(Long fromTreeId, Long toTreeId, Integer ruleType, String ruleValue) {
        this.fromTreeId = fromTreeId;
        this.toTreeId = toTreeId;
        this.ruleType = ruleType;
        this.ruleValue = ruleValue;
    }

    public Long getFromTreeId() {
        return fromTreeId;
    }

    public void setFromTreeId(Long fromTreeId) {
        this.fromTreeId = fromTreeId;
    }

    public Long getToTreeId() {
        return toTreeId;
    }

    public void setToTreeId(Long toTreeId) {
        this.toTreeId = toTreeId;
    }

    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }
}
