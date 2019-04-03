package com.tckj.icloud.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tckj.icloud.mapper.UserMapper;
import com.tckj.icloud.pojo.User;
import com.tckj.icloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User getUser(User user) {
        return userMapper.selectById(user.getId());
    }

    @Override
    public User existUser(User user) {
        if (userMapper.selectById(user.getId()) != null) {
            return null;
        }
        List<User> userList = userMapper.selectList(new EntityWrapper<User>().eq("name",user.getName()).eq("password",user.getPassword()));
        if (userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }
}
