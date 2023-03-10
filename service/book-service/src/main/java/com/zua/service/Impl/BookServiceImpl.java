package com.zua.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.BookMapper;
import com.zua.pojo.Book;
import com.zua.service.BookSerivce;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookSerivce {
    @Override
    public void saveBook(Book book) {
        String id = UUID.randomUUID().toString();
        id = id.replace("-","");
        book.setId(id);
        book.setBookTypeId(id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        book.setPublisherTime(format.format(new Date()));
        this.save(book);
    }
}
