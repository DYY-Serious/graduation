package com.zua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.pojo.SeatInfo;
import com.zua.vo.SeatInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeatInfoMapper extends BaseMapper<SeatInfo> {
    IPage<SeatInfoVo> getListByType(IPage<SeatInfoVo> page, String id,SeatInfoVo seatInfoVo);

    IPage<SeatInfoVo> getListAll(IPage<SeatInfoVo> page,SeatInfoVo seatInfoVo);
}
