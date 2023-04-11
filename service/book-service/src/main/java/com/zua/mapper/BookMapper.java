package com.zua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.pojo.Book;
import com.zua.vo.BookVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
    IPage<Book> getBookList(IPage<Book> page,Book book);

    IPage<Book> getBookListByStore(IPage<Book> page,Book book);

    int subStore(String bookId);

    int addStore(String bookId);

    List<BookVo> getHotBook();
}
