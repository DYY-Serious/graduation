package com.zua.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.pojo.Class;

import java.util.List;

public interface ClassService extends IService<Class> {

    /**
     * 选择班级
     * @return
     */
    List<Class> classList();
}
