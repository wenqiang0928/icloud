package com.tckj.icloud.controller;

import com.tckj.icloud.pojo.User;
import com.tckj.icloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String login() {
        return "home";
    }

    @RequestMapping("confirm")
    public String loginConfirm(User user) {
        Boolean isExist = userService.existUser(user);
        if (isExist) {
            return "index";
        } else {
            return "login";
        }
    }
}
