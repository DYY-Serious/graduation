package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField(fill = FieldFill.INSERT)
    private String id;
    private String userId;
    private String roleId;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
}
