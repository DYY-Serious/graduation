package com.zua.vo;

import com.zua.pojo.BorrowInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BorrowInfoVo extends BorrowInfo {
    private Integer curPage;
    private Integer pageSize;
    private String studentId;
    private String studentName;
    private String bookName;
    //0: 待审核 1： 已审核 2：拒绝
    private String applyStatus;
    //1:在借中 2：已还 3：拒绝
    private String borrowStatus;
    //1: 正常还书 2：异常还书
    private String returnStatus;
    // 1:到期 0：未到期
    private String timeStatus;
}
