package com.zua.controller;

import com.zua.annotation.Auth;
import com.zua.pojo.SeatInfo;
import com.zua.service.SeatService;
import com.zua.utils.R;
import com.zua.vo.SeatInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("seat")
@CrossOrigin
public class SeatController {

    @Autowired
    private SeatService seatService;

    /**
     * 获取图书馆座位信息
     * @return
     */
    @Auth
    @GetMapping("list")
    public R getSeatList() {
        return seatService.getSeatList();
    }

    /**
     * 预约座位
     * @return
     */
    @Auth
    @PostMapping("pot")
    public R potSeat(@RequestBody SeatInfo seatInfo) {
        return seatService.potSeat(seatInfo);
    }

    /**
     * 获取座位的剩余情况
     */
    @Auth
    @GetMapping("store")
    public R getStore() {
        return seatService.getStore();
    }

    /**
     * 根据登入账号查询预约列表
     * @param userType
     * @param userId
     * @return
     */
    @Auth
    @GetMapping("getListType")
    public R getListByType(String userType, String userId, SeatInfoVo seatInfoVo) {
        return seatService.getListByType(userType,userId,seatInfoVo);
    }

    /**
     * 离开座位
     * @param id
     * @return
     */
    @Auth
    @GetMapping("leavSeat")
    public R getListByType(String id) {
        return seatService.leavSeat(id);
    }
}
