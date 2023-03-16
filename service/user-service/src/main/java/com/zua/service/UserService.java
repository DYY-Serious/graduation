package com.zua.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.pojo.User;

public interface UserService extends IService<User> {
    void saveUser(User user);

    User getUser(User user);
}
