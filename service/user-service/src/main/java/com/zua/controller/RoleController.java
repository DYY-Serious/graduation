package com.zua.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zua.pojo.Assign;
import com.zua.pojo.Role;
import com.zua.service.RoleMenuService;
import com.zua.service.RoleService;
import com.zua.utils.R;
import com.zua.vo.AssignVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 获取角色列表，或者根据条件获取角色列表
     * @param role
     * @param pageSize
     * @param curPage
     * @return
     */
    @GetMapping("list")
    public R getRoleList(Role role,Integer pageSize,Integer curPage) {
        IPage<Role> page = roleService.getListRole(role,pageSize,curPage);
        return R.SUCCESS("查询成功",page);
    }

    /**
     * 新增角色
     * @param role
     * @return
     */
    @PostMapping("addRole")
    public R addRole(@RequestBody Role role) {
        return roleService.addRole(role);
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @PostMapping("editRole")
    public R editRole(@RequestBody Role role) {
        return roleService.editRole(role);
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping("delRole/{roleId}")
    public R delRole(@PathVariable("roleId") String id) {
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
    @GetMapping("/getAssingShow")
    public R getAssignShow(AssignVo parm){
        Assign show = roleService.getAssignShow(parm);
        return R.SUCCESS("查询成功",show);
    }

    /**
     * 角色权限分配
     * @param assignVo
     * @return
     */
    @PostMapping("assignSave")
    public R assignSave(@RequestBody AssignVo assignVo) {
        return roleMenuService.assignSave(assignVo.getRoleId(),assignVo.getMenuList());
    }
}
