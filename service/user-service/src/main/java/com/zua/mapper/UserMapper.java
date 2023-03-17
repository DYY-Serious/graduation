package com.zua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zua.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    public User loginUser(User user);
}
