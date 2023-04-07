package com.zua.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zua.pojo.Book_Borrow;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookBorrowVo extends Book_Borrow {
    //学生id
    @TableField(exist = false)
    private String studentId;
    //图书id
    private List<String> bookIds;
}
