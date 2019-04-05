package com.tckj.icloud.controller;

import com.tckj.icloud.pojo.Role;
import com.tckj.icloud.pojo.User;
import com.tckj.icloud.pojo.UserRole;
import com.tckj.icloud.service.RoleService;
import com.tckj.icloud.service.UserRoleService;
import com.tckj.icloud.service.UserService;
import com.tckj.icloud.vo.SuccessResponse;
import com.tckj.icloud.vo.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    private static String PREFIX = "/user/";

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
     *
     * @return
     */
    @GetMapping("home")
    public String home() {
        return "home";
    }

    @GetMapping("addUser")
    public String addUser() {
        return PREFIX + "user";
    }

    @PostMapping("getUsers")
    @ResponseBody
    public Object getUsers(User user) {
        //此方法要优化，暂时这么写
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = userService.getUsers(user);
        for (User u : users) {
            UserDto userDto = new UserDto();
            userDto.setUser(u);
            UserRole userRole = userRoleService.getUserRoleByUserId(u.getId());
            userDto.setUserRole(userRole);
            userDto.setRole(roleService.getRole(userRole.getRoleId()));
            userDtos.add(userDto);
        }
        return new SuccessResponse(userDtos);
    }

    @PostMapping("userAdd")
    @ResponseBody
    public Object userAdd(String name, String password, String alarm) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setAlarm(alarm);
        if (!userService.isRegisterUser(user)) {
            userService.addUser(user);
            userService.existUser(user);
            return new SuccessResponse("添加用户成功");
        }
        return new SuccessResponse("用户已经存在");
    }
}
