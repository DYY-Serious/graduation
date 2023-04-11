package com.zua.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.annotation.Auth;
import com.zua.pojo.BookType;
import com.zua.pojo.CategoryEcharts;
import com.zua.service.BookTypeService;
import com.zua.utils.JwtUtils;
import com.zua.utils.R;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("category")
@CrossOrigin
public class BookTypeController {

    @Autowired
    private BookTypeService bookTypeService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 获取图书分类
     * @param bookType
     * @param pageSize
     * @param curPage
     * @return
     */
    @Auth
    @GetMapping("list")
    public R getTypeList(BookType bookType,Integer pageSize,Integer curPage, HttpServletRequest request) {
        IPage<BookType> page = bookTypeService.getListType(bookType,pageSize,curPage);
        return R.SUCCESS(page);
    }

    /**
     * 新增
     * @param bookType
     * @return
     */
    @Auth
    @PostMapping("addType")
    public R addType(@RequestBody BookType bookType, HttpServletRequest request) {
        return bookTypeService.addType(bookType);
    }

    /**
     * 编辑
     * @param bookType
     * @return
     */
    @Auth
    @PostMapping("editType")
    public R editType(@RequestBody BookType bookType, HttpServletRequest request) {
        return bookTypeService.editType(bookType);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Auth
    @DeleteMapping("delType/{id}")
    public R delType(@PathVariable("id") String id, HttpServletRequest request) {
        return bookTypeService.delType(id);
    }

    /**
     * 图书列表分类
     * @return
     */
    @Auth
    @GetMapping("/cateList")
    public R getCateList(HttpServletRequest request){
        List<BookType> list = bookTypeService.list();
        return R.SUCCESS("查询成功",list);
    }

    /**
     * 图书分类统计
     * @return
     */
    @Auth
    @GetMapping("/categoryCount")
    public R categoryCount(){
        CategoryEcharts vo = bookTypeService.getCategoryVo();
        return R.SUCCESS("查询成功",vo);
    }

}
