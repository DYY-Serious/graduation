package com.zua.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.pojo.User;
import com.zua.utils.R;

import java.util.List;

public interface UserService extends IService<User> {
    R saveUser(User user);

    User getUser(User user);

    IPage getUserList(User user, Integer pageSize, Integer curPage);
}
