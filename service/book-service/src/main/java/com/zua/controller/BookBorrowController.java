package com.zua.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.service.BookBorrowService;
import com.zua.utils.R;
import com.zua.vo.BookBorrowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bookBorrow")
public class BookBorrowController {

    @Autowired
    private BookBorrowService bookBorrowService;

    @GetMapping("borrowList")
    public R getBorrowInfo(BookBorrowVo bookBorrowVo, Integer pageSize, Integer curPage) {
        IPage page = bookBorrowService.getBorrowList(bookBorrowVo,pageSize,curPage);
        return R.SUCCESS(page);
    }
}
