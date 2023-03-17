package com.zua.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.LimitMapper;
import com.zua.mapper.RoleMapper;
import com.zua.mapper.UserMapper;
import com.zua.pojo.Limit;
import com.zua.pojo.Role;
import com.zua.pojo.User;
import com.zua.service.UserService;
import com.zua.utils.MD5;
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
    private LimitMapper limitMapper;

    @Override
    public void saveUser(User user) {
        String id = UUID.randomUUID().toString().replace("-", "");
        user.setId(id);
        String password = MD5.MD5Encode(user.getPassword(),"utf-8");
        user.setPassword(password);
        this.save(user);
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
            List<Limit> limits = limitMapper.getLimits(roleId);
            role.setLimits(limits);
        }
        user.setRoles(roles);
        return user;
    }

}
