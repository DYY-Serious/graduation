package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("seatinfo")
public class SeatInfo {
    private String id;
    private String userId;
    private String seatId;
    private String status = "1";
}
