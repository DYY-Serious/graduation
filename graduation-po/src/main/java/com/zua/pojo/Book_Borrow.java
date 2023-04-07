package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("book_borrow")
public class Book_Borrow implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @TableField(fill = FieldFill.INSERT)
    private String id;
    private String bookId;
    private String userId;
    private Date borrowTime;
    private Date returnTime;
    private String applyStatus;
    private String borrowStatus;
    private String returnStatus;
    private String exceptionText;
    private String applyText;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
}
