package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Seat {

    private String id;
    private String parentId;
    private String name;
    private String status;
    private String orderNum;
    @TableField(exist = false)
    private String updateTime;
    @TableField(exist = false)
    private String createTime;
    @TableField(exist = false)
    private List<Seat> children = new ArrayList<Seat>();
}
