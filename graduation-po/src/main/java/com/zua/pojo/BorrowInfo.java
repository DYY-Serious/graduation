package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BorrowInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String bookId;
    private String bookName;
    private String bookPlaceNum;
    private String studentName;
    @TableField(exist = false)
    private String studentId;
    private String userId;
    private String className;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date borrowTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date returnTime;
    private String applyStatus;
    private String borrowStatus;
    private String returnStatus;
    private String exceptionText;
    private String applyText;
    private String timeStatus;
}
