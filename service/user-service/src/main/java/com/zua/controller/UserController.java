package com.zua.controller;

import com.zua.pojo.User;
import com.zua.service.UserService;
import com.zua.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("login")
    public R loginUser(User user) {
        return R.SUCCESS(userService.getUser(user));
    }

    @PostMapping("register")
    public String registerUser(User user) {
        userService.saveUser(user);
        return null;
    }
}
