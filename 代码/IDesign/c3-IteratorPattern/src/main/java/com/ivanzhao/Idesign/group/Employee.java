package com.ivanzhao.Idesign.group;

/**
 * 雇员实体类（Element / Item）
 * 
 * ==================== 【迭代器模式角色：聚合元素对象 (Element)】 ====================
 * 1. 角色定义：存放在聚合容器（Collection / Aggregate）中的业务数据实体。
 * 2. 模式中的作用：
 *    - 作为迭代器 Iterator<Employee> 遍历过程中不断产出并返回的目标对象（E next() 的返回值）；
 *    - 纯粹保存业务属性（雇员ID、姓名、所属部门/描述），不关心任何迭代控制逻辑或指针关系；
 *    - 实现了数据载体与集合遍历逻辑的彻底解耦。
 * 
 * @author 赵一帆(Ivan Zhao)
 * @version 1.0
 */
public class Employee {

    /** 雇员全局唯一ID */
    private String uId;

    /** 雇员姓名 */
    private String name;

    /** 雇员备注说明（例如：二级部门、三级部门等层级描述） */
    private String desc;

    public Employee(String uId, String name) {
        this.uId = uId;
        this.name = name;
    }

    public Employee(String uId, String name, String desc) {
        this.uId = uId;
        this.name = name;
        this.desc = desc;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}