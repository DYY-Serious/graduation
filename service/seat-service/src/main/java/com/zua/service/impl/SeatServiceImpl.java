package com.zua.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.SeatMapper;
import com.zua.pojo.Seat;
import com.zua.pojo.SeatInfo;
import com.zua.service.SeatInfoService;
import com.zua.service.SeatService;
import com.zua.util.MakeTree;
import com.zua.util.R;
import com.zua.vo.SeatInfoVo;
import com.zua.vo.SeatStoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper, Seat> implements SeatService {

    @Autowired
    private SeatInfoService seatInfoService;

    /**
     * 获取可预约信息
     * @return
     */
    @Override
    public R getSeatList() {
        //查询没有被预约的座位
        LambdaQueryWrapper<Seat> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Seat::getStatus,"0").orderByAsc(Seat::getOrderNum);
        List<Seat> list = this.list(queryWrapper);
        Seat seat = new Seat();
        seat.setId(null);
        seat.setParentId("top-seat");
        seat.setName("楼层");
        list.add(seat);
        List<Seat> seats = MakeTree.makeSeatTree(list, "top-seat");
        return R.SUCCESS(seats);
    }

    /**
     * 预约
     * @param seatInfo
     * @return
     */
    @Override
    @Transactional
    public R potSeat(SeatInfo seatInfo) {
        //构造查询条件
        LambdaQueryWrapper<SeatInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SeatInfo::getUserId,seatInfo.getUserId());
        queryWrapper.eq(SeatInfo::getStatus,"1");
        SeatInfo one = seatInfoService.getOne(queryWrapper);
        if (one != null) {
            return R.ERRORMSG("您已预约其他座位,请取消后再预约!");
        }
        seatInfoService.save(seatInfo);
        Seat seat = new Seat();
        seat.setId(seatInfo.getSeatId());
        seat.setStatus("1");
        this.updateById(seat);
        return R.SUCCESS("预约成功");
    }

    /**
     * 获取座位的剩余情况
     */
    @Override
    public R getStore() {
        List<SeatStoreVo> list = new ArrayList<>();
        LambdaQueryWrapper<Seat> queryWrapper = new LambdaQueryWrapper<Seat>();
        list = this.baseMapper.getByQy();
        queryWrapper.eq(Seat::getStatus,"1");
        long count = this.count(queryWrapper);
        list.add(new SeatStoreVo("已被预约", (int) count));
        return R.SUCCESS(list);
    }

    /**
     * 根据登入账号查询预约列表
     * @param userType
     * @param id
     * @return
     */
    @Override
    public R getListByType(String userType, String id,SeatInfoVo seatInfoVo) {
        IPage<SeatInfoVo> page = new Page<>(seatInfoVo.getCurPage(),seatInfoVo.getPageSize());
        if (userType.equals("0")) {
            page = seatInfoService.getListByType(page,id,seatInfoVo);
        }else {
            page = seatInfoService.getListAll(page,seatInfoVo);
        }
        return R.SUCCESS(page);
    }

    /**
     * 离开座位
     * @param id
     * @return
     */
    @Override
    @Transactional
    public R leavSeat(String id) {
        //获取预约信息
        LambdaQueryWrapper<SeatInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SeatInfo::getSeatId,id);
        queryWrapper.eq(SeatInfo::getStatus,"1");
        SeatInfo one = seatInfoService.getOne(queryWrapper);
        one.setStatus("2");
        //修改离开座位
        seatInfoService.updateById(one);
        //获取座位信息
        Seat seat = this.getById(id);
        //修改座位为未被预约
        seat.setStatus("0");
        this.updateById(seat);
        return R.SUCCESS("离开成功");
    }


}
