package com.zua.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.annotation.Auth;
import com.zua.pojo.Book_Borrow;
import com.zua.pojo.BorrowInfo;
import com.zua.pojo.ReturnBook;
import com.zua.service.BookBorrowService;
import com.zua.service.BookSerivce;
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
import java.util.Date;
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
    @Auth
    @GetMapping("borrowList")
    public R getBorrowInfo(ReturnBookVo bookBorrowVo,HttpServletRequest request) {
        //从请求的头部获取token
        String token = request.getHeader("token");
        //从token里面解析用户的类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Object userType = claims.get("userType");
        if (userType.equals("0")) {
            IPage<ReturnBook> page = bookBorrowService.getBorrowList(bookBorrowVo);
            return R.SUCCESS("查询成功",page);
        } else if (userType.equals("1")) {
            IPage<ReturnBook> page = bookBorrowService.getAllBorrowList(bookBorrowVo);
            return R.SUCCESS("查询成功",page);
        } else {
            return R.SUCCESS("查询成功");
        }
    }

    /**
     * 新增图书借阅
     * @param bookBorrowVo
     * @return
     */
    @Auth
    @PostMapping("saveBorrow")
    public R saveBorrow(@RequestBody BookBorrowVo bookBorrowVo,HttpServletRequest request) {
        return bookBorrowService.saveBorrow(bookBorrowVo);
    }

    /**
     * 还书
     * @param returnBookVos
     * @return
     */
    @Auth
    @PostMapping("returnBook")
    public R returnBook(@RequestBody List<ReturnBookVo> returnBookVos,HttpServletRequest request) {
        return bookBorrowService.returnBook(returnBookVos);
    }

    /**
     * 异常还书
     * @param returnBookVo
     * @return
     */
    @Auth
    @PostMapping("/exceptionBooks")
    public R exceptionBooks(@RequestBody ReturnBookVo returnBookVo,HttpServletRequest request){
        return bookBorrowService.exceptionBook(returnBookVo);
    }

    /**
     * 获取借阅列表
     * @param borrowInfoVo
     * @return
     */
    @Auth
    @GetMapping("getInfoList")
    public R getBorrowInfoList(BorrowInfoVo borrowInfoVo, HttpServletRequest request) {
        //从请求的头部获取token
        String token = request.getHeader("token");
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
    @Auth
    @PostMapping("editBorrow")
    public R editBorrow(Book_Borrow book_borrow,HttpServletRequest request) {
        bookBorrowService.updateById(book_borrow);
        return R.SUCCESS("数据修改成功");
    }

    /**
     * 删除借阅记录
     * @param book_borrow
     * @return
     */
    @Auth
    public R deleteBorrow(Book_Borrow book_borrow,HttpServletRequest request) {
        bookBorrowService.removeById(book_borrow);
        return R.SUCCESS("删除成功");
    }

    /**
     * 借书待审核总数
     * @return
     */
    @Auth
    @GetMapping("getBorrowApplyCount")
    public R getApplyCount(String userType, String id) {
        if (userType.equals("0")) {
            QueryWrapper<Book_Borrow> query = new QueryWrapper<>();
            query.lambda().eq(Book_Borrow::getApplyStatus, "0")
                    .eq(Book_Borrow::getUserId, id);
            int count = (int) bookBorrowService.count(query);
            return R.SUCCESS("查询成功", count);
        } else if (userType.equals("1")) {
            QueryWrapper<Book_Borrow> query = new QueryWrapper<>();
            query.lambda().eq(Book_Borrow::getApplyStatus, "0");
            int count = (int) bookBorrowService.count(query);
            return R.SUCCESS("查询成功", count);
        } else {
            return R.SUCCESS("查询成功", 0);
        }
    }

    /**
     * 到期还书总数
     * @param userType
     * @param id
     * @return
     */
    @Auth
    @GetMapping("/getBorrowReturnCount")
    public R getBorrowReturnCount(String userType, String id) {
        if (userType.equals("0")) { //读者
            QueryWrapper<Book_Borrow> query = new QueryWrapper<>();
            query.lambda().eq(Book_Borrow::getBorrowStatus, "1")
                    .lt(Book_Borrow::getReturnTime, new Date())
                    .eq(Book_Borrow::getUserId, id);
            int count = (int) bookBorrowService.count(query);
            return R.SUCCESS("查询成功", count);
        } else if (userType.equals("1")) { //系统管理员
            QueryWrapper<Book_Borrow> query = new QueryWrapper<>();
            query.lambda().eq(Book_Borrow::getBorrowStatus, "1")
                    .lt(Book_Borrow::getReturnTime, new Date());
            int count = (int) bookBorrowService.count(query);
            return R.SUCCESS("查询成功", count);
        } else {
            return R.SUCCESS("查询成功", 0);
        }
    }

    /**
     * 续借
     * @return
     */
    @Auth
    @PostMapping("addTime")
    public R addTime(@RequestBody Book_Borrow book_borrow) {
        return bookBorrowService.addTime(book_borrow);
    }

    /**
     * 审核图书
     * @return
     */
    @Auth
    @PostMapping("applyBook")
    public R applyBook(@RequestBody Book_Borrow book_borrow) {
        bookBorrowService.updateById(book_borrow);
        return R.SUCCESS("审核成功");
    }
}
