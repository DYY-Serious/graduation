package com.zua.vo;

import lombok.Data;

@Data
public class ReturnBookVo {
    private Integer curPage;
    private Integer pageSize;
    private String studentId;
    private String userId;
    private String studentName;
    private String borrowStatus;
    private String borrowId;
    private String bookId;
    private String type;
    private String exceptionText;
}
