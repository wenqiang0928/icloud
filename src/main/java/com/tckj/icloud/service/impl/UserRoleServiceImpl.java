package com.tckj.icloud.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tckj.icloud.mapper.UserRoleMapper;
import com.tckj.icloud.pojo.UserRole;
import com.tckj.icloud.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
        implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserRole getUserRoleByUserId(Integer userId) {
        List<UserRole> list = userRoleMapper.selectList(new EntityWrapper<UserRole>().eq("user_id", userId));
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public UserRole addUserRole(UserRole ur) {
       int a= userRoleMapper.insert(ur);
       if (a>0){
           return getUserRoleByUserId(ur.getUserId());
       }
       return null;
    }
}
