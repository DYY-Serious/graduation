package com.zua.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.pojo.Book;
import com.zua.utils.R;
import com.zua.vo.BookVo;

import java.util.List;

public interface BookSerivce extends IService<Book> {
    R deleteBook(Book book);

    IPage getBookList(BookVo bookVo, Integer pageSize, Integer curPage);

    void saveBook(Book book);
}
