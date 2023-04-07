package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class Notice implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField(fill = FieldFill.INSERT)
    private String id;
    private String noticeTitle;
    private String noticeContent;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
}
