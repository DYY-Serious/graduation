package com.zua.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.annotation.Auth;
import com.zua.pojo.Role;
import com.zua.pojo.User;
import com.zua.pojo.UserRole;
import com.zua.service.RoleService;
import com.zua.service.UserRoleService;
import com.zua.service.UserService;
import com.zua.util.JwtUtils;
import com.zua.util.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 新增用户
     * @param user
     * @return
     */
    @Auth
    @PostMapping("register")
    public R registerUser(@RequestBody User user, HttpServletRequest request) {
        return userService.saveUser(user);
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @Auth
    @PostMapping("editUser")
    public R editUser(@RequestBody User user,HttpServletRequest request) {
        return userService.editUser(user);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @Auth
    @DeleteMapping("/delUser/{userId}")
    public R delUser(@PathVariable("userId") String id,HttpServletRequest request) {
        boolean flag = userService.removeById(id);
        if (flag) {
            return R.SUCCESS("删除成功");
        }
        return R.ERRORMSG("删除失败!!!");
    }

    /**
     * 获取用户列表
     * @param user
     * @param pageSize
     * @param curPage
     * @return
     */
    @Auth
    @GetMapping("/userList")
    public R getUserList(User user, Integer pageSize, Integer curPage,HttpServletRequest request) {
        IPage<User> userList = userService.getUserList(user, pageSize, curPage);
        //用stream流将查询的user集合的密码全部设置为空，密码不能进行传递
        userList.getRecords().forEach(item -> {
            item.setPassword("");
        });
        return R.SUCCESS("查询成功",userList);
    }

    /**
     * 获取角色列表
     * @return
     */
    @Auth
    @GetMapping("/roleList")
    public R getRoleList(HttpServletRequest request) {
        List<Role> list = roleService.list();
        return R.SUCCESS("查询成功",list);
    }

    /**
     * 获取角色列表
     * @return
     */
    @Auth
    @GetMapping("/getRoleByUserId")
    public R getRoleByUserId(String id,HttpServletRequest request) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>();
        queryWrapper.eq(UserRole::getUserId,id);
        UserRole one = userRoleService.getOne(queryWrapper);
        return R.SUCCESS("查询成功",one);
    }
}
