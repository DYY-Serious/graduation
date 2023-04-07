package com.zua.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.pojo.BookType;
import com.zua.utils.R;

public interface BookTypeService extends IService<BookType> {

    /**
     * 获取图书分类
     * @param bookType
     * @param pageSize
     * @param curPage
     * @return
     */
    IPage<BookType> getListType(BookType bookType, Integer pageSize, Integer curPage);

    /**
     * 新增
     * @param bookType
     * @return
     */
    R addType(BookType bookType);

    /**
     * 编辑
     * @param bookType
     * @return
     */
    R editType(BookType bookType);

    /**
     * 删除
     * @param id
     * @return
     */
    R delType(String id);
}
