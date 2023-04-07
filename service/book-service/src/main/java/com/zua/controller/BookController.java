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
@CrossOrigin
public class BookController {

    @Autowired
    private BookSerivce bookSerivce;

    /**
     * 获取全部图书
     * @return
     */
    @GetMapping("list")
    public R getBooks(BookVo bookVo,Integer pageSize,Integer curPage) {
        IPage<Book> page = bookSerivce.getBookList(bookVo, pageSize, curPage);
        return R.SUCCESS(page);
    }

    /**
     * 获取全部图书
     * @return
     */
    @GetMapping("store")
    public R getBookByStore(BookVo bookVo,Integer pageSize,Integer curPage) {
        IPage<Book> page = bookSerivce.getBookListByStore(bookVo, pageSize, curPage);
        return R.SUCCESS(page);
    }

    /**
     * 新增图书
     * @param bookVo
     * @return
     */
    @PostMapping("saveBook")
    public R saveBook(@RequestBody BookVo bookVo) {
        boolean flag = bookSerivce.save((Book) bookVo);
        return flag ? R.SUCCESS("添加成功") : R.ERRORMSG("添加失败");
    }

    /**
     * 删除图书
     * @param id
     * @return
     */
    @PutMapping("deleteBook/{id}")
    public R deleteBook(@PathVariable("id") String id) {
        return bookSerivce.deleteBook(id);
    }

    /**
     * 编辑图书
     * @param bookVo
     * @return
     */
    @PostMapping("editBook")
    public R editBook(@RequestBody BookVo bookVo) {
        bookSerivce.updateById((Book) bookVo);
        return R.SUCCESS();
    }
}
