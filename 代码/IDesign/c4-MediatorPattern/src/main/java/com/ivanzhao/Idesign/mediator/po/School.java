package com.ivanzhao.Idesign.mediator.po;

import java.util.Date;

/**
 * 学校实体类（PO - Persistent Object）
 * <p>
 * 对应数据库表 school，存储学校相关信息，供中介者模式映射查询结果。
 */
public class School {

    /** 学校自增主键ID */
    private Long id;
    /** 学校名称 */
    private String name;
    /** 学校地址 */
    private String address;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}