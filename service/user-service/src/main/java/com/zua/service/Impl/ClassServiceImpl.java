package com.zua.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.ClassMapper;
import com.zua.pojo.Class;
import com.zua.pojo.Menu;
import com.zua.service.ClassService;
import com.zua.utils.MakeTree;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {

    /**
     * 选择班级
     * @return
     */
    @Override
    public List<Class> classList() {
        List<Class> classes = this.list();
        //组装顶级菜单，防止无数据没有菜单
        Class menu = new Class();
        menu.setId(null);
        menu.setParentId("top-class");
        menu.setTitle("顶级班级");
        classes.add(menu);
        List<Class> classList = MakeTree.makeClassTree(classes,"top-class");
        return classList;
    }
}
