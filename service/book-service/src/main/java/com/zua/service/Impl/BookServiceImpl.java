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
     * @param book
     */
    @Override
    public R deleteBook(Book book) {
        //删除图书之前先判断图书是否被借阅，如果被借阅则不能被删除
        LambdaQueryWrapper<Book_Borrow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(book.getId() != null,Book_Borrow::getBookId, book.getId());
        List<Book_Borrow> book_borrowList = bookBorrowService.list(queryWrapper);
        if (book_borrowList != null && book_borrowList.size() > 0) {
            return R.ERRORMSG("此书正在被借阅,暂时不能删除");
        }
        this.removeById(book);
        return R.SUCCESS();
    }

    /**
     * @author 乔培洋
     * @param bookVo
     * @param pageSize
     * @param curPage
     * @return
     */
    @Override
    public IPage getBookList(BookVo bookVo, Integer pageSize, Integer curPage) {
        IPage page = new Page(curPage,pageSize);
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<Book>();
        queryWrapper.like(bookVo.getName() != null, Book::getName, bookVo.getName());
        queryWrapper.like(bookVo.getAuthor() != null, Book::getAuthor, bookVo.getAuthor());
        queryWrapper.like(bookVo.getPublisher() != null, Book::getPublisher, bookVo.getPublisher());
        return this.page(page, queryWrapper);
    }

    /**
     * 保存图书
     * @author 乔培洋
     * @param book
     */
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
