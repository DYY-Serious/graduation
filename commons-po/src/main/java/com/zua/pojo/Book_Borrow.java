package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("book_borrow")
public class Book_Borrow {
    @TableId("ID")
    private String id;
    @TableField("BOOKID")
    private String bookId;
    @TableField("BORROWERID")
    private String borrowerId;
    @TableField("BORROWERNAME")
    private String borrowerName;
    @TableField("BEGINDATE")
    private String beginDate;
    @TableField("ENDDATE")
    private String endDate;
    @TableField("ENDDATE_REL")
    private String endDate_rel;
}
