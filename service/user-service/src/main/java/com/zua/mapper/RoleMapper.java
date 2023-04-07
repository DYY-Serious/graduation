package com.zua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zua.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
