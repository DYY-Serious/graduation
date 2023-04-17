package com.zua.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zua.login.LoginInfo;
import com.zua.pojo.Menu;
import com.zua.pojo.Student;
import com.zua.pojo.User;
import com.zua.service.MenuService;
import com.zua.service.StudentService;
import com.zua.service.UserService;
import com.zua.util.*;
import com.zua.vo.LoginVo;
import com.zua.vo.RouterVO;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("system")
@CrossOrigin
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 用户登入
     *
     * @param loginVo
     * @return
     */
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        if (loginVo.getLoginType().equals("0")) { //0 ：学生
            QueryWrapper<Student> query = new QueryWrapper<>();
            query.lambda().eq(Student::getStudentId, loginVo.getAccount());
            query.lambda().eq(Student::getPassword, MD5.MD5Encode(loginVo.getPassword(), "utf-8"));
            Student one = studentService.getOne(query);
            if (one != null && !one.getAccountStatus().equals("1")) {
                return R.ERRORMSG("账户未激活");
            }
            if (one == null) {
                return R.ERRORMSG("用户名或密码错误!");
            }
            LoginResult result = new LoginResult();
            result.setId(one.getId());
            //生成token
            result.setToken(jwtUtils.generateToken(one.getStudentName(), loginVo.getLoginType(),one.getId()));
            return R.SUCCESS("登录成功", result);
        } else if (loginVo.getLoginType().equals("1")) { // 1: 系统用户
            QueryWrapper<User> query = new QueryWrapper<User>();
            query.lambda().eq(User::getAccount, loginVo.getAccount());
            query.lambda().eq(User::getPassword, MD5.MD5Encode(loginVo.getPassword(), "utf-8"));
            User one = userService.getOne(query);
            if (one != null && one.getAccountStatus() != 1) {
                return R.ERRORMSG("账户未激活");
            }
            if (one == null) {
                return R.ERRORMSG("用户名或密码错误");
            }
            LoginResult result = new LoginResult();
            result.setId(one.getId());
            //生成token
            result.setToken(jwtUtils.generateToken(one.getUsername(), loginVo.getLoginType(),one.getId()));
            return R.SUCCESS("登录成功", result);
        } else {
            return R.ERRORMSG("用户类型不存在!");
        }
    }

    /**
     * 获取用户权限字段
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/getInfo")
    public R getInfo(String id, HttpServletRequest request) {
        //从请求的头部获取token
        String token = request.getHeader("token");
        //从token里面解析用户的类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        if (claims == null) {
            return R.ERROR("过期登入!",600);
        }
        Object userType = claims.get("userType");

        //定义用户信息类
        LoginInfo loginInfo = new LoginInfo();
        if (userType.equals("0")) { //学生
            //根据id查询读者信息
            Student student = studentService.getById(id);
            loginInfo.setIntroduction(student.getStudentName());
            loginInfo.setName(student.getStudentName());
            loginInfo.setAvatar(student.getImage());
            //权限字段查询与设置
            List<Menu> menuList = menuService.getReaderMenuByUserId(id);
            List<String> collect = menuList.stream().filter(item -> item != null && item.getCode() != null).map(item ->
                    item.getCode()).collect(Collectors.toList());
            if (collect.size() == 0) {
                return R.ERRORMSG("暂无登录权限，请联系管理员!");
            }
            //转成数组
            String[] strings = collect.toArray(new String[collect.size()]);
            loginInfo.setRoles(strings);
            return R.SUCCESS("查询成功", loginInfo);
        } else if (userType.equals("1")) { //管理员
            User user = userService.getById(id);
            loginInfo.setIntroduction(user.getUsername());
            loginInfo.setName(user.getUsername());
            loginInfo.setAvatar(user.getImage());
            //权限字段查询与设置
            List<Menu> menuList = menuService.getMenuByUserId(id);
            List<String> collect = menuList.stream().filter(item -> item != null && item.getCode() != null).map(item ->
                    item.getCode()).collect(Collectors.toList());
            if (collect.size() == 0) {
                return R.ERRORMSG("暂无登录权限，请联系管理员!");
            }
//转成数组
            String[] strings = collect.toArray(new String[collect.size()]);
            loginInfo.setRoles(strings);
            return R.SUCCESS("查询成功", loginInfo);
        } else {
            return R.SUCCESS("用户类型不存在", loginInfo);
        }
    }

    //获取菜单
    @GetMapping("/getMenuList")
    public R getMenuList(HttpServletRequest request) {
        //从请求的头部获取token
        String token = request.getHeader("token");
        //从token里面解析用户的类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        if (claims == null) {
            return R.ERROR("过期登入!",600);
        }
        Object userType = claims.get("userType");
        Object id = claims.get("id");
        if (userType.equals("0")) { //读者
            //获取用户信息
            Student student = studentService.loadById(id);
            //获取权限信息
            List<Menu> menuList =
                    menuService.getReaderMenuByUserId(student.getId());
            List<Menu> collect = menuList.stream().filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
            if (collect.size() == 0) {
                return R.ERRORMSG("暂无登录权限，请联系管理员!");
            }
            List<RouterVO> routerVOS = MakeTree.makeRouter(collect, null);
            return R.SUCCESS("查询成功", routerVOS);
        } else if (userType.equals("1")) { // 管理员
            //获取用户信息
            User user = userService.loadById(id);
            //获取权限信息
            List<Menu> menuList = menuService.getMenuByUserId(user.getId());
            List<Menu> collect = menuList.stream().filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
            if (collect.size() == 0) {
                return R.ERRORMSG("暂无登录权限，请联系管理员!");
            }
            List<RouterVO> routerVOS = MakeTree.makeRouter(collect, null);
            return R.SUCCESS("查询成功", routerVOS);
        } else {
            return R.ERRORMSG("用户类型不存在!");
        }
    }

}
