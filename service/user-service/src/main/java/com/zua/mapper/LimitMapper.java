package com.zua.mapper;

import com.zua.pojo.Limit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LimitMapper {
    public List<Limit> getLimits(String roleID);
}
