package com.tckj.icloud.service;

import com.tckj.icloud.pojo.User;
import com.tckj.icloud.vo.ResponseResult;

import java.util.List;

public interface UserService {
    /**
     * 获取User对象
     *
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 是否存在此用户 用于登录判断
     *
     * @param user
     * @return
     */
    User existUser(User user);

    /**
     * 是否存在此用户 用于添加用户
     *
     * @param user
     * @return
     */
    Boolean isRegisterUser(User user);
    /**
     * 新增用户
     * @param user
     * @return
     */
    Boolean addUser(User user);

    /**
     * 通过id查询用户
     * @param id
     * @return
     */
    User selectById(Integer id);

    /**
     * 根据条件获取用户列表
     * @param user
     * @return
     */
    List<User> getUsers(User user);

    /**
     * 获取用户列表
     * @param user
     * @return
     */
    List<User> getAllUsers();

    /**
     * 删除用户
     * @param user
     * @return
     */
    Boolean delUser(User user);
    /**
     * 修改密码
     * @param oldPassWord
     * @param newPassword
     * @param user
     * @return com.tckj.icloud.vo.ResponseResult
     * @author LiZG
     * @date 2019/04/09 22:10
     */
	ResponseResult modifyPassword(String oldPassWord, String newPassword,User user);
}
