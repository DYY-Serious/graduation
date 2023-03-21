package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("book")
public class Book implements Serializable {
    private static final long serialVersionUID = -8668034013803393986L;
    @TableId
    @TableField(fill = FieldFill.INSERT)
    private String id;
    private String name;
    @TableField("booktypeid")
    private String bookTypeId;
    private String author;
    private String introduction;
    private String publisher;
    @TableField("publishertime")
    private String publisherTime;
    private Integer stock;
    private Integer status;
    private Integer total;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
