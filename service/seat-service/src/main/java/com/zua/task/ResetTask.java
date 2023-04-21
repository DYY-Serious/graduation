package com.zua.task;

import com.zua.service.SeatInfoService;
import com.zua.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ResetTask {

    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatInfoService seatInfoService;

    @Scheduled(cron = "0 0 24 * * ?")
    @Transactional
    public void resetSeat() {
        seatService.resetSeat();
        seatInfoService.resetInfo();
        System.out.println("定时器被执行了");
    }
}
