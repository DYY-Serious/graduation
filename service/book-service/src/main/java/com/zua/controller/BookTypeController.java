package com.zua.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.pojo.BookType;
import com.zua.service.BookTypeService;
import com.zua.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@CrossOrigin
public class BookTypeController {

    @Autowired
    private BookTypeService bookTypeService;

    /**
     * 获取图书分类
     * @param bookType
     * @param pageSize
     * @param curPage
     * @return
     */
    @GetMapping("list")
    public R getTypeList(BookType bookType,Integer pageSize,Integer curPage) {
        IPage<BookType> page = bookTypeService.getListType(bookType,pageSize,curPage);
        return R.SUCCESS(page);
    }

    /**
     * 新增
     * @param bookType
     * @return
     */
    @PostMapping("addType")
    public R addType(@RequestBody BookType bookType) {
        return bookTypeService.addType(bookType);
    }

    /**
     * 编辑
     * @param bookType
     * @return
     */
    @PostMapping("editType")
    public R editType(@RequestBody BookType bookType) {
        return bookTypeService.editType(bookType);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("delType/{id}")
    public R delType(@PathVariable("id") String id) {
        return bookTypeService.delType(id);
    }

    /**
     * 图书列表分类
     * @return
     */
    @GetMapping("/cateList")
    public R getCateList(){
        List<BookType> list = bookTypeService.list();
        return R.SUCCESS("查询成功",list);
    }
}
