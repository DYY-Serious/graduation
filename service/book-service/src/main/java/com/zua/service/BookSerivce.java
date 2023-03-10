package com.zua.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.pojo.Book;

public interface BookSerivce extends IService<Book> {
    void saveBook(Book book);
}
