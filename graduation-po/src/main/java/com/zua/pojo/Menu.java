package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@TableName("menu")
public class Menu implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @TableField(fill = FieldFill.INSERT)
    private String id;
    private String parentId;
    private String title;
    private String code;
    private String name;
    private String path;
    private String url;
    private String type;
    private String icon;
    private String parentName;
    private Integer orderNum;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
    @TableField(exist = false)
    private List<Menu> children = new ArrayList<Menu>();
}
