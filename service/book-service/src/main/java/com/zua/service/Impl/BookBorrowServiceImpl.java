package com.zua.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.BookBorrowMapper;
import com.zua.pojo.*;
import com.zua.service.BookBorrowService;
import com.zua.service.BookSerivce;
import com.zua.service.PotDayService;
import com.zua.util.R;
import com.zua.vo.BookBorrowVo;
import com.zua.vo.BorrowInfoVo;
import com.zua.vo.ReturnBookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class BookBorrowServiceImpl extends ServiceImpl<BookBorrowMapper, Book_Borrow> implements BookBorrowService {

    @Autowired
    private BookSerivce bookSerivce;

    @Autowired
    private PotDayService potDayService;

    private Lock lock = new ReentrantLock();

    @Override
    @Transactional
    public R saveBorrow(BookBorrowVo bookBorrowVo) {
        //借阅前先判断是否借阅过图书
        LambdaQueryWrapper<Book_Borrow> queryWrapper = new LambdaQueryWrapper<Book_Borrow>();
        queryWrapper.in(Book_Borrow::getBookId,bookBorrowVo.getBookIds());
        queryWrapper.eq(Book_Borrow::getUserId,bookBorrowVo.getUserId());
        queryWrapper.in(Book_Borrow::getBorrowStatus,"1","3");
        List<Book_Borrow> book_borrows = this.baseMapper.selectList(queryWrapper);
        if (book_borrows != null && book_borrows.size() >0) {
            return R.ERRORMSG("已借阅图书,请先归还");
        }
        lock.lock();
        try {
            List<String> bookIds = bookBorrowVo.getBookIds();
            for (String bookId : bookIds) {
                int res = bookSerivce.subStore(bookId);
                if (res > 0) {
                    Book_Borrow book_borrow = new Book_Borrow();
                    book_borrow.setBookId(bookId);
                    book_borrow.setStudentId(bookBorrowVo.getStudentId());
                    book_borrow.setUserId(bookBorrowVo.getUserId());
                    book_borrow.setBorrowTime(bookBorrowVo.getBorrowTime());
                    book_borrow.setReturnTime(bookBorrowVo.getReturnTime());
                    book_borrow.setApplyStatus("1");
                    book_borrow.setBorrowStatus("1");
                    this.baseMapper.insert(book_borrow);
                }
            }
            return R.SUCCESS("借阅成功");
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            return R.ERRORMSG("系统异常");
        }finally {
            lock.unlock();
        }
    }

    /**
     * 获取借阅列表
     * @param returnBookVo
     * @return
     */
    @Override
    public IPage<ReturnBook> getBorrowList(ReturnBookVo returnBookVo) {
        //构造分页对象
        Page<ReturnBook> page = new Page<>(returnBookVo.getCurPage(),returnBookVo.getPageSize());
        IPage<ReturnBook> borrowList = this.baseMapper.getBorrowList(page, returnBookVo);
        return borrowList;
    }

    /**
     * 还书
     * @param list
     * @return
     */
    @Override
    @Transactional
    public R returnBook(List<ReturnBookVo> list) {
        try {
            for (ReturnBookVo returnBookVo : list) {
                int res = bookSerivce.addStore(returnBookVo.getBookId());
                if (res > 0) {
                    Book_Borrow book_borrow = new Book_Borrow();
                    book_borrow.setBorrowStatus("2");
                    book_borrow.setId(returnBookVo.getBorrowId());
                    this.baseMapper.updateById(book_borrow);
                }
            }
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return R.SUCCESS("归还成功");
    }

    /**
     * 异常还书
     * @param returnBookVo
     * @return
     */
    @Override
    @Transactional
    public R exceptionBook(ReturnBookVo returnBookVo) {
        try{
            // 0: 异常、破损 1：丢失 ：不能还库存
            String type = returnBookVo.getType();
            if(type.equals("0")){
                //加库存
                int res = bookSerivce.addStore(returnBookVo.getBookId());
                if(res > 0){
                    //变更借书状态
                    Book_Borrow borrowBook = new Book_Borrow();
                    borrowBook.setId(returnBookVo.getBorrowId());
                    borrowBook.setBorrowStatus("2"); //已还
                    borrowBook.setReturnStatus("2"); //异常还书
                    borrowBook.setExceptionText(returnBookVo.getExceptionText());
                    this.baseMapper.updateById(borrowBook);
                }
            }else{ //丢失
                //变更借书状态
                Book_Borrow borrowBook = new Book_Borrow();
                borrowBook.setId(returnBookVo.getBorrowId());
                borrowBook.setBorrowStatus("2"); //已还
                borrowBook.setReturnStatus("3"); //丢失
                borrowBook.setExceptionText(returnBookVo.getExceptionText());
                this.baseMapper.updateById(borrowBook);
            }
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
        return R.SUCCESS("还书成功");
    }

    /**
     * 获取借阅信息列表
     * @param borrowInfoVo
     * @return
     */
    @Override
    public R getBorrowInfoList(BorrowInfoVo borrowInfoVo) {
        Page<BorrowInfo> page = new Page<BorrowInfo>(borrowInfoVo.getCurPage(),borrowInfoVo.getPageSize());
        IPage<BorrowInfo> borrowInfoList = this.baseMapper.getBorrowInfoList(page, borrowInfoVo);
        return R.SUCCESS("查询成功",borrowInfoList);
    }

    /**
     * 获取学生借阅列表
     * @param borrowInfoVo
     * @return
     */
    @Override
    public R getStudentBorrowInfoList(BorrowInfoVo borrowInfoVo) {
        Page<BorrowInfo> page = new Page<BorrowInfo>(borrowInfoVo.getCurPage(),borrowInfoVo.getPageSize());
        IPage<BorrowInfo> borrowInfoList = this.baseMapper.getStudentBorrowInfoList(page, borrowInfoVo);
        return R.SUCCESS("查询成功",borrowInfoList);
    }

    /**
     * 获取用户借阅列表
     * @param returnBookVo
     * @return
     */
    @Override
    public IPage<ReturnBook> getAllBorrowList(ReturnBookVo returnBookVo) {
        //构造分页对象
        Page<ReturnBook> page = new Page<>(returnBookVo.getCurPage(),returnBookVo.getPageSize());
        IPage<ReturnBook> borrowList = this.baseMapper.getAllBorrowList(page, returnBookVo);
        return borrowList;
    }

    /**
     * 续租
     * @param potDay
     * @return
     */
    @Override
    @Transactional
    public R addTime(PotDay potDay) {
        Book_Borrow book_borrow = this.getById(potDay.getBorrowId());
//        //如果图书已经归还，续租将图书库存减1
//        if (byId != null && byId.getBorrowStatus().equals("2")) {
//            Book book = bookSerivce.getById(byId.getBookId());
//            book.setBookStore(book.getBookStore() - 1);
//            bookSerivce.updateById(book);
//        }
        book_borrow.setBorrowStatus("1");
        book_borrow.setApplyStatus("0");
        this.updateById(book_borrow);
        potDayService.save(potDay);
        return R.SUCCESS("续期成功,等待审核");
    }

    /**
     * 审核图书
     * @return
     */
    @Override
    @Transactional
    public R updateReturnTime(Book_Borrow book_borrow) {
        book_borrow = this.getById(book_borrow.getId());
        //如果图书已经归还，续租将图书库存减1
        if (book_borrow != null && book_borrow.getBorrowStatus().equals("2")) {
            Book book = bookSerivce.getById(book_borrow.getBookId());
            book.setBookStore(book.getBookStore() - 1);
            bookSerivce.updateById(book);
        }
        //查询续期信息
        LambdaQueryWrapper<PotDay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PotDay::getBorrowId,book_borrow.getId());
        PotDay one = potDayService.getOne(queryWrapper);
        //更新归还日期
        book_borrow.setReturnTime(one.getReturnTime());
        book_borrow.setApplyStatus("1");
        this.updateById(book_borrow);
        return R.SUCCESS("审核成功");
    }
}
