package com.zua.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zua.mapper.RoleMapper;
import com.zua.pojo.Assign;
import com.zua.pojo.Menu;
import com.zua.pojo.Role;
import com.zua.pojo.User;
import com.zua.service.MenuService;
import com.zua.service.RoleService;
import com.zua.service.UserService;
import com.zua.util.MakeTree;
import com.zua.util.R;
import com.zua.vo.AssignVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    /**
     * 获取角色列表，或者根据条件获取角色列表
     * @param role
     * @param pageSize
     * @param curPage
     * @return
     */
    @Override
    public IPage<Role> getListRole(Role role, Integer pageSize, Integer curPage) {
        IPage<Role> page = new Page<Role>(curPage,pageSize);
        //构造查询条件
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>();
        queryWrapper.like(role.getRoleName() != null && !role.getRoleName().equals(""),Role::getRoleName,role.getRoleName());
        queryWrapper.eq(role.getRoleType() != null && !role.getRoleType().equals(""),Role::getRoleType,role.getRoleType());
        //进行分页查询
        page = this.page(page,queryWrapper);
        return page;
    }

    /**
     * 新增角色
     * @param role
     * @return
     */
    @Override
    public R addRole(Role role) {
        if (role.getRoleType().equals("3")) {
            //构造查询查询调价，先判断角色类型一样的情况下是否有相同的名称
            LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>();
            queryWrapper.eq(role.getRoleType() != null,Role::getRoleType,role.getRoleType());
            Role one = this.getOne(queryWrapper);
            if (one != null) {
                return R.ERRORMSG("默认角色只能有一个");
            }
        }
        //构造查询查询调价，先判断角色类型一样的情况下是否有相同的名称
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>();
        queryWrapper.eq(role.getRoleName() != null,Role::getRoleName,role.getRoleName());
        queryWrapper.eq(role.getRoleType() != null,Role::getRoleType,role.getRoleType());
        Role one = this.getOne(queryWrapper);
        if (one != null) {
            return R.ERRORMSG("该类型角色已存在");
        }
        boolean flag = this.save(role);
        return flag ? R.SUCCESS("新增成功") : R.ERRORMSG("新增失败,请稍后再试");
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @Override
    public R editRole(Role role) {

        if (role.getRoleType().equals("3")) {
            //构造查询查询调价，先判断角色类型一样的情况下是否有相同的名称
            LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>();
            queryWrapper.eq(role.getRoleType() != null,Role::getRoleType,role.getRoleType());
            Role one = this.getOne(queryWrapper);
            if (one != null) {
                return R.ERRORMSG("默认角色只能有一个,请重新修改");
            }
        }

        boolean flag = this.updateById(role);
        return flag ? R.SUCCESS("修改成功") : R.ERRORMSG("修改失败,请稍后再试");
    }

    /**
     * 角色权限的回显
     * @param parm
     * @return
     */
    @Override
    public Assign getAssignShow(AssignVo parm) {
        //查询当前用户的信息
        User user = userService.getById(parm.getUserId());
        //菜单数据
        List<Menu> list = null;
        if(user.getIsAdmin().equals(1)){ //如果是超级管理员，拥有所有的权限
            QueryWrapper<Menu> query = new QueryWrapper<>();
            query.lambda().orderByAsc(Menu::getOrderNum);
            list = menuService.list(query);
        }else{
            list = menuService.getMenuByUserId(user.getId());
        }
        //组装树
        List<Menu> menuList = MakeTree.makeMenuTree(list, null);
        //查询角色原来的菜单
        List<Menu> roleList =
                menuService.getMenuByRoleId(parm.getRoleId());
        List<String> ids = new ArrayList<>();
        Optional.ofNullable(roleList).orElse(new ArrayList<>
                ()).stream().filter(item -> item != null).forEach(item ->{
            ids.add(item.getId());
        });
        //组装数据
        Assign assign = new Assign();
        assign.setMenuList(menuList);
        assign.setCheckList(ids.toArray());
        return assign;

    }
}
