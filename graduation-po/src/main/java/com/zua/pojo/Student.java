package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @TableField(fill = FieldFill.INSERT)
    private String id;
    private String studentId;
    private String studentName;
    private String idCard;
    private String sex;
    private String phone;
    private String password;
    private String accountStatus = "1"; //审核状态 0：未审核   1：已审核
    private String studentStatus = "1"; //用户状态 0：停用   1：启用
    private String classId;
    private String className;
    private String image;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
}
