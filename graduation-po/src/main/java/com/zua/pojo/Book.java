package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("book")
public class Book implements Serializable {
    private static final long serialVersionUID = -8668034013803393986L;
    @TableId
    @TableField(fill = FieldFill.INSERT)
    private String id;
    private String categoryId;
    private String bookName;
    private String bookCode;
    private String bookPlaceNum;
    private String bookAuthor;
    private String bookProduct;
    private Double bookPrice;
    private Integer bookStore;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
}
