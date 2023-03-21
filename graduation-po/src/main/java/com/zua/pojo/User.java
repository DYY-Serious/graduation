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
    private String studentId;
    private String username;
    @TableField(select = false)
    private String password;
    private String phone;
    private String email;
    private String sex;
    private Integer isAdmin;
    private Integer isDeleted;
    //账号状态,0-未激活，1-可用，2-停用
    private Integer accountStatus;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
}
