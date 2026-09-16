package com.ivanzhao.Idesign.mediator.dao;

import com.ivanzhao.Idesign.mediator.po.School;

/**
 * 学校数据访问接口（DAO）
 * <p>
 * 在中介者模式结构中作为业务调用方/同事角色（Colleague），
 * 业务层通过DAO接口定义操作，具体执行时交由中介者（SqlSession）协调底层JDBC驱动、SQL语句解析和结果集映射。
 */
public interface ISchoolDao {

    /**
     * 根据学校ID查询学校信息
     *
     * @param treeId 学校ID
     * @return 学校实体对象
     */
    School querySchoolInfoById(Long treeId);

}