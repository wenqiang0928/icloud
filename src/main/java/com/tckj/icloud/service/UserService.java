package com.tckj.icloud.service;

import com.tckj.icloud.pojo.User;

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
}
