package com.zua.mapper;

import com.zua.pojo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    public List<Role> getRoles(String userid);
}
