package com.zua.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.pojo.Book_Borrow;
import com.zua.vo.BookBorrowVo;

public interface BookBorrowService extends IService<Book_Borrow> {
    IPage getBorrowList(BookBorrowVo bookBorrowVo, Integer pageSize, Integer curPage);

    void saveBorrow(Book_Borrow book_borrow);
}
