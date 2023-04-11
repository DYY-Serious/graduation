package com.zua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zua.pojo.Seat;
import com.zua.vo.SeatStoreVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeatMapper extends BaseMapper<Seat> {
    List<SeatStoreVo> getByQy();
}
