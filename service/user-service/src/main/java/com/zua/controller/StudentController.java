package com.zua.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.annotation.Auth;
import com.zua.pojo.Student;
import com.zua.pojo.User;
import com.zua.service.StudentService;
import com.zua.service.UserService;
import com.zua.util.JwtUtils;
import com.zua.util.MD5;
import com.zua.util.R;
import com.zua.vo.StudentVo;
import com.zua.vo.UpdatePasswordVo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
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
     *
     * @param studentVo
     * @param pageSize
     * @param curPage
     * @return
     */
    @Auth
    @GetMapping("list")
    public R getStuList(StudentVo studentVo, Integer pageSize, Integer curPage, HttpServletRequest request) {
        IPage<Student> page = studentService.getStuList(studentVo, pageSize, curPage);
        return R.SUCCESS(page);
    }

    /**
     * 查询学生对应的权限
     *
     * @param id
     * @return
     */
    @Auth
    @GetMapping("getRole")
    public R getRoleById(String id, HttpServletRequest request) {
        return studentService.getRoleById(id);
    }

    /**
     * 根据学号获取学生信息
     *
     * @param id
     * @return
     */
    @Auth
    @GetMapping("getStuById")
    public R getByStuId(String id, HttpServletRequest request) {
        Student student = studentService.getStudentByStuId(id);
        return R.SUCCESS(student);
    }

    /**
     * 新增学生
     *
     * @param studentVo
     * @return
     */
    @Auth
    @PostMapping("addStu")
    public R addStu(@RequestBody StudentVo studentVo, HttpServletRequest request) {
        return studentService.addStu(studentVo);
    }

    /**
     * 修改学生信息
     *
     * @param studentVo
     * @return
     */
    @Auth
    @PostMapping("editStu")
    public R editStu(@RequestBody StudentVo studentVo, HttpServletRequest request) {
        return studentService.editStu(studentVo);
    }

    /**
     * 删除学生
     *
     * @param id
     * @return
     */
    @Auth
    @DeleteMapping("delStu/{stuId}")
    public R delStu(@PathVariable("stuId") String id, HttpServletRequest request) {
        boolean flag = studentService.removeById(id);
        if (flag) {
            return R.SUCCESS("删除成功");
        }
        return R.ERRORMSG("删除失败");
    }

    /**
     * 学生注册
     *
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
     *
     * @param student
     * @return
     */
    @Auth
    @PostMapping("/applyStu")
    public R applyReader(@RequestBody Student student, HttpServletRequest request) {
        student.setAccountStatus("1");
        studentService.updateById(student);
        return R.SUCCESS("审核成功!");
    }

    /**
     * 获取总数
     *
     * @return
     */
    @Auth
    @GetMapping("getReaderCount")
    public R getStudentCount() {
        return R.SUCCESS(studentService.count());
    }

    /**
     * 获取总数
     *
     * @return
     */
    @Auth
    @GetMapping("getApplyReaderCount")
    public R getApplyStudentCount() {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getAccountStatus, '0');
        return R.SUCCESS(studentService.count(queryWrapper));
    }

    /**
     * 修改密码
     *
     * @param updatePasswordVo
     * @return
     */
    @Auth
    @PostMapping("updatePassword")
    public R updatePassword(@RequestBody UpdatePasswordVo updatePasswordVo, HttpServletRequest request) {
        String token = request.getHeader("token");
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Object userType = claims.get("userType");
        String old = MD5.MD5Encode(updatePasswordVo.getOldPassword(),"utf-8");
        if (userType.equals("0")) { //读者
            Student student = studentService.getById(updatePasswordVo.getId());
            if (!student.getPassword().equals(old)) {
                return R.ERRORMSG("原密码不正确!");
            }
            student.setPassword(MD5.MD5Encode(updatePasswordVo.getPassword(),"utf-8"));
            boolean b = studentService.updateById(student);
            if (b) {
                return R.SUCCESS("密码修改成功!");
            }
        } else {
            User user = userService.getById(updatePasswordVo.getId());
            if (!user.getPassword().equals(old)) {
                return R.ERRORMSG("原密码不正确!");
            }
            user.setPassword(MD5.MD5Encode(updatePasswordVo.getPassword(),"utf-8"));
            boolean b = userService.updateById(user);
            if (b) {
                return R.SUCCESS("密码修改成功!");
            }
        }
        return R.ERRORMSG("密码修改失败!");

    }
}
