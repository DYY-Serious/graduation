package com.zua.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.pojo.User;
import com.zua.service.UserService;
import com.zua.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("login")
    public R loginUser(User user) {
        return R.SUCCESS(userService.getUser(user));
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping("register")
    public R registerUser(User user) {
        return userService.saveUser(user);
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @PostMapping("editUser")
    public R editUser(User user) {
        userService.updateById(user);
        return R.SUCCESS("修改成功!!!");
    }

    @GetMapping("/userList")
    public R getUserList(User user, Integer pageSize, Integer curPage) {
        return R.SUCCESS(userService.getUserList(user,pageSize,curPage));
    }
}
