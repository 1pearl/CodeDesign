package com.ivanzhao.Idesign.mediator.dao;

import com.ivanzhao.Idesign.mediator.po.User;

import java.util.List;

/**
 * 用户数据访问接口（DAO）
 * <p>
 * 在中介者模式中作为同事角色（Colleague），
 * 业务通过调用DAO方法（如根据ID查询用户信息），底层由中介者（SqlSession）统一路由到XML映射中的SQL并处理与数据库的交互。
 */
public interface IUserDao {

    /**
     * 根据用户ID查询单个用户信息
     *
     * @param id 用户ID
     * @return 用户实体对象
     */
    User queryUserInfoById(Long id);

    /**
     * 根据条件查询用户列表
     *
     * @param user 查询条件（封装在User对象中）
     * @return 用户列表
     */
    List<User> queryUserList(User user);

}