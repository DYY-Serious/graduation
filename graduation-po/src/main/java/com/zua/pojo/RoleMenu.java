package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField(fill = FieldFill.INSERT)
    private String id;
    private String roleId;
    private String menuId;
    @TableField(exist = false,fill = FieldFill.INSERT)
    private String createTime;
    @TableField(exist = false,fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
}
