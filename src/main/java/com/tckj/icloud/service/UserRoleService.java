package com.tckj.icloud.service;

import com.tckj.icloud.pojo.UserRole;

public interface UserRoleService {
   UserRole getUserRoleByUserId(Integer userId);

   UserRole addUserRole(UserRole ur);
}
