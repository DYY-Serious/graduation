package com.zua.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.pojo.Book_Borrow;
import com.zua.pojo.PotDay;
import com.zua.pojo.ReturnBook;
import com.zua.util.R;
import com.zua.vo.BookBorrowVo;
import com.zua.vo.BorrowInfoVo;
import com.zua.vo.ReturnBookVo;

import java.util.List;

public interface BookBorrowService extends IService<Book_Borrow> {
    /**
     * 借书
     * @param bookBorrowVo
     * @return
     */
    R saveBorrow(BookBorrowVo bookBorrowVo);

    /**
     * 还书列表
     * @param returnBookVo
     * @return
     */
    IPage<ReturnBook> getBorrowList(ReturnBookVo returnBookVo);

    /**
     * 还书
     * @param list
     * @return
     */
    R returnBook(List<ReturnBookVo> list);

    /**
     * 异常还书
     * @param returnBookVo
     * @return
     */
    R exceptionBook(ReturnBookVo returnBookVo);

    /**
     * 获取借阅信息列表
     * @param borrowInfoVo
     * @return
     */
    R getBorrowInfoList(BorrowInfoVo borrowInfoVo);

    /**
     * 获取学生借阅列表
     * @param borrowInfoVo
     * @return
     */
    R getStudentBorrowInfoList(BorrowInfoVo borrowInfoVo);

    /**
     * 用户借阅列表
     * @param bookBorrowVo
     * @return
     */
    IPage<ReturnBook> getAllBorrowList(ReturnBookVo bookBorrowVo);

    /**
     * 续租
     * @param book_borrow
     * @return
     */
    R addTime(PotDay PotDay);

    /**
     * 审核图书
     * @return
     */
    R updateReturnTime(Book_Borrow book_borrow);
}
