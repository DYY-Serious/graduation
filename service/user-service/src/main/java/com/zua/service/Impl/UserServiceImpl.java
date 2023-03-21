package com.zua.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.MenuMapper;
import com.zua.mapper.RoleMapper;
import com.zua.mapper.UserMapper;
import com.zua.pojo.Limit;
import com.zua.pojo.Role;
import com.zua.pojo.User;
import com.zua.service.UserService;
import com.zua.utils.MD5;
import com.zua.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 新增用户
     * @param user
     */
    @Override
    public R saveUser(User user) {
        //先判断学号是否重复
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getStudentId,user.getStudentId());
        User one = this.getOne(queryWrapper);
        if (one != null) {
            return R.ERRORMSG("学号重复！！！");
        }
        String password = MD5.MD5Encode(user.getPassword(),"utf-8");
        user.setPassword(password);
        this.save(user);
        return R.SUCCESS("注册成功");
    }

    /**
     * 登入账号，查询用户对应的角色和权限
     * @param user
     * @return
     */
    @Override
    public User getUser(User user) {
        user.setPassword(MD5.MD5Encode(user.getPassword(),"UTF-8"));
        user = userMapper.loginUser(user);
        List<Role> roles = roleMapper.getRoles(user.getId());
        for (Role role : roles) {
            String roleId = role.getId();
            List<Limit> limits = menuMapper.getLimits(roleId);
            role.setLimits(limits);
        }
        return user;
    }

    /**
     * 获取用户列表
     * @param user
     * @param pageSize
     * @param curPage
     * @return
     */
    @Override
    public IPage getUserList(User user, Integer pageSize, Integer curPage) {
        IPage<User> page = new Page<User>(curPage,pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.like(user.getUsername() != null,User::getUsername,user.getUsername());
        queryWrapper.like(user.getPhone() != null,User::getPhone,user.getPhone());
        page = this.page(page,queryWrapper);
        return page;
    }

}
