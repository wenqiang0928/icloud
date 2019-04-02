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
    Boolean existUser(User user);
}
