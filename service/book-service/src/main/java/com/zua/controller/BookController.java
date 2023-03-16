package com.zua.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.pojo.Book;
import com.zua.service.BookSerivce;
import com.zua.utils.R;
import com.zua.vo.BookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    private BookSerivce bookSerivce;

    /**
     * 获取全部图书
     * @return
     */
    @GetMapping("get")
    public R getBooks(BookVo bookVo,Integer pageSize,Integer curPage) {
        IPage page = bookSerivce.getBookList(bookVo, pageSize, curPage);
        return R.SUCCESS(page);
    }

    /**
     * 新增图书
     * @param book
     * @return
     */
    @PostMapping("saveBook")
    public R saveBook(Book book) {
        bookSerivce.saveBook(book);
        return R.SUCCESS();
    }

    /**
     * 删除图书
     * @param book
     * @return
     */
    @PutMapping("deleteBook")
    public R deleteBook(Book book) {
        // TODO 先判断图书是否被借阅，如果被借阅则不能被删除
        bookSerivce.removeById(book);
        return R.SUCCESS();
    }

    /**
     * 编辑图书
     * @param book
     * @return
     */
    @PostMapping("editBook")
    public R editBook(Book book) {
        bookSerivce.updateById(book);
        return R.SUCCESS();
    }
}
