package com.zua.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zua.pojo.Student;
import com.zua.utils.R;
import com.zua.vo.StudentVo;

public interface StudentService extends IService<Student> {

    /**
     * 查询学生
     * @param studentVo
     * @param pageSize
     * @param curPage
     * @return
     */
    IPage<Student> getStuList(StudentVo studentVo, Integer pageSize, Integer curPage);

    /**
     * 查询学生对应的权限
     * @param id
     * @return
     */
    R getRoleById(String id);

    /**
     * 添加学生
     * @param studentVo
     * @return
     */
    R addStu(StudentVo studentVo);

    /**
     * 修改学生信息
     * @param studentVo
     * @return
     */
    R editStu(StudentVo studentVo);

    /**
     * 根据学号获取学生信息
     * @param stuId
     * @return
     */
    Student getStudentByStuId(String stuId);

    Student loadById(Object id);
}
