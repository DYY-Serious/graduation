package com.zua.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.SeatInfoMapper;
import com.zua.pojo.SeatInfo;
import com.zua.service.SeatInfoService;
import com.zua.vo.SeatInfoVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatInfoServiceImpl extends ServiceImpl<SeatInfoMapper, SeatInfo> implements SeatInfoService {
    @Override
    public IPage<SeatInfoVo> getListByType(IPage<SeatInfoVo> page, String id,SeatInfoVo seatInfoVo) {
        return this.baseMapper.getListByType(page,id,seatInfoVo);
    }

    @Override
    public IPage<SeatInfoVo> getListAll(IPage<SeatInfoVo> page,SeatInfoVo seatInfoVo) {
        return this.baseMapper.getListAll(page,seatInfoVo);
    }
}
