package com.ivanzhao.Idesign.mediator.po;

import java.util.Date;

/**
 * 用户实体类（PO - Persistent Object）
 * <p>
 * 对应数据库表 user，存储用户信息，在中介者模式中作为数据载体和查询条件对象。
 */
public class User {

    /** 用户主键ID */
    private Long id;
    /** 用户姓名 */
    private String name;
    /** 用户年龄 */
    private Integer age;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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