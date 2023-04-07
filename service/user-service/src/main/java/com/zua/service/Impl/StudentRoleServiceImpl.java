package com.zua.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.StudentRoleMapper;
import com.zua.pojo.StudentRole;
import com.zua.service.StudentRoleService;
import org.springframework.stereotype.Service;

@Service
public class StudentRoleServiceImpl extends ServiceImpl<StudentRoleMapper, StudentRole> implements StudentRoleService {
}
