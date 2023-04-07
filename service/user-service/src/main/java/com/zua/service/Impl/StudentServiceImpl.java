package com.zua.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.StudentMapper;
import com.zua.pojo.Role;
import com.zua.pojo.Student;
import com.zua.pojo.StudentRole;
import com.zua.service.RoleService;
import com.zua.service.StudentRoleService;
import com.zua.service.StudentService;
import com.zua.utils.MD5;
import com.zua.utils.R;
import com.zua.vo.StudentVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private StudentRoleService studentRoleService;

    @Autowired
    private RoleService roleService;

    /**
     * 查询学生
     * @param studentVo
     * @param pageSize
     * @param curPage
     * @return
     */
    @Override
    public IPage<Student> getStuList(StudentVo studentVo, Integer pageSize, Integer curPage) {
        //构造查询条件
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<Student>();
        queryWrapper.like(studentVo.getIdCard() != null && !studentVo.getIdCard().equals(""),Student::getIdCard,
                studentVo.getIdCard());
        queryWrapper.like(studentVo.getStudentId() != null && !studentVo.getStudentId().equals(""),Student::getStudentId,
                studentVo.getStudentId());
        queryWrapper.like(studentVo.getPhone() != null && !studentVo.getPhone().equals(""),Student::getPhone,
                studentVo.getPhone());
        queryWrapper.like(studentVo.getStudentName() != null && !studentVo.getStudentName().equals(""),Student::getStudentName,
                studentVo.getStudentName());
        //构造分页对象
        IPage<Student> page = new Page<Student>(curPage,pageSize);
        page = this.page(page,queryWrapper);
        return page;
    }

    /**
     * 查询学生对应的权限
     * @param id
     * @return
     */
    @Override
    public R getRoleById(String id) {
        //构造查询条件
        LambdaQueryWrapper<StudentRole> queryWrapper = new LambdaQueryWrapper<StudentRole>();
        queryWrapper.eq(StudentRole::getStudentId,id);
        StudentRole one = studentRoleService.getOne(queryWrapper);
        return R.SUCCESS("查询成功",one);
    }

    /**
     * 添加学生
     * @param studentVo
     * @return
     */
    @Override
    @Transactional
    public R addStu(StudentVo studentVo) {
        //构造查询条件
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<Student>();
        queryWrapper.eq(studentVo.getStudentId()!=null,Student::getStudentId,studentVo.getStudentId());
        Student stuQuery = this.getOne(queryWrapper);
        if (stuQuery != null) {
            return R.ERRORMSG("添加失败,学号重复");
        }
        //密码加密
        String password = MD5.MD5Encode(studentVo.getPassword(),"utf-8");
        studentVo.setPassword(password);
        this.save(studentVo);
        //判断角色id是否为空
        StudentRole studentRole = new StudentRole();
        if (StringUtils.isEmpty(studentVo.getRoleId())) {
            //为空，为学生设置默认角色
            LambdaQueryWrapper<Role> queryWrapperRole = new LambdaQueryWrapper<>();
            queryWrapperRole.eq(Role::getRoleType,"3");
            Role one = roleService.getOne(queryWrapperRole);
            studentRole.setRoleId(one.getId());
            studentRole.setStudentId(studentVo.getId());
        }else {
            studentRole.setRoleId(studentVo.getRoleId());
            studentRole.setStudentId(studentVo.getId());
        }
        studentRoleService.save(studentRole);

        return R.SUCCESS("添加成功");
    }

    /**
     * 修改学生信息
     * @param studentVo
     * @return
     */
    @Override
    @Transactional
    public R editStu(StudentVo studentVo) {
        //先删除学生对应的角色
        LambdaQueryWrapper<StudentRole> queryWrapper = new LambdaQueryWrapper<StudentRole>();
        queryWrapper.eq(StudentRole::getStudentId,studentVo.getId());
        studentRoleService.remove(queryWrapper);
        //再重新进行插入
        StudentRole studentRole = new StudentRole();
        studentRole.setRoleId(studentVo.getRoleId());
        studentRole.setStudentId(studentVo.getId());
        studentRoleService.save(studentRole);
        //更新学生信息
        boolean flag = this.updateById(studentVo);
        if (flag) {
            return R.SUCCESS("修改成功");
        }
        return R.ERRORMSG("修改失败");
    }

    @Override
    public Student getStudentByStuId(String stuId) {
        if (stuId.length() > 10) {
            //构造查询调价
            LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<Student>();
            queryWrapper.eq(Student::getId,stuId);
            return this.getOne(queryWrapper);
        }
        //构造查询调价
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<Student>();
        queryWrapper.eq(Student::getStudentId,stuId);
        return this.getOne(queryWrapper);
    }

    @Override
    public Student loadById(Object id) {
        QueryWrapper<Student> query = new QueryWrapper<Student>();
        query.lambda().eq(Student::getId,id);
        return this.baseMapper.selectOne(query);
    }
}
