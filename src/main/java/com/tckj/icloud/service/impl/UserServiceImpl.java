package com.tckj.icloud.service.impl;

import com.alibaba.druid.util.StringUtils;
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
        List<User> userList = userMapper.selectList(new EntityWrapper<User>().eq("name", user.getName()).eq("password", user.getPassword()));
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public Boolean isRegisterUser(User user) {
        List<User> userList = userMapper.selectList(new EntityWrapper<User>().eq("name", user.getName()).eq("alarm", user.getAlarm()));
        if (userList != null && userList.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean addUser(User user) {
        return userMapper.insert(user) > 0;
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> getUsers(User user) {
        if (!StringUtils.isEmpty(user.getName()) && !StringUtils.isEmpty(user.getAlarm())) {
            return userMapper.selectList(new EntityWrapper<User>().eq("name", user.getName()));
        } else if (StringUtils.isEmpty(user.getName()) && !StringUtils.isEmpty(user.getAlarm())) {
            return userMapper.selectList(new EntityWrapper<User>().eq("alarm", user.getAlarm()));
        } else {
            return userMapper.selectList(null);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectList(null);
    }

}
