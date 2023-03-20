package com.zua.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.pojo.Book_Borrow;
import com.zua.service.BookBorrowService;
import com.zua.utils.R;
import com.zua.vo.BookBorrowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bookBorrow")
public class BookBorrowController {

    @Autowired
    private BookBorrowService bookBorrowService;

    /**
     * 图书借阅信息
     * @param bookBorrowVo
     * @param pageSize
     * @param curPage
     * @return
     */
    @GetMapping("borrowList")
    public R getBorrowInfo(BookBorrowVo bookBorrowVo, Integer pageSize, Integer curPage) {
        IPage page = bookBorrowService.getBorrowList(bookBorrowVo,pageSize,curPage);
        return R.SUCCESS(page);
    }

    /**
     * 新增图书借阅
     * @param book_borrow
     * @return
     */
    @PostMapping("saveBorrow")
    public R saveBorrow(Book_Borrow book_borrow) {
        bookBorrowService.saveBorrow(book_borrow);
        return R.SUCCESS("数据保存成功");
    }

    /**
     * 编辑借阅信息
     * @param book_borrow
     * @return
     */
    @PostMapping("editBorrow")
    public R editBorrow(Book_Borrow book_borrow) {
        bookBorrowService.updateById(book_borrow);
        return R.SUCCESS("数据修改成功");
    }

    /**
     * 删除借阅记录
     * @param book_borrow
     * @return
     */
    public R deleteBorrow(Book_Borrow book_borrow) {
        bookBorrowService.removeById(book_borrow);
        return R.SUCCESS("删除成功");
    }
}
