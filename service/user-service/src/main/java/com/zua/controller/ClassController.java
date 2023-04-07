package com.zua.controller;

import com.zua.pojo.Class;
import com.zua.pojo.Menu;
import com.zua.service.ClassService;
import com.zua.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("class")
@CrossOrigin
public class ClassController {

    @Autowired
    private ClassService classService;

    /**
     * 选择班级
     * @return
     */
    @GetMapping("/all")
    public R getClassList(){
        List<Class> list = classService.classList();
        return R.SUCCESS("查询成功",list);
    }
}
