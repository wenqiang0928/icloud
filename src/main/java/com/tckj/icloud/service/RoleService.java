package com.tckj.icloud.service;

import com.tckj.icloud.pojo.Role;

public interface RoleService  {
   Role getRole(Integer id);
   Role addRole(Role role);
}
