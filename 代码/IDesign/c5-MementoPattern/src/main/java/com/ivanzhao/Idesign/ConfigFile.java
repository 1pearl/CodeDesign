package com.ivanzhao.Idesign;

import java.util.Date;

/**
 * 配置文件实体类 —— 备忘录模式中的【状态实体（State Object）】
 * <p>
 * 存储系统配置的具体数据，包含版本号、配置文本内容、修改时间以及操作人信息。
 * 在备忘录模式中，该对象作为发起人（Originator）需要记录和恢复的内部状态载体。
 *
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class ConfigFile {

    /** 配置文件版本号（如 "1000001"），作为版本的唯一业务标识 */
    private String versionNo;

    /** 配置项的具体文本内容 */
    private String content;

    /** 配置保存/生效时间戳 */
    private Date dateTime;

    /** 执行此次配置变更的操作人员 */
    private String operator;

    public ConfigFile() {}

    public ConfigFile(String versionNo, String content, Date dateTime, String operator) {
        this.versionNo = versionNo;
        this.content = content;
        this.dateTime = dateTime;
        this.operator = operator;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

}