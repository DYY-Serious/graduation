package com.zua.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.pojo.SeatInfo;
import com.zua.vo.SeatInfoVo;

import java.util.List;

public interface SeatInfoService extends IService<SeatInfo> {
    IPage<SeatInfoVo> getListByType(IPage<SeatInfoVo> page, String id,SeatInfoVo seatInfoVo);
    IPage<SeatInfoVo> getListAll(IPage<SeatInfoVo> page,SeatInfoVo seatInfoVo);
}
