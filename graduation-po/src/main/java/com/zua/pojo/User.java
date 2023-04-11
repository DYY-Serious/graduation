package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    @TableField(fill = FieldFill.INSERT)
    private String id;
    @TableField(exist = false)
    private String roleId;
    private String account;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String sex;
    private Integer isAdmin = 0;
    private Integer isDeleted = 0;
    //账号状态,0-未激活，1-可用，2-停用
    private Integer accountStatus = 0;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
}
