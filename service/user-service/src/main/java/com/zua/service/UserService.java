package com.zua.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.pojo.User;
import com.zua.util.R;

public interface UserService extends IService<User> {
    R saveUser(User user);

    IPage<User> getUserList(User user, Integer pageSize, Integer curPage);

    R editUser(User user);

    User loadById(Object id);
}
