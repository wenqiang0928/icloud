package com.tckj.icloud.vo;

import com.tckj.icloud.pojo.Role;
import com.tckj.icloud.pojo.User;
import com.tckj.icloud.pojo.UserRole;

public class UserDto {
    private User user;
    private Role role;
    private UserRole userRole;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
