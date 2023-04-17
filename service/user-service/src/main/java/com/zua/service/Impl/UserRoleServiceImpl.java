package com.zua.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.UserRoleMapper;
import com.zua.pojo.UserRole;
import com.zua.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
