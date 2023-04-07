package com.zua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zua.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    //根据用户id查询权限
    List<Menu> getMenuByUserId(@Param("userId") String userId);
    //根据角色id查询权限
    List<Menu> getMenuByRoleId(@Param("roleId") String roleId);
    //根据学生id查询权限
    List<Menu> getReaderMenuByUserId(@Param("studentId") String studentId);
}
