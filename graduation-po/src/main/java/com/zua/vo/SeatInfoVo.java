package com.zua.vo;

import com.zua.pojo.SeatInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class SeatInfoVo {

    private String id;
    private String name;
    private String parentName;
    private String studentName;
    private String studentId;
    private String status;
    private String userId;
    private Integer curPage;
    private Integer pageSize;
}
