package com.zua.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.pojo.Book_Borrow;
import com.zua.pojo.BorrowInfo;
import com.zua.pojo.ReturnBook;
import com.zua.service.BookBorrowService;
import com.zua.utils.JwtUtils;
import com.zua.utils.R;
import com.zua.vo.BookBorrowVo;
import com.zua.vo.BorrowInfoVo;
import com.zua.vo.ReturnBookVo;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("bookBorrow")
@CrossOrigin
public class BookBorrowController {

    @Autowired
    private BookBorrowService bookBorrowService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 还书列表
     * @param bookBorrowVo
     * @return
     */
    @GetMapping("borrowList")
    public R getBorrowInfo(ReturnBookVo bookBorrowVo) {
        IPage<ReturnBook> page = bookBorrowService.getBorrowList(bookBorrowVo);
        return R.SUCCESS("查询成功",page);
    }

    /**
     * 新增图书借阅
     * @param bookBorrowVo
     * @return
     */
    @PostMapping("saveBorrow")
    public R saveBorrow(@RequestBody BookBorrowVo bookBorrowVo) {
        return bookBorrowService.saveBorrow(bookBorrowVo);
    }

    /**
     * 还书
     * @param returnBookVos
     * @return
     */
    @PostMapping("returnBook")
    public R returnBook(@RequestBody List<ReturnBookVo> returnBookVos) {
        return bookBorrowService.returnBook(returnBookVos);
    }

    /**
     * 异常还书
     * @param returnBookVo
     * @return
     */
    @PostMapping("/exceptionBooks")
    public R exceptionBooks(@RequestBody ReturnBookVo returnBookVo){
        return bookBorrowService.exceptionBook(returnBookVo);
    }

    /**
     * 获取借阅列表
     * @param borrowInfoVo
     * @return
     */
    @GetMapping("getInfoList")
    public R getBorrowInfoList(BorrowInfoVo borrowInfoVo, HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return R.ERROR("token过期!",600);
        }
        //从token里面解析用户的类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Object userType = claims.get("userType");
        if (userType.equals("0")) {
            return bookBorrowService.getStudentBorrowInfoList(borrowInfoVo);
        } else if (userType.equals("1")) {
            return bookBorrowService.getBorrowInfoList(borrowInfoVo);
        } else {
            return R.SUCCESS("查询成功");
        }
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
