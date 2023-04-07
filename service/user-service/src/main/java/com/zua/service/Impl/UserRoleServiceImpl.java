package com.zua.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.UserRoleMapper;
import com.zua.pojo.UserRole;
import com.zua.service.UserRoleService;
import com.zua.utils.R;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
