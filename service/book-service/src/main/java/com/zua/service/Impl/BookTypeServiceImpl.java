package com.zua.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.BookMapper;
import com.zua.mapper.BookTypeMapper;
import com.zua.pojo.Book;
import com.zua.pojo.BookType;
import com.zua.pojo.CategoryEcharts;
import com.zua.pojo.User;
import com.zua.service.BookTypeService;
import com.zua.utils.R;
import com.zua.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookTypeServiceImpl extends ServiceImpl<BookTypeMapper, BookType> implements BookTypeService {

    @Autowired
    private BookMapper bookMapper;

    /**
     * 获取图书分类
     * @param bookType
     * @param pageSize
     * @param curPage
     * @return
     */
    @Override
    public IPage<BookType> getListType(BookType bookType, Integer pageSize, Integer curPage) {
        IPage<BookType> page = new Page<BookType>(curPage,pageSize);
        LambdaQueryWrapper<BookType> queryWrapper = new LambdaQueryWrapper<BookType>();
        queryWrapper.like(bookType.getCategoryName() != null && !bookType.getCategoryName().equals(""),
                BookType::getCategoryName,bookType.getCategoryName());
        return this.page(page,queryWrapper);
    }

    /**
     * 新增
     * @param bookType
     * @return
     */
    @Override
    public R addType(BookType bookType) {
        //构造查询条件
        LambdaQueryWrapper<BookType> queryWrapper = new LambdaQueryWrapper<BookType>();
        queryWrapper.eq(BookType::getCategoryName,bookType.getCategoryName());
        List<BookType> list = this.list(queryWrapper);
        if (list != null && list.size() > 0) {
            return R.ERRORMSG("已有该分类");
        }
        this.save(bookType);
        return R.SUCCESS("添加成功");
    }

    /**
     * 编辑
     * @param bookType
     * @return
     */
    @Override
    public R editType(BookType bookType) {
        //构造查询条件
        LambdaQueryWrapper<BookType> queryWrapper = new LambdaQueryWrapper<BookType>();
        queryWrapper.eq(BookType::getCategoryName,bookType.getCategoryName());
        queryWrapper.eq(BookType::getOrderNum,bookType.getOrderNum());
        List<BookType> list = this.list(queryWrapper);
        if (list != null && list.size() > 0) {
            return R.ERRORMSG("已有该分类");
        }
        this.updateById(bookType);
        return R.SUCCESS("修改成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public R delType(String id) {
        //构造查询条件
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<Book>();
        queryWrapper.eq(Book::getCategoryId,id);
        List<Book> books = bookMapper.selectList(queryWrapper);
        if (books != null && books.size() > 0) {
            return R.ERRORMSG("删除失败，该分类下已有图书");
        }
        this.removeById(id);
        return R.SUCCESS("删除成功");
    }

    /**
     * 分类统计
     * @return
     */
    @Override
    public CategoryEcharts getCategoryVo() {
        CategoryEcharts echarts = new CategoryEcharts();
        List<CategoryVo> categoryVo = this.baseMapper.getCategoryVo();
        List<String> names = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();
        for(int i =0;i<categoryVo.size();i++){
            names.add(categoryVo.get(i).getCategoryName());
            counts.add(categoryVo.get(i).getBookCount());
        }
        echarts.setNames(names);
        echarts.setCounts(counts);
        return echarts;
    }
}
