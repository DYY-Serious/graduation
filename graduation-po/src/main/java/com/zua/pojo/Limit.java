package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("limits")
public class Limit {
    private String id;
    private String fid;
    private String path;
    @TableField("name")
    private String name; //权限名称
    private List<Limit> children;
}
