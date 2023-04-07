package com.zua.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.pojo.Student;
import com.zua.pojo.User;
import com.zua.service.StudentService;
import com.zua.service.UserService;
import com.zua.utils.JwtUtils;
import com.zua.utils.R;
import com.zua.vo.StudentVo;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("student")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 获取学生列表
     * @param studentVo
     * @param pageSize
     * @param curPage
     * @return
     */
    @GetMapping("list")
    public R getStuList(StudentVo studentVo,Integer pageSize,Integer curPage) {
        IPage<Student> page = studentService.getStuList(studentVo,pageSize,curPage);
        return R.SUCCESS(page);
    }

    /**
     * 查询学生对应的权限
     * @param id
     * @return
     */
    @GetMapping("getRole")
    public R getRoleById(String id) {
        return studentService.getRoleById(id);
    }

    /**
     * 根据学号获取学生信息
     * @param id
     * @return
     */
    @GetMapping("getStuById")
    public R getByStuId(String id) {
        Student student = studentService.getStudentByStuId(id);
        return R.SUCCESS(student);
    }

    /**
     * 新增学生
     * @param studentVo
     * @return
     */
    @PostMapping("addStu")
    public R addStu(@RequestBody StudentVo studentVo) {
        return studentService.addStu(studentVo);
    }

    /**
     * 修改学生信息
     * @param studentVo
     * @return
     */
    @PostMapping("editStu")
    public R editStu(@RequestBody StudentVo studentVo) {
        return studentService.editStu(studentVo);
    }

    /**
     * 删除学生
     * @param id
     * @return
     */
    @DeleteMapping("delStu/{stuId}")
    public R delStu(@PathVariable("stuId") String id) {
        boolean flag = studentService.removeById(id);
        if (flag) {
            return R.SUCCESS("删除成功");
        }
        return R.ERRORMSG("删除失败");
    }

    /**
     * 学生注册
     * @param studentVo
     * @return
     */
    @PostMapping("registerStu")
    public R registerStu(@RequestBody StudentVo studentVo) {
        studentVo.setAccountStatus("0");
        return studentService.addStu(studentVo);
    }

    /**
     * 学生审核
     * @param student
     * @return
     */
    @PostMapping("/applyStu")
    public R applyReader(@RequestBody Student student) {
        student.setAccountStatus("1");
        studentService.updateById(student);
        return R.SUCCESS("审核成功!");
    }

}
