package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;


@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @TableField(fill = FieldFill.INSERT)
    private String id;
    private String roleName;
    private String roleType;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
}
