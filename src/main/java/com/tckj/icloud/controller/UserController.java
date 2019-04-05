package com.tckj.icloud.controller;

import com.tckj.icloud.pojo.Role;
import com.tckj.icloud.pojo.User;
import com.tckj.icloud.pojo.UserRole;
import com.tckj.icloud.service.RoleService;
import com.tckj.icloud.service.UserRoleService;
import com.tckj.icloud.service.UserService;
import com.tckj.icloud.vo.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    @GetMapping("")
    public String login() {
        return "index";
    }

    @RequestMapping("login")
    public String loginConfirm(User user, HttpSession session) {
        User userDb = userService.existUser(user);
        if (userDb != null) {
            session.setAttribute("userName", user.getName());
            UserRole ur = userRoleService.getUserRoleByUserId(userDb.getId());
            Role role = roleService.getRole(ur.getRoleId());
            if (role != null) {
                session.setAttribute("userRole", role.getName());
            } else {
                session.setAttribute("userRole", "public");
            }
           // return new SuccessResponse(true);
           return "home";
        } else {
            //return new SuccessResponse(false);
           return "login";
        }
    }

    /**
     * 路由到主页
     * @return
     */
    @GetMapping("home")
    public String home() {
        return "home";
    }
    @PostMapping("addUser")
    public String addUser(String name, String password, String alarm, Integer roleId) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setAlarm(alarm);
        userService.addUser(user);
        user = userService.existUser(user);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(roleId);
        userRoleService.addUserRole(userRole);
        return "home";
    }
}
