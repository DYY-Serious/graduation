package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("book")
public class Book implements Serializable {
    private static final long serialVersionUID = -8668034013803393986L;
    @TableField("id")
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
}
