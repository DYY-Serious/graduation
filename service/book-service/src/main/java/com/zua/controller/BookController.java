package com.zua.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.annotation.Auth;
import com.zua.pojo.Book;
import com.zua.service.BookSerivce;
import com.zua.util.JwtUtils;
import com.zua.util.R;
import com.zua.vo.BookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("books")
@CrossOrigin
public class BookController {

    @Autowired
    private BookSerivce bookSerivce;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 获取全部图书
     * @return
     */
    @Auth
    @GetMapping("list")
    public R getBooks(BookVo bookVo, Integer pageSize, Integer curPage) {
        IPage<Book> page = bookSerivce.getBookList(bookVo, pageSize, curPage);
        return R.SUCCESS(page);
    }

    /**
     * 获取全部图书
     * @return
     */
    @Auth
    @GetMapping("store")
    public R getBookByStore(BookVo bookVo,Integer pageSize,Integer curPage, HttpServletRequest request) {
        IPage<Book> page = bookSerivce.getBookListByStore(bookVo, pageSize, curPage);
        return R.SUCCESS(page);
    }

    /**
     * 新增图书
     * @param bookVo
     * @return
     */
    @Auth
    @PostMapping("saveBook")
    public R saveBook(@RequestBody BookVo bookVo, HttpServletRequest request) {
        boolean flag = bookSerivce.save((Book) bookVo);
        return flag ? R.SUCCESS("添加成功") : R.ERRORMSG("添加失败");
    }

    /**
     * 删除图书
     * @param id
     * @return
     */
    @Auth
    @DeleteMapping("deleteBook/{id}")
    public R deleteBook(@PathVariable("id") String id, HttpServletRequest request) {
        return bookSerivce.deleteBook(id);
    }

    /**
     * 编辑图书
     * @param bookVo
     * @return
     */
    @Auth
    @PostMapping("editBook")
    public R editBook(@RequestBody BookVo bookVo, HttpServletRequest request) {
        bookSerivce.updateById((Book) bookVo);
        return R.SUCCESS();
    }

    /**
     * 热门图书
     * @return
     */
    @Auth
    @GetMapping("/getHotBook")
    public R getHotBook(){
        List<BookVo> hotBook = bookSerivce.getHotBook();
        hotBook.removeIf(bookVo -> bookVo.getValue() == 0);
        return R.SUCCESS("查询成功",hotBook);
    }
}
