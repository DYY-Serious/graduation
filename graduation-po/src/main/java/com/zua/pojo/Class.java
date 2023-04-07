package com.zua.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Class implements Serializable {
    private static final long serialVersionUID = -8668034013803393986L;
    @TableId
    @TableField(fill = FieldFill.INSERT)
    private String id;
    private String classId;
    private String parentId;
    private String title;
    private String parentName;
    @TableField(fill = FieldFill.INSERT)
    private String createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
    @TableField(exist = false)
    private List<Class> children = new ArrayList<Class>();
}
