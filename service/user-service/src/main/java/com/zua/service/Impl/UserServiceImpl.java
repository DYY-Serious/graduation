package com.zua.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.UserMapper;
import com.zua.pojo.User;
import com.zua.service.UserService;
import com.zua.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void saveUser(User user) {
        String id = UUID.randomUUID().toString().replace("-", "");
        user.setId(id);
        String password = MD5.MD5Encode(user.getPassword(),"utf-8");
        user.setPassword(password);
        this.save(user);
    }

    @Override
    public User getUser(User user) {
        User user1 = userMapper.getUser(user);
        return user1;
    }

}
