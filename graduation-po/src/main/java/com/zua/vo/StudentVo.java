package com.zua.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zua.pojo.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentVo extends Student {
    @TableField(exist = false)
    private String roleId;
}
