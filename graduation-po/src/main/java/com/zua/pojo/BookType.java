package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@TableName("booktype")
public class BookType implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @TableField(fill = FieldFill.INSERT)
    private String id;
    private String categoryName;
    private Integer orderNum;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
}
