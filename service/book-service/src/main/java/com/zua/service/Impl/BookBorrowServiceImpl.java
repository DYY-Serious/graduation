package com.zua.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.BookBorrowMapper;
import com.zua.pojo.Book_Borrow;
import com.zua.service.BookBorrowService;
import com.zua.vo.BookBorrowVo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookBorrowServiceImpl extends ServiceImpl<BookBorrowMapper, Book_Borrow> implements BookBorrowService {

    @Override
    public IPage getBorrowList(BookBorrowVo bookBorrowVo, Integer pageSize, Integer curPage) {
        IPage page = new Page(curPage,pageSize);
        LambdaQueryWrapper<Book_Borrow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(bookBorrowVo.getBorrowerName() != null, Book_Borrow::getBorrowerName, bookBorrowVo.getBorrowerName());
        return this.page(page);
    }

    @Override
    public void saveBorrow(Book_Borrow book_borrow) {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        book_borrow.setId(id);
    }
}
