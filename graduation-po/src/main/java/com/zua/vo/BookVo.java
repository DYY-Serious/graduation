package com.zua.vo;

import com.zua.pojo.Book;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BookVo extends Book {
    private String categoryName;
}
