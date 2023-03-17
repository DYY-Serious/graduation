package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("limits")
public class Limit {
    private String id;
    @TableField("name")
    private String name; //权限名称
}
