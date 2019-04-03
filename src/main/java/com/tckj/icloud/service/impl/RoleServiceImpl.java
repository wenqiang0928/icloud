package com.tckj.icloud.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tckj.icloud.mapper.RoleMapper;
import com.tckj.icloud.pojo.Role;
import com.tckj.icloud.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role>  implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public Role getRole(Integer id) {
        return roleMapper.selectById(id);
    }

    @Override
    public Role addRole(Role role) {
        int a= roleMapper.insert(role);
        if (a>0){
            List<Role> list= roleMapper.selectList(new EntityWrapper<Role>().eq("role_name",role.getName()));
            if(list!=null && list.size()>0){
                return list.get(0);
            }
            return null;
        }
        return null;
    }
}
