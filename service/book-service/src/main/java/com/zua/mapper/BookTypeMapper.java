package com.zua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zua.pojo.BookType;
import com.zua.vo.CategoryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookTypeMapper extends BaseMapper<BookType> {
    //分类统计
    List<CategoryVo> getCategoryVo();
}
