package com.zua.service;

import com.zua.pojo.SeatInfo;
import com.zua.util.R;
import com.zua.vo.SeatInfoVo;

public interface SeatService {

    /**
     * 获取可预约信息
     * @return
     */
    R getSeatList();

    /**
     * 预约
     * @param seatInfo
     * @return
     */
    R potSeat(SeatInfo seatInfo);

    /**
     * 获取座位的剩余情况
     */
    R getStore();

    /**
     * 根据登入账号查询预约列表
     * @param userType
     * @param id
     * @return
     */
    R getListByType(String userType, String id, SeatInfoVo seatInfoVo);

    /**
     * 离开座位
     * @param id
     * @return
     */
    R leavSeat(String id);

    /**
     * 定时任务，重置座位预约状态
     */
    void resetSeat();
}
