package com.zua.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.annotation.Auth;
import com.zua.pojo.Assign;
import com.zua.pojo.Role;
import com.zua.service.RoleMenuService;
import com.zua.service.RoleService;
import com.zua.util.JwtUtils;
import com.zua.util.R;
import com.zua.vo.AssignVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/role")
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 获取角色列表，或者根据条件获取角色列表
     * @param role
     * @param pageSize
     * @param curPage
     * @return
     */
    @Auth
    @GetMapping("list")
    public R getRoleList(Role role, Integer pageSize, Integer curPage, HttpServletRequest request) {
        IPage<Role> page = roleService.getListRole(role,pageSize,curPage);
        return R.SUCCESS("查询成功",page);
    }

    /**
     * 新增角色
     * @param role
     * @return
     */
    @Auth
    @PostMapping("addRole")
    public R addRole(@RequestBody Role role,HttpServletRequest request) {
        return roleService.addRole(role);
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @Auth
    @PostMapping("editRole")
    public R editRole(@RequestBody Role role,HttpServletRequest request) {
        return roleService.editRole(role);
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @Auth
    @DeleteMapping("delRole/{roleId}")
    public R delRole(@PathVariable("roleId") String id,HttpServletRequest request) {
        boolean flag = roleService.removeById(id);
        if (!flag) {
            return R.ERRORMSG("删除失败");
        }
        return R.SUCCESS("删除成功");
    }

    /**
     * 查询角色权限树的回显
     * @param parm
     * @return
     */
    @Auth
    @GetMapping("/getAssingShow")
    public R getAssignShow(AssignVo parm,HttpServletRequest request){
        Assign show = roleService.getAssignShow(parm);
        return R.SUCCESS("查询成功",show);
    }

    /**
     * 角色权限分配
     * @param assignVo
     * @return
     */
    @Auth
    @PostMapping("assignSave")
    public R assignSave(@RequestBody AssignVo assignVo,HttpServletRequest request) {
        return roleMenuService.assignSave(assignVo.getRoleId(),assignVo.getMenuList());
    }
}
