package com.zua.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.BookMapper;
import com.zua.pojo.Book;
import com.zua.pojo.Book_Borrow;
import com.zua.service.BookBorrowService;
import com.zua.service.BookSerivce;
import com.zua.utils.R;
import com.zua.vo.BookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookSerivce {

    @Autowired
    private BookBorrowService bookBorrowService;

    /**
     * author 乔培洋
     * @param id
     */
    @Override
    public R deleteBook(String id) {
        //删除图书之前先判断图书是否被借阅，如果被借阅则不能被删除
        LambdaQueryWrapper<Book_Borrow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book_Borrow::getBookId, id);
        queryWrapper.eq(Book_Borrow::getBorrowStatus, "1");
        List<Book_Borrow> book_borrowList = bookBorrowService.list(queryWrapper);
        if (book_borrowList != null && book_borrowList.size() > 0) {
            return R.ERRORMSG("此书正在被借阅,暂时不能删除");
        }
        this.removeById(id);
        return R.SUCCESS("删除成功");
    }

    /**
     * @author 乔培洋
     * @param bookVo
     * @param pageSize
     * @param curPage
     * @return
     */
    @Override
    public IPage<Book> getBookList(BookVo bookVo, Integer pageSize, Integer curPage) {
        IPage<Book> page = new Page<Book>(curPage,pageSize);
        return this.baseMapper.getBookList(page, bookVo);
    }

    /**
     * 查询库存数大于0的图书
     * @author 乔培洋
     * @param bookVo
     * @param pageSize
     * @param curPage
     * @return
     */
    @Override
    public IPage<Book> getBookListByStore(BookVo bookVo, Integer pageSize, Integer curPage) {
        IPage<Book> page = new Page<Book>(curPage,pageSize);
        return this.baseMapper.getBookListByStore(page, bookVo);
    }

    /**
     * 减少库存
     * @param bookId
     * @return
     */
    @Override
    public int subStore(String bookId) {
        return this.baseMapper.subStore(bookId);
    }

    /**
     * 还书更新库存
     * @param bookId
     * @return
     */
    @Override
    public int addStore(String bookId) {
        return this.baseMapper.addStore(bookId);
    }

    /**
     * 获取热门图书
     * @return
     */
    @Override
    public List<BookVo> getHotBook() {
        return this.baseMapper.getHotBook();
    }
}
