package com.zua.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zua.pojo.Book_Borrow;
import com.zua.pojo.BorrowInfo;
import com.zua.pojo.ReturnBook;
import com.zua.vo.BorrowInfoVo;
import com.zua.vo.ReturnBookVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookBorrowMapper extends BaseMapper<Book_Borrow> {
    //获取还书列表
    IPage<ReturnBook> getBorrowList(Page<ReturnBook> page, ReturnBookVo returnBookVo);
    //获取还书列表
    IPage<ReturnBook> getAllBorrowList(Page<ReturnBook> page, ReturnBookVo returnBookVo);
    //借阅查看
    IPage<BorrowInfo> getBorrowInfoList(Page<BorrowInfo> page, BorrowInfoVo borrowInfoVo);
    //学生借阅查看列表
    IPage<BorrowInfo> getStudentBorrowInfoList(Page<BorrowInfo> page, BorrowInfoVo borrowInfoVo);
}
